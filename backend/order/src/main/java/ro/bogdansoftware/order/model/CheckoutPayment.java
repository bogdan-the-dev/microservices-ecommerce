package ro.bogdansoftware.order.model;

import lombok.Data;

@Data
public class CheckoutPayment {
    private String name;
    private String currency;
    private long amount;
    private long quantity;
}
