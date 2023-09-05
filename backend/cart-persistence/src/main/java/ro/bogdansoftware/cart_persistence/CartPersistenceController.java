package ro.bogdansoftware.cart_persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.bogdansoftware.clients.product.ProductForCartDTO;
import ro.bogdansoftware.shared.security.VerifyRole;

import java.util.List;

@RestController
@RequestMapping("api/v1/cart")
@RequiredArgsConstructor
public class CartPersistenceController {

    private final CartPersistenceService service;

    @GetMapping("get-cart")
    @VerifyRole("USER")
    public ResponseEntity<List<CartResponseDTO>> getCart(@RequestHeader("USERNAME") String username) {
        return ResponseEntity.ok(service.getCart(username));
    }

    @PutMapping("add-item")
    @VerifyRole("USER")
    public ResponseEntity<Void> addItem(@RequestBody CartItem item, @RequestHeader("USERNAME") String username) {
        service.addItem(item, username);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("remove-item")
    @VerifyRole("USER")
    public ResponseEntity<Void> removeItem(@RequestParam(name = "itemId") String itemId, @RequestHeader("USERNAME") String username) {
        service.removeItem(itemId, username);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("empty-cart")
    @VerifyRole("USER")
    public ResponseEntity<Void> emptyCart(@RequestHeader("USERNAME") String username) {
        service.emptyCart(username);
        return ResponseEntity.noContent().build();
    }
}
