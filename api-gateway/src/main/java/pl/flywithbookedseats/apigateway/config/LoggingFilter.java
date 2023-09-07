package pl.flywithbookedseats.apigateway.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;



@Component
public class LoggingFilter implements GlobalFilter {


    private final static Logger logger = LoggerFactory.getLogger(LoggingFilter.class);
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        logger.info("Path of the received request from client: {}", exchange.getRequest().getPath());
        return chain.filter(exchange);
    }
}
