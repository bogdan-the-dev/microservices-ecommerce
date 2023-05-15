package ro.bogdansoftware.product;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ro.bogdansoftware.clients.category.ICategoryClient;
import ro.bogdansoftware.product.dto.AssignProductToCategoryRequestDTO;
import ro.bogdansoftware.product.dto.AssignProductToSubcategoryRequestDTO;
import ro.bogdansoftware.product.dto.CreateProductRequestDTO;
import ro.bogdansoftware.product.dto.ProductResponseDTO;
import ro.bogdansoftware.product.model.Product;

import java.math.BigDecimal;
import java.util.*;


@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ICategoryClient ICategoryClient;

    private final static Log log = LogFactory.getLog(ProductService.class);


    public ProductService(ProductRepository productRepository, ICategoryClient ICategoryClient) {
        this.productRepository = productRepository;
        this.ICategoryClient = ICategoryClient;
    }

    public void addProduct(CreateProductRequestDTO productRequestDTO) {
        Product product = Product.builder()
                .id(UUID.randomUUID().toString())
                .name(productRequestDTO.name())
                .description(productRequestDTO.description())
                .price(productRequestDTO.price())
                .build();

        productRepository.insert(product);

    }

    public List<Product> getAll() {
        return this.productRepository.findAll();
    }

    public List<Product> getProductsByCategoryName(String categoryName) {
//        String categoryId = webClientBuilder
//                .build()
//                .get()
//                .uri("http://CATEGORY/api/v1/category/get-category-id?name=" + categoryName)
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();
        log.info("Send request to find the category id for: " + categoryName);
        var response = ICategoryClient.getCategoryId(categoryName);
        String categoryId = "";
        if (response.getStatusCode() == HttpStatus.OK) {
            log.info("Category id fond");
            categoryId = response.getBody();
        }

        return this.productRepository.findAllByCategoryIdIs(categoryId).orElse(new ArrayList<>());
    }

    public void assignProductToCategory(AssignProductToCategoryRequestDTO requestDTO) throws RuntimeException {
        var product = this.productRepository.findById(requestDTO.categoryId()).orElseThrow();
        //TODO verify if category exists

        product.setCategoryId(requestDTO.categoryId());
        this.productRepository.save(product);
    }

    public void assignProductToSubcategory(AssignProductToSubcategoryRequestDTO requestDTO) {
        var product = this.productRepository.findById(requestDTO.productId()).orElseThrow();
        //TODO verify if subcategory exists

        product.setSubcategoryId(requestDTO.subcategoryId());
        this.productRepository.save(product);
    }

    public void editProduct(ProductResponseDTO requestDTO) {
        var product = this.productRepository.findById(requestDTO.id()).orElseThrow();

    }

    public void changeInventoryStatus(String id, boolean status) {
        Optional<Product> opt = productRepository.findById(id);
        if(opt.isEmpty()) {
            log.error(String.format("Product with id [%s] was not found in the product database", id));
            return;
        }
        Product p = opt.get();
        p.setInventoryEmpty(status);
        productRepository.save(p);
    }

    public Dictionary<String, BigDecimal> getProductsPrices(List<String> ids) {
        Dictionary<String, BigDecimal> prices = new Hashtable<>();
        for(String productId: ids) {
            Optional<Product> o = productRepository.findById(productId);
            o.ifPresent(product -> prices.put(productId, product.getPrice()));
        }
        return prices;
    }
}
