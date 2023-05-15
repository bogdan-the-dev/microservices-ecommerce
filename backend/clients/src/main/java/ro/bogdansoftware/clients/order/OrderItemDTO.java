package ro.bogdansoftware.clients.order;

public record OrderItemDTO(
        String itemId,
        int quantity
) {
}
