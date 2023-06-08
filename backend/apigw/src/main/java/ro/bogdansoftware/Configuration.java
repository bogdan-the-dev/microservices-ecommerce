package ro.bogdansoftware;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, AuthenticationPrefilter authFilter) {
        return builder.routes()
                .route("product", r -> r.path("/api/v1/products/**").uri("lb://PRODUCT"))
                .route("category", r -> r.path("/api/v1/category/**").uri("lb://CATEGORY"))
                .route("inventory", r -> r.path("/api/v1/inventory/**").uri("lb://INVENTORY"))
                .route("security", r -> r.path("/api/v1/auth/**").uri("lb://SECURITY"))
                .route("order", r -> r.path("/api/v1/order/**")
                        .filters(f -> f.filter(authFilter.apply(new AuthenticationPrefilter.Config())))
                        .uri("lb://ORDER"))
                .build();
    }


    @Bean
    @LoadBalanced
    public WebClient.Builder getWebClientBuilder() {
        return WebClient.builder();
    }
}
