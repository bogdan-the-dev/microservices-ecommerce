package ro.bogdansoftware.clients.order;

import java.util.List;

public record PlaceOrderClientDTO(
        List<OrderItemDTO> items
) {
}
