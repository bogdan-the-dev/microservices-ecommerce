package ro.bogdansoftware.clients.inventory;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        value = "inventory",
        path = "api/v1/inventory"
)
public interface IInventoryClient {
}
