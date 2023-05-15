package ro.bogdansoftware.order;

import lombok.AllArgsConstructor;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.stereotype.Service;
import ro.bogdansoftware.clients.order.*;
import ro.bogdansoftware.order.repository.IOrderItemRepository;
import ro.bogdansoftware.order.repository.IOrderRepository;
import ro.bogdansoftware.order.repository.IOrderStatusRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {

    private final IOrderItemRepository orderItemRepository;
    private final IOrderRepository orderRepository;
    private final IOrderStatusRepository orderStatusRepository;

    private final static Log log = LogFactory.getLog(OrderService.class);

    public void placeOrder(PlaceOrderDTO orderDTO) {
        List<String> productIds = orderDTO.items().stream().map(OrderItemDTO::itemId).collect(Collectors.toList());
        //todo send to inventory service to update stock, if everything is ok, save order

        //todo send to product service to get price
    }
}
