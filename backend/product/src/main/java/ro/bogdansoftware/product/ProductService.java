package ro.bogdansoftware.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.bogdansoftware.product.dto.CreateCategoryRequestDTO;
import ro.bogdansoftware.product.dto.CreateProductRequestDTO;
import ro.bogdansoftware.product.model.Category;
import ro.bogdansoftware.product.model.Product;

import java.util.List;
import java.util.UUID;


@Slf4j
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
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

    public void addCategory(CreateCategoryRequestDTO requestDTO) {
        Category category = Category.builder()
                .name(requestDTO.name())
                .build();
        this.categoryRepository.insert(category);
    }

    public List<Category> getCategories() {
        return this.categoryRepository.findAll();
    }
}
