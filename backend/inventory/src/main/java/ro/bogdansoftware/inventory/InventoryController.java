package ro.bogdansoftware.inventory;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.bogdansoftware.clients.inventory.InventoryDTO;
import ro.bogdansoftware.clients.inventory.ListOfInventoryItemsDTO;

import java.net.URI;
import java.util.List;

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
    public ResponseEntity<Boolean> orderModifyInventory(@RequestBody ListOfInventoryItemsDTO requestDTO) {
        return ResponseEntity.ok(inventoryService.orderModifyInventory(requestDTO.orderItems()));
    }

    @PutMapping("modify-inventory")
    public ResponseEntity<Void> modifyInventory(@RequestParam("id") String id, @RequestParam("qty") int quantity) {
        inventoryService.modifyInventory(id, quantity);
        return ResponseEntity.ok().build();
    }

    @PostMapping("create-inventory")
    public ResponseEntity<Void> createInventory(@RequestBody InventoryDTO inventoryDTO) {
        inventoryService.createInventory(inventoryDTO);
        return ResponseEntity.created(URI.create("")).build();
    }

    @DeleteMapping("delete-inventory")
    public ResponseEntity<Void> deleteInventory(@RequestParam(value = "id") String id) {
        inventoryService.deleteInventory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("get-all")
    public ResponseEntity<List<InventoryItemDTO>> getAll() {
        var res = inventoryService.getAllInventory();
        return ResponseEntity.ok(res);
    }

}
