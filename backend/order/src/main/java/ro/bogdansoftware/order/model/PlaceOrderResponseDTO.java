package ro.bogdansoftware.order.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlaceOrderResponseDTO {
    private boolean valid;
    private Long orderId;
}
