package ro.bogdansoftware.inventory;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Dictionary;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("get-available-quantity")
    public ResponseEntity<Integer> getAvailableQuantity(@RequestParam("id") String id) {
        return ResponseEntity.ok(inventoryService.getAvailableQuantity(id));
    }

    @PutMapping("order-modify-inventory")
    public ResponseEntity<Boolean> orderModifyInventory(Map<String, Integer> orderItems) {
        return ResponseEntity.ok(inventoryService.orderModifyInventory(orderItems));
    }

    @PostMapping("modify-inventory")
    public ResponseEntity<Void> modifyInventory(@RequestParam("id") String id, @RequestParam("qty") int quantity) {
        inventoryService.modifyInventory(id, quantity);
        return ResponseEntity.ok().build();
    }

    @PutMapping("increment-inventory")
    public ResponseEntity<Void> incrementInventory(String id, int quantity) {
        boolean status = inventoryService.incrementInventory(id, quantity);
        if (status)
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.badRequest().build();
    }

}
