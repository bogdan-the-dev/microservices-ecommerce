package ro.bogdansoftware.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(
        scanBasePackages = {
                "ro.bogdansoftware.security",
                "ro.bogdansoftware.amqp"
        }
)
@EnableDiscoveryClient
public class SecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class);
    }
}
