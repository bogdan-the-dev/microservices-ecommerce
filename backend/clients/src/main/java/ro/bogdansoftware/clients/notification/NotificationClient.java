package ro.bogdansoftware.clients.notification;


import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        value = "notification",
        path = "api/v1/category"
)
public interface NotificationClient {
}
