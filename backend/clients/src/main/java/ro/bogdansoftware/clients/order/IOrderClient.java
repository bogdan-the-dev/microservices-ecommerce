package ro.bogdansoftware.clients.order;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        value = "order",
        path = "api/v1/order"
)
public interface IOrderClient {
}
