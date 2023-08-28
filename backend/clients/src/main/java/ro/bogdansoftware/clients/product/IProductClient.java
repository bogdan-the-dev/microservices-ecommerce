package ro.bogdansoftware.clients.product;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
        value = "product",
        path = "api/v1/products"
)
public interface IProductClient {

    @PutMapping("change-inventory-status")
    ResponseEntity<Void> changeInventoryStatus(@RequestParam("id") String id, @RequestParam("status") boolean status);

    @PutMapping("get-product-names")
    ResponseEntity<List<String>> getProductNames(@RequestBody List<String> ids);
}
