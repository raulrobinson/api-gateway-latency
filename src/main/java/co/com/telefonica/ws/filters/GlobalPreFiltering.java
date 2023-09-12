package co.com.telefonica.ws.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@Configuration
public class GlobalPreFiltering implements GlobalFilter {

    @Value("${allowed.origin}")
    private String allowedOrigin;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Global Pre Filter executed");
        return chain.filter(exchange);
    }

    @Bean
    public GlobalFilter customGlobalPreFilter() {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("☺ Cookies: " + exchange.getRequest().getCookies());
            log.info("☺ Local Address: " + exchange.getRequest().getLocalAddress());
            log.info("☺ Remote Address: " + exchange.getRequest().getRemoteAddress());
            log.info("☺ Path: " + exchange.getRequest().getPath());
            log.info("☺ SSL Info: " + exchange.getRequest().getSslInfo());
            log.info("☺ Id: " + exchange.getRequest().getId());
            log.info("☺ Query Params: " + exchange.getRequest().getQueryParams());

            HttpHeaders headers = request.getHeaders();
            String origin = headers.getFirst(HttpHeaders.ORIGIN);
            log.info("☺ Request Origin: " + origin);
            log.info("☺ Request Headers: " + request.getHeaders());

            log.info(" --------------------------------------------------- ");
            log.info("☺ Response Cookies: " + exchange.getResponse().getCookies());
            log.info("☺ Response Headers: " + exchange.getResponse().getHeaders());
            log.info(" --------------------------------------------------- ");

            if (!originMatchesAllowedOrigin(origin)) {
                response.setStatusCode(HttpStatus.FORBIDDEN);
                return Mono.empty();
            }

            return chain.filter(exchange);
        };
    }

    private boolean originMatchesAllowedOrigin(String origin) {
        return allowedOrigin.equals(origin);
    }

}
