package ro.bogdansoftware.clients.produt;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        value = "product",
        path = "api/v1/product"
)
public interface IProductClient {

    @PutMapping("change-inventory-status")
    ResponseEntity<Void> changeInventoryStatus(@RequestParam("id") String id, @RequestParam("status") boolean status);
    }
