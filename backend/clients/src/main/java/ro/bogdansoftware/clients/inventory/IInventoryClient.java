package ro.bogdansoftware.clients.inventory;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        value = "inventory",
        path = "api/v1/inventory"
)
public interface IInventoryClient {
    @PostMapping("create-inventory")
    ResponseEntity<Void> createInventory(@RequestBody InventoryDTO inventoryDTO);

    @DeleteMapping("delete-inventory")
    ResponseEntity<Void> deleteInventory(@RequestParam(value = "id") String id);
}
