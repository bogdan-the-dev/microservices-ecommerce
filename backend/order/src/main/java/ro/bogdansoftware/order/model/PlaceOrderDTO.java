package ro.bogdansoftware.order.model;

import lombok.Data;
import ro.bogdansoftware.clients.order.OrderItemDTO;

import java.util.List;

@Data
public class PlaceOrderDTO {
    private Order order;
    List<OrderItemDTO> orderItemDTOList;
}
