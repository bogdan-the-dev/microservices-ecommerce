package ro.bogdansoftware.order.model;

public enum OrderStatus {
    PLACED,
    PENDING_PAYMENT,
    PAYMENT_ACCEPTED,
    PREPARATION,
    READY_FOR_SHIPPING,
    SHIPPED,
    RECEIVED
}
