package ro.bogdansoftware;

import io.opentelemetry.exporter.zipkin.ZipkinSpanExporter;
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
                .route("review", r -> r.path("/api/v1/reviews/get-reviews", "/api/v1/reviews/verify-review-present","/api/v1/reviews/add-review", "/api/v1/reviews/get-rating", "/api/v1/reviews/delete-individual-review")
                        .filters(f -> f.filter(authFilter.apply(new AuthenticationPrefilter.Config())))
                        .uri("lb://REVIEW"))
                .route("cart", r -> r.path("/api/v1/cart/**")
                        .filters(f -> f.filter(authFilter.apply(new AuthenticationPrefilter.Config())))
                        .uri("lb://CART-PERSISTENCE"))
                .route("promotion", r -> r.path("/api/v1/promotion/**")
                        .filters(f -> f.filter(authFilter.apply(new AuthenticationPrefilter.Config())))
                        .uri("lb://PROMOTION"))
                .build();
    }


    @Bean
    public ZipkinSpanExporter zipkinSpanExporter() {
        return ZipkinSpanExporter.builder().setEndpoint("http://zipkin:9411")
                .build();
    }

    @Bean
    @LoadBalanced
    public WebClient.Builder getWebClientBuilder() {
        return WebClient.builder();
    }
}
