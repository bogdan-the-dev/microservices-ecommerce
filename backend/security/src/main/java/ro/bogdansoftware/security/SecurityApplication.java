package ro.bogdansoftware.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import ro.bogdansoftware.security.dto.RegisterRequestDTO;

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


    @Bean
    public CommandLineRunner runner(SecurityService service) {
        return args -> {
            if(!service.userExists("admin@everythingonline.com")) {
                service.createAdmin(new RegisterRequestDTO("admin", "admin@everythingonline.com", "password"));
            }
        };
    }
}
