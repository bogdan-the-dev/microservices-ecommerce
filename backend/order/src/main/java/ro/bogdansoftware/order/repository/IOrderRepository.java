package ro.bogdansoftware.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.bogdansoftware.order.model.Order;

public interface IOrderRepository extends JpaRepository<Order, Long> {
}
