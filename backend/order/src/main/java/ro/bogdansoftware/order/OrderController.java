package ro.bogdansoftware.order;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.bogdansoftware.clients.order.PlaceOrderDTO;

import java.net.URI;

@Controller
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/place-order")
    public ResponseEntity<Void> placeOrder(PlaceOrderDTO orderDTO) {
        this.orderService.placeOrder(orderDTO);
        return ResponseEntity.created(URI.create("")).build();
    }
}
