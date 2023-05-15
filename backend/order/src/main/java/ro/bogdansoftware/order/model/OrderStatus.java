package ro.bogdansoftware.order.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "order_status")
public class OrderStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "status_id", unique = true)
    private int statusId;

    private String status;
}
