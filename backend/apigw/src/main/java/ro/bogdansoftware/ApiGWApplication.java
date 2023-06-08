package ro.bogdansoftware;

import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import reactor.netty.http.client.HttpClient;


@SpringBootApplication
@EnableDiscoveryClient
public class ApiGWApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGWApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("product", r -> r.path("/api/v1/products/**").uri("lb://PRODUCT"))
                .route("category", r -> r.path("/api/v1/category/**").uri("lb://CATEGORY"))
                .route("inventory", r -> r.path("/api/v1/inventory/**").uri("lb://INVENTORY"))
                .route("security", r -> r.path("/api/v1/auth/**").uri("lb://SECURITY"))
                .route("order", r -> r.path("/api/v1/order/**").uri("lb://ORDER"))
                .build();
    }
}