package ro.bogdansoftware.order.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true)
    private long id;

    private String username;

    private LocalDateTime orderDate;

    private BigDecimal orderTotal;

    private String trackingNumber;

    private String phoneNumber;

    private String billingAddress;

    private boolean notifySMS;

    private String deliveryAddress;

    private String billingName;

    private String deliveryName;

    private BigDecimal transportCost;

    private String stripeId;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
