package ro.bogdansoftware.order.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class MyOrderDTO {
    private Long id;
    private LocalDateTime date;
    private BigDecimal total;
    private OrderStatus status;
    private String trackingNumber;

    public static MyOrderDTO convert(Order o) {
        return MyOrderDTO.builder()
                .id(o.getId())
                .date(o.getOrderDate())
                .total(o.getOrderTotal())
                .status(o.getStatus())
                .trackingNumber(o.getTrackingNumber())
                .build();
    }
}
