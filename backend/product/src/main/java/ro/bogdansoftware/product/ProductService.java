package ro.bogdansoftware.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.client.WebClient;
import ro.bogdansoftware.clients.category.CategoryClient;
import ro.bogdansoftware.product.dto.AssignProductToCategoryRequestDTO;
import ro.bogdansoftware.product.dto.AssignProductToSubcategoryRequestDTO;
import ro.bogdansoftware.product.dto.CreateProductRequestDTO;
import ro.bogdansoftware.product.dto.ProductResponseDTO;
import ro.bogdansoftware.product.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Slf4j
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryClient categoryClient;
    public ProductService(ProductRepository productRepository, CategoryClient categoryClient) {
        this.productRepository = productRepository;
        this.categoryClient = categoryClient;
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

        var response = categoryClient.getCategoryId(categoryName);
        String categoryId = "";
        if (response.getStatusCode() == HttpStatus.OK) {
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

}
