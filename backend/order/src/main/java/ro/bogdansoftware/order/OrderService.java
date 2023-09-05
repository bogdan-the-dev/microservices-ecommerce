package ro.bogdansoftware.order;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.stereotype.Service;
import ro.bogdansoftware.amqp.RabbitMQMessageProducer;
import ro.bogdansoftware.clients.inventory.IInventoryClient;
import ro.bogdansoftware.clients.inventory.ListOfInventoryItemsDTO;
import ro.bogdansoftware.clients.notification.NotificationType;
import ro.bogdansoftware.clients.notification.SendNotificationRequest;
import ro.bogdansoftware.clients.order.OrderItemDTO;
import ro.bogdansoftware.clients.product.IProductClient;
import ro.bogdansoftware.order.model.*;
import ro.bogdansoftware.order.repository.IOrderItemRepository;
import ro.bogdansoftware.order.repository.IOrderRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final IOrderItemRepository orderItemRepository;
    private final IOrderRepository orderRepository;
    private final IInventoryClient inventoryClient;
    private final IProductClient productClient;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    private static final String STRIPE_KEY = "sk_test_51NJdFwKsuRPrWUFjfG05fJdEeb5KsLKahF7vNzbURvGhXPcAURWDcKUsei7nVxt4ceSF052hL8Hl161bm1srcTR400Bh2SQnzi";
    private final static Log log = LogFactory.getLog(OrderService.class);

    public Map<String, String> pay(CheckoutPayment payment) throws StripeException {
        Stripe.apiKey = STRIPE_KEY;
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT).setSuccessUrl("http://localhost:4200/payment/success")
                .setCancelUrl("http://localhost:4200/payment/fail")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(payment.getQuantity())
                                .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                        .setCurrency(payment.getCurrency())
                                        .setUnitAmount(payment.getAmount())
                                        .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder().setName(payment.getName()).build()).build()).build()

                ).build();
        Session session = Session.create(params);
        Map<String, String> responseData = new HashMap<>();
        responseData.put("id", session.getId());
        var orderOpt = orderRepository.findById(Long.valueOf(payment.getName().substring(payment.getName().lastIndexOf(' ') + 1)));
        var order = orderOpt.get();
        order.setStripeId(session.getId());
        order.setStatus(OrderStatus.PENDING_PAYMENT);
        orderRepository.saveAndFlush(order);
        return responseData;
    }

    public void edit(UpdateOrderDTO dto) {
        var order = this.orderRepository.findById(dto.getOrderId()).get();
        order.setStatus(dto.getStatus());
        order.setTrackingNumber(dto.getTrackingNumber());
        orderRepository.saveAndFlush(order);
    }

    public boolean hasUserBoughtItem(String username, String productId) {
        return orderItemRepository.findOrderItemsByItemIdIsAndOrderUsernameIs(productId, username).size()>0;
    }

    public List<MyOrderDTO> getMyOrders(String username) {
        return orderRepository.getOrdersByUsernameIs(username).stream().map(MyOrderDTO::convert).collect(Collectors.toList());
    }

    public List<MyOrderDTO> getAll() {
        return orderRepository.findAll().stream().map(MyOrderDTO::convert).collect(Collectors.toList());
    }

    public void cancel(long orderId) {
        Order o = orderRepository.findById(orderId).get();
        o.setStatus(OrderStatus.CANCELED);
        List<OrderItem> items = orderItemRepository.findOrderItemsByOrderIdIs(orderId);

        for(OrderItem item: items) {
            inventoryClient.returnInventory(item.getItemId(), item.getQuantity());
        }

        orderRepository.saveAndFlush(o);
    }

    public PlaceOrderResponseDTO placeOrder(PlaceOrderDTO orderDTO) {
        List<String> productIds = orderDTO.getOrderItemDTOList().stream().map(OrderItemDTO::itemId).collect(Collectors.toList());
        var productsSpecs = productClient.getProductsPrices(productIds).getBody();
        Map<String, Integer> items = new HashMap<>();
        for(OrderItemDTO item: orderDTO.getOrderItemDTOList()) {
            items.put(item.itemId(), item.quantity());
        }
        var res = inventoryClient.orderModifyInventory(new ListOfInventoryItemsDTO(items));
        if(!res.getBody()) {
            return PlaceOrderResponseDTO.builder().valid(false).build();
        }
        orderDTO.getOrder().setOrderDate(LocalDateTime.now());
        orderDTO.getOrder().setStatus(OrderStatus.PLACED);
        var savedOrder = orderRepository.saveAndFlush(orderDTO.getOrder());

        for(OrderItemDTO item: orderDTO.getOrderItemDTOList()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setQuantity(item.quantity());
            orderItem.setItemId(item.itemId());
            orderItem.setPrice(productsSpecs.get(item.itemId()));
            orderItemRepository.saveAndFlush(orderItem);
        }

        if(savedOrder.isNotifySMS()) {
            SendNotificationRequest requestSMS = new SendNotificationRequest("The order with the id " + savedOrder.getId() + " was placed", NotificationType.SMS,savedOrder.getUsername(), "Order placed", savedOrder.getPhoneNumber());
            rabbitMQMessageProducer.publish(requestSMS, "internal.exchange", "internal.notification.routing-key");
        }

        //todo send to inventory service to update stock, if everything is ok, save order

        //todo send to product service to get price
        return PlaceOrderResponseDTO.builder().valid(true).orderId(savedOrder.getId()).build();
    }
}
