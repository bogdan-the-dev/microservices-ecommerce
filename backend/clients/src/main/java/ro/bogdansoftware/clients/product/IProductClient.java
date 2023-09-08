package ro.bogdansoftware.clients.product;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ro.bogdansoftware.clients.product.ProductForCartDTO;

import java.math.BigDecimal;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;

@FeignClient(
        value = "product",
        path = "api/v1/products"
)
public interface IProductClient {

    @PutMapping("change-inventory-status")
    ResponseEntity<Void> changeInventoryStatus(@RequestParam("id") String id, @RequestParam("status") boolean status);

    @PutMapping("get-product-names")
    ResponseEntity<List<String>> getProductNames(@RequestBody List<String> ids);

    @PostMapping("get-products-prices")
    ResponseEntity<Map<String, BigDecimal>> getProductsPrices(@RequestBody List<String> productsIds);

    @PutMapping("remove-promotion")
    ResponseEntity<Void> removePromotions(@RequestBody String id);

    @PutMapping("get-product-cart")
    ResponseEntity<List<ProductForCartDTO>> getProductForCart(@RequestBody List<String> ids);

    @PutMapping("change-category")
    ResponseEntity<Void> changeCategory(@RequestBody ChangeProductsCategoryDTO dto);

    @PutMapping("category-deleted")
    ResponseEntity<Void> categoryDeleted(@RequestParam(name = "category") String category, @RequestParam (name = "subcategory") String subcategory);
}
