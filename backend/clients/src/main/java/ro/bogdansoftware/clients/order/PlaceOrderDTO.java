package ro.bogdansoftware.clients.order;

import java.util.List;

public record PlaceOrderDTO(
        List<OrderItemDTO> items
) {
}
