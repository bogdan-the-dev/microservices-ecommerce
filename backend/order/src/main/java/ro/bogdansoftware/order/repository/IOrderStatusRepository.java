package ro.bogdansoftware.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.bogdansoftware.order.model.OrderStatus;

public interface IOrderStatusRepository extends JpaRepository<OrderStatus, Integer> {
}
