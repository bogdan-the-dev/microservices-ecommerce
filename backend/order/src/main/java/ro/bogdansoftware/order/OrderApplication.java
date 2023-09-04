package ro.bogdansoftware.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(
        scanBasePackages = {
                "ro.bogdansoftware.order",
                "ro.bogdansoftware.amqp"
        }
)
@EnableDiscoveryClient
@EnableFeignClients(
        basePackages = {"ro.bogdansoftware.clients.inventory", "ro.bogdansoftware.clients.product"}
)
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class);
    }
}
