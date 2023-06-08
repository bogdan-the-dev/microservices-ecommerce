package ro.bogdansoftware;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ro.bogdansoftware.clients.security.ISecurityClient;

import java.util.List;
import java.util.function.Predicate;

@Component
public class AuthenticationPrefilter extends AbstractGatewayFilterFactory<AuthenticationPrefilter.Config> {

    private final WebClient.Builder webClient;
    private final List<String> authUrl = List.of("api/v1/order/test");


    public AuthenticationPrefilter(WebClient.Builder webClient) {
        this.webClient = webClient;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (isSecured.test(exchange.getRequest())) {
                String token = exchange.getRequest().getHeaders().getFirst("Authorization");
                String result = webClient.build().get().uri("http://SECURITY/api/v1/authorize?token="+token).header("Authorization", token)
                        .retrieve().bodyToMono(String.class).block();

                if(result == null) {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
                }

                ServerWebExchange modifiedExchange = exchange.mutate().request(builder -> {
                    builder.header("auth-user-role", result);
                }).build();
                return chain.filter(modifiedExchange);
            }
            return chain.filter(exchange);
        });
    }

    public Predicate<ServerHttpRequest> isSecured = request -> authUrl.stream().anyMatch(uri -> request.getURI().getPath().contains(uri));

    public static class Config{}
}
