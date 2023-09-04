package ro.bogdansoftware.clients.inventory;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        value = "inventory",
        path = "api/v1/inventory"
)
public interface IInventoryClient {
    @PostMapping("create-inventory")
    ResponseEntity<Void> createInventory(@RequestBody InventoryDTO inventoryDTO);

    @DeleteMapping("delete-inventory")
    ResponseEntity<Void> deleteInventory(@RequestParam(value = "id") String id);

    @PutMapping("order-modify-inventory")
    ResponseEntity<Boolean> orderModifyInventory(@RequestBody ListOfInventoryItemsDTO requestDTO);
}
