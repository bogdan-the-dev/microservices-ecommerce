package ro.bogdansoftware.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import ro.bogdansoftware.product.dto.AssignProductToCategoryRequestDTO;
import ro.bogdansoftware.product.dto.AssignProductToSubcategoryRequestDTO;
import ro.bogdansoftware.product.dto.CreateProductRequestDTO;
import ro.bogdansoftware.product.dto.ProductResponseDTO;
import ro.bogdansoftware.product.model.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


@Slf4j
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final WebClient.Builder webClientBuilder;
    public ProductService(ProductRepository productRepository, WebClient.Builder builder) {
        this.productRepository = productRepository;
        this.webClientBuilder = builder;
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
        String categoryId = webClientBuilder
                .build()
                .get()
                .uri("http://CATEGORY/category/get-category-name?name=" + categoryName)
                .retrieve()
                .bodyToMono(String.class)
                .block();

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

}
