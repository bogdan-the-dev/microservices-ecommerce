package ro.bogdansoftware.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.bogdansoftware.order.model.Order;

import java.util.List;

public interface IOrderRepository extends JpaRepository<Order, Long> {
    List<Order> getOrdersByUsernameIsOrderByOrderDateDesc(String username);
}
