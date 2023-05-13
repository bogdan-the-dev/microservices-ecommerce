package ro.bogdansoftware.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.bogdansoftware.product.dto.AssignProductToCategoryRequestDTO;
import ro.bogdansoftware.product.dto.AssignProductToSubcategoryRequestDTO;
import ro.bogdansoftware.product.dto.CreateProductRequestDTO;
import ro.bogdansoftware.product.dto.ProductResponseDTO;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<List<ProductResponseDTO>> getAll() {
        return ResponseEntity.ok(this.productService.getAll().stream().map(ProductResponseDTO::convert).collect(Collectors.toList()));
    }

    @GetMapping(value = "get-by-category")
    public ResponseEntity<List<ProductResponseDTO>> getByCategory(@RequestParam String name) {
        return ResponseEntity.ok(this.productService.getProductsByCategoryName(name).stream().map(ProductResponseDTO::convert).toList());
    }

    @PutMapping(value = "assign-product-to-category")
    public ResponseEntity<Void> assignProductToCategory(@RequestBody AssignProductToCategoryRequestDTO requestDTO) {
        this.productService.assignProductToCategory(requestDTO);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "assign-product-to-subcategory")
    public ResponseEntity<Void> assignProductToSubcategory(@RequestBody AssignProductToSubcategoryRequestDTO requestDTO) {



        return ResponseEntity.noContent().build();
    }

}
