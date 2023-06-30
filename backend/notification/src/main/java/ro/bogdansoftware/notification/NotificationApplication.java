package ro.bogdansoftware.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;



@SpringBootApplication(
        scanBasePackages = {
                "ro.bogdansoftware.notification",
                "ro.bogdansoftware.amqp"
        }
)
@EnableDiscoveryClient
@EnableFeignClients(
        basePackages = "ro.bogdansoftware.clients"
)
public class NotificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class);
    }


}

