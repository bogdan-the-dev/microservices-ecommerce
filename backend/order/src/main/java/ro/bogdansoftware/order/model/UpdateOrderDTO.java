package ro.bogdansoftware.order.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateOrderDTO {
    private Long orderId;
    private OrderStatus status;
    private String trackingNumber;
}
