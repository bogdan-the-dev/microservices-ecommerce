package ro.bogdansoftware.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.bogdansoftware.product.dto.CreateCategoryRequestDTO;
import ro.bogdansoftware.product.dto.CreateProductRequestDTO;
import ro.bogdansoftware.product.model.Category;
import ro.bogdansoftware.product.model.Product;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping(value = "add-product")
    public ResponseEntity<Void> addProduct(@RequestBody CreateProductRequestDTO productRequestDTO) {
        log.info("New product created {}", productRequestDTO);
        this.productService.addProduct(productRequestDTO);

        return ResponseEntity.created(URI.create("")).build();

    }

    @GetMapping(value = "get-all")
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(this.productService.getAll());
    }

    @PostMapping(value = "add-category")
    public ResponseEntity<Void> createCategory(@RequestBody CreateCategoryRequestDTO requestDTO) {
        this.productService.addCategory(requestDTO);
        return ResponseEntity.created(URI.create("")).build();
    }

    @GetMapping(value = "all-categories")
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.ok(this.productService.getCategories());
    }
}
