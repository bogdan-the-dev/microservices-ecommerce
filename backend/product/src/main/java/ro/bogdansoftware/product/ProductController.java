package ro.bogdansoftware.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.bogdansoftware.clients.product.ProductForCartDTO;
import ro.bogdansoftware.product.dto.*;
import ro.bogdansoftware.product.model.ProductFilter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping(value = "get-all")
    public ResponseEntity<List<ProductResponseDTO>> getAll() {
        return ResponseEntity.ok(this.productService.getAll().stream().map(ProductResponseDTO::convert).collect(Collectors.toList()));
    }

/*    @GetMapping(value = "get-by-category")
    public ResponseEntity<List<ProductResponseDTO>> getByCategory(@RequestParam String name) {
        return ResponseEntity.ok(this.productService.getProductsByCategoryName(name).stream().map(ProductResponseDTO::convert).toList());
    }*/

    @GetMapping(value = "get-product-for-table")
    public ResponseEntity<List<ProductTableDTO>> getProductsForTable() {
        return ResponseEntity.ok(productService.getProductsForTable());
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

    @PostMapping("get-products-prices")
    public ResponseEntity<Map<String, BigDecimal>> getProductsPrices(@RequestBody List<String> productsIds) {
        return ResponseEntity.ok(this.productService.getProductsPrices(productsIds));
    }

    @PutMapping("change-inventory-status")
    public ResponseEntity<Void> changeInventoryStatus(@RequestParam("id") String id, @RequestParam("status") boolean status) {
        productService.changeInventoryStatus(id, status);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("get-product-previews")
    public ResponseEntity<PreviewDTO> getProductPreviews(@RequestParam("page") String pageNumber, @RequestBody List<ProductFilter> filters){
        return ResponseEntity
                .ok(
                        productService
                                .getProductPreviews(filters, Integer.parseInt(pageNumber)
                ));
    }

    @PutMapping("get-product-names")
    public ResponseEntity<List<String>> getProductNames(@RequestBody List<String> ids) {
        return ResponseEntity.ok(productService.getProductNames(ids));
    }

    @PutMapping("get-product-cart")
    public ResponseEntity<List<ProductForCartDTO>> getProductForCart(@RequestBody List<String> ids) {
        return ResponseEntity.ok(productService.getProductForCart(ids));
    }

    @GetMapping("get-full-product")
    public ResponseEntity<ProductResponseDTO> getFullProduct(@RequestParam(name = "id") String id) {
        return ResponseEntity.ok(productService.getFullProduct(id));
    }

    @PostMapping("create")
    public ResponseEntity<Void> createProduct(@RequestBody CreateProductDTO createProductDTO) {
        productService.createProduct(createProductDTO);
        return ResponseEntity.ok().build();
    }
    @GetMapping("get")
    public ResponseEntity<ViewEditProductDTO> getProductForEdit(@RequestParam(value = "id") String id) {
        var res = productService.getProductEdit(id);
        return ResponseEntity.ok(res);
    }
    @PutMapping("edit")
    public ResponseEntity<Void> editProduct(@RequestBody ViewEditProductDTO dto) {
        productService.editProduct(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("delete")
    public ResponseEntity<Void> deleteProduct(@RequestBody List<String> ids) {
        productService.deleteProduct(ids);
        return ResponseEntity.ok().build();
    }

    @PutMapping("enable")
    public ResponseEntity<Void> enableProducts(@RequestBody List<String> ids) {
        productService.enable(ids);
        return ResponseEntity.ok().build();
    }

    @PutMapping("disable")
    public ResponseEntity<Void> disableProducts(@RequestBody List<String> ids) {
        productService.disable(ids);
        return ResponseEntity.ok().build();
    }

    @PutMapping("change-category")
    public ResponseEntity<Void> changeCategory(@RequestBody ChangeProductsCategoryDTO dto) {
        productService.changeProductsCategory(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("remove-promotion")
    public ResponseEntity<Void> removePromotions(@RequestBody String id) {
        productService.removePromotion(id);
        return ResponseEntity.ok().build();
    }
}
