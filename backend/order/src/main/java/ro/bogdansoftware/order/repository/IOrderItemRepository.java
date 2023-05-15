package ro.bogdansoftware.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.bogdansoftware.order.model.OrderItem;

public interface IOrderItemRepository extends JpaRepository<OrderItem, Long> {
}
