package ro.bogdansoftware.order;

import com.stripe.exception.StripeException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.bogdansoftware.order.model.CheckoutPayment;
import ro.bogdansoftware.order.model.PlaceOrderDTO;
import ro.bogdansoftware.order.model.PlaceOrderResponseDTO;
import ro.bogdansoftware.shared.security.VerifyRole;

import java.net.URI;
import java.util.Map;

@Controller
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<PlaceOrderResponseDTO> placeOrder(@RequestBody PlaceOrderDTO orderDTO) {
        return ResponseEntity.ok(this.orderService.placeOrder(orderDTO));
    }

    @PutMapping("/cancel")
    public ResponseEntity<Void> cancel(@RequestParam String orderId) {

        return ResponseEntity.ok().build();
    }

    @PostMapping("/pay")
    public ResponseEntity<Map<String, String>> pay(@RequestBody CheckoutPayment payment) throws StripeException {
        return ResponseEntity.ok(orderService.pay(payment));
    }


    @GetMapping("/test")
    @VerifyRole("ADMIN")
    public ResponseEntity<Void> test() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/test2")
    @VerifyRole("USER")
    public ResponseEntity<Void> test2() {
        return ResponseEntity.ok().build();
    }
}

