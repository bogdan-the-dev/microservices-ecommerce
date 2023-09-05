package ro.bogdansoftware.product;

import lombok.RequiredArgsConstructor;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import ro.bogdansoftware.clients.file.IFileClient;
import ro.bogdansoftware.clients.file.PhotoDeleteDTO;
import ro.bogdansoftware.clients.file.PhotoUploadDTO;
import ro.bogdansoftware.clients.inventory.IInventoryClient;
import ro.bogdansoftware.clients.inventory.InventoryDTO;
import ro.bogdansoftware.clients.product.ProductForCartDTO;
import ro.bogdansoftware.clients.review.IReviewClient;
import ro.bogdansoftware.clients.review.ReviewProductPreview;
import ro.bogdansoftware.product.dto.*;
import ro.bogdansoftware.product.model.ComparisonType;
import ro.bogdansoftware.product.model.Product;
import ro.bogdansoftware.product.model.ProductFilter;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final MongoTemplate mongoTemplate;
    private final IReviewClient iReviewClient;
    private final IFileClient fileClient;
    private final IInventoryClient inventoryClient;

    private final static Integer PAGE_SIZE = 24;
    private final static Log log = LogFactory.getLog(ProductService.class);


    public void createProduct(CreateProductDTO productDTO) {
        Product p = Product.builder()
                .id(UUID.randomUUID().toString())
                .title(productDTO.title())
                .description(productDTO.description())
                .price(productDTO.price())
                .category(productDTO.category())
                .subcategory(productDTO.subcategory())
                .specifications(Serializer.deserializeSpecs(productDTO.specifications()))
                .promotion(productDTO.promotion())
                .outOfStock(productDTO.outOfStock())
                .photos(new LinkedList<>())
                .creationTimestamp(LocalDateTime.now())
                .latestEditTimestamp(LocalDateTime.now())
                .isEnabled(true)
                .build();
        try {
            if(productDTO.photos().size() > 0) {
                var response = fileClient.uploadPhotos(new PhotoUploadDTO(p.getId(), productDTO.photos()));
                var links = response.getBody();
                p.setPhotos(links);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while uploading the photos to google cloud bucket");
        }
        iReviewClient.createReviews(p.getId());
        var res = inventoryClient.createInventory(new InventoryDTO(p.getId(), productDTO.initialQuantity()));
        if(res.getStatusCode().value() != 201) {
            throw new RuntimeException("Error while creating the inventory");
        }
        productRepository.save(p);

    }

    public List<Product> getAll() {
        return this.productRepository.findAll();
    }

    public ViewEditProductDTO getProductEdit(String id) {
        Product p = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product id"));
        return new ViewEditProductDTO(
                p.getId(),
                p.getTitle(),
                p.getDescription(),
                p.getPrice(),
                p.getCategory(),
                p.getSubcategory(),
                p.getPhotos(),
                Serializer.serializeSpecs(p.getSpecifications()),
                p.getPromotion(),
                p.isOutOfStock(),
                p.isEnabled());
    }

    public List<ProductTableDTO> getProductsForTable() {
        return productRepository.findAll().stream().map(elem -> new ProductTableDTO(elem.getId(), elem.getTitle(), elem.getFirstPhoto(), elem.getPromotion() != null, elem.getPrice(), elem.isOutOfStock(), elem.getCategory(), elem.getSubcategory(), elem.getCreationTimestamp(), elem.isEnabled())).collect(Collectors.toList());
    }

    public void assignProductToCategory(AssignProductToCategoryRequestDTO requestDTO) throws RuntimeException {
        var product = this.productRepository.findById(requestDTO.categoryId()).orElseThrow();
        //TODO verify if category exists

        product.setCategory(requestDTO.categoryId());
        this.productRepository.save(product);
    }

    public void assignProductToSubcategory(AssignProductToSubcategoryRequestDTO requestDTO) {
        var product = this.productRepository.findById(requestDTO.productId()).orElseThrow();
        //TODO verify if subcategory exists

        product.setSubcategory(requestDTO.subcategoryId());
        this.productRepository.save(product);
    }

    public void editProduct(ViewEditProductDTO requestDTO) {
        Product product = this.productRepository.findById(requestDTO.id()).orElseThrow(() -> new IllegalArgumentException("Invalid product id"));

        product.setPrice(requestDTO.price());
        product.setTitle(requestDTO.title());
        product.setDescription(requestDTO.description());
        product.setCategory(requestDTO.category());
        product.setSubcategory(requestDTO.subcategory());
        product.setSpecifications(Serializer.deserializeSpecs(requestDTO.specifications()));
        product.setLatestEditTimestamp(LocalDateTime.now());
        product.setPromotion(requestDTO.promotion());

        HashSet<String> dtoPhotos = new HashSet<>(requestDTO.photos());

        List<String> photoForDeletion = product.getPhotos().stream().filter(Predicate.not(dtoPhotos::contains)).collect(Collectors.toList());
        List<String> newPhotos = requestDTO.photos().stream().filter(elem -> elem.contains("base64")).collect(Collectors.toList());

        Queue<String> newUrls = new LinkedList<>();

        try {
            fileClient.deletePhotos(new PhotoDeleteDTO(product.getId(), photoForDeletion));
            var res = fileClient.uploadPhotos(new PhotoUploadDTO(product.getId(), newPhotos));
            if(res.getStatusCode().value() == 200) {
                newUrls = new LinkedList<>(res.getBody());
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("Error deleting the unused photos");
        } catch (IOException ex) {
            throw new RuntimeException("Error uploading the new photos");
        }
        LinkedList<String> productPhotos = new LinkedList<>();
        for(String photo: requestDTO.photos()) {
            if (photo.contains("storage.googleapis.com/download/storage/v1/b/product-photo-storage-bucket")) {
                productPhotos.add(photo);
            } else {
                productPhotos.add(newUrls.poll());
            }
        }

        product.setPhotos(productPhotos);

        productRepository.save(product);
    }

    public void deleteProduct(List<String> ids) {
        for(String id: ids) {
            Product p = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product id"));

            try {
                fileClient.deletePhotos(new PhotoDeleteDTO(id, p.getPhotos().stream().filter(Objects::nonNull).collect(Collectors.toList())));
            } catch (MalformedURLException ex) {
                throw new RuntimeException("Error deleting the unused photos");
            }

            iReviewClient.deleteReviews(id);

            inventoryClient.deleteInventory(id);

            productRepository.delete(p);
        }
    }

    public void enable(List<String> ids) {
        for (String id: ids) {
            Product p = productRepository.findById(id).orElseThrow();
            p.setEnabled(true);
            productRepository.save(p);
        }
    }

    public void disable (List<String> ids) {
        for (String id: ids) {
            Product p = productRepository.findById(id).orElseThrow();
            p.setEnabled(false);
            productRepository.save(p);
        }
    }

    public void changeProductsCategory(ChangeProductsCategoryDTO dto) {
        for (String id: dto.ids()) {
            Product p = productRepository.findById(id).orElseThrow();
            p.setCategory(dto.category());
            p.setSubcategory(dto.subcategory());
            productRepository.save(p);
        }
    }

    public void changeInventoryStatus(String id, boolean status) {
        Optional<Product> opt = productRepository.findById(id);
        if(opt.isEmpty()) {
            log.error(String.format("Product with id [%s] was not found in the product database", id));
            return;
        }
        Product p = opt.get();
        p.setOutOfStock(!status);
        productRepository.save(p);
    }

    public Map<String, BigDecimal> getProductsPrices(List<String> ids) {
        Map<String, BigDecimal> prices = new Hashtable<>();
        for(String productId: ids) {
            Optional<Product> o = productRepository.findById(productId);
            o.ifPresent(product -> prices.put(productId, product.getPrice()));
        }
        return prices;
    }

    public List<ProductForCartDTO> getProductForCart(List<String> ids) {
        List<ProductForCartDTO> list = new ArrayList<>(ids.size());
        for (String id: ids) {
            Product p = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Invalid product id"));
            ProductForCartDTO dto = new ProductForCartDTO(id, p.getTitle(), p.getPhotos().get(0), p.getPrice());
            list.add(dto);
        }
        return list;
    }

    public ProductResponseDTO getFullProduct(String id) {
        return ProductResponseDTO.convert(productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product id")));
    }

    public PreviewDTO getProductPreviews(List<ProductFilter> filters, int page) {
        List<Product> products = getProductFiltered(filters, page);

        List<String> productIds = products.stream().map(Product::getId).collect(Collectors.toList());

        ReviewProductPreview reviews = iReviewClient.getRatingForProducts(productIds).getBody();

        List<ProductPreviewDTO> productPreviewDTOS = new ArrayList<>(products.size());

        for(int i = 0; i < products.size(); i++) {
            Product p = products.get(i);

            var hasPromo = p.getPromotion() != null;
            PromotionDTO pDTO = null;
            if(hasPromo) {
                pDTO = new PromotionDTO(p.getPromotion().name(), p.getPromotion().percentage());
            }

            var dto =ProductPreviewDTO.builder()
                            .id(p.getId())
                            .title(p.getTitle())
                            .imagePath(p.getPhotos().get(0))
                            .price(p.getPrice())
                            .promoActive(hasPromo)
                            .promo(pDTO)
                            .rating(reviews.getRatings().get(i))
                            .numberOfReviews(reviews.getNumberOfRatings().get(i))
                            .isAvailable(!p.isOutOfStock())
                            .tags(new LinkedList<>())
                            .build();
            productPreviewDTOS.add(dto);
        }
        return new PreviewDTO(productPreviewDTOS, getProductNumber(filters));
    }

    public List<String> getProductNames(List<String> ids) {
        List<String> names = new ArrayList<>(ids.size());

        for(String id : ids) {
            names.add(productRepository.findById(id).orElseThrow().getTitle());
        }
        return names;
    }

    private int getProductNumber(List<ProductFilter> filters) {
        Criteria criteria = getCriteria(filters);
        return mongoTemplate.find(new Query(criteria), Product.class).size();
    }

    private List<Product> getProductFiltered(List<ProductFilter> filters, int page) {
        filters.add(new ProductFilter("isEnabled", ComparisonType.EQUALS, "true"));
        Criteria criteria = getCriteria(filters);
        Query query = new Query(criteria);
        query.with(PageRequest.of(page-1, PAGE_SIZE));
        return mongoTemplate.find(query, Product.class);
    }

    private Criteria getCriteria(List<ProductFilter> filters) {
        Criteria criteria = new Criteria();

        for(ProductFilter filter : filters) {
            Object value;
            if(Objects.equals(filter.fieldName(), "isEnabled") || Objects.equals(filter.fieldName(), "outOfStock")) {
                value = Objects.equals(filter.value(), "true");
            } else if (Objects.equals(filter.fieldName(), "price") && !filter.value().contains("|")) {
                value = new BigDecimal(filter.value());

            } else {
                value = filter.value();
            }
            switch (filter.comparisonType()) {
                case EQUALS -> criteria.and(filter.fieldName()).is(value);
                case GREATER_THAN -> criteria.and(filter.fieldName()).gte(value);
                case LESS_THAN -> criteria.and(filter.fieldName()).lte(value);
                case CONTAINS -> {
                    Pattern p = Pattern.compile(filter.value(), Pattern.CASE_INSENSITIVE);
                    criteria.and(filter.fieldName()).regex(p.pattern());
                }
                case BETWEEN -> {
                    String[] numbersArray = filter.value().split("\\|");
                    criteria.and(filter.fieldName()).gte(new BigDecimal(numbersArray[0])).lte(new BigDecimal(numbersArray[1].equals("Infinity")? (String.valueOf(Integer.MAX_VALUE)) : numbersArray[1]));
                }
                default -> throw new IllegalArgumentException("Invalid comparison");
            }
        }
        return criteria;
    }

    public void removePromotion(String id) {
        List<Product> products = productRepository.findProductsByPromotionIdIs(id);
        for(Product p : products) {
            p.setPromotion(null);
            productRepository.save(p);
        }
    }

}
