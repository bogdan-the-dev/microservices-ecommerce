package ro.bogdansoftware.cart_persistence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication@EnableFeignClients(
        basePackages = {"ro.bogdansoftware.clients.product"}
)
public class CartPersistenceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CartPersistenceApplication.class, args);
    }
}
