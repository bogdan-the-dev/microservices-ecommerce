package ro.bogdansoftware.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.bogdansoftware.order.model.OrderItem;

import java.util.List;
import java.util.Optional;

public interface IOrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findOrderItemsByItemIdIsAndOrderUsernameIs(String itemId, String username);

    List<OrderItem> findOrderItemsByOrderIdIs(long orderId);
}
