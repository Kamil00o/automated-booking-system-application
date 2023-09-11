package pl.flywithbookedseats.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder routeLocatorBuilder) {

        return routeLocatorBuilder.routes()
                .route(predicateSpec -> predicateSpec
                        .path("/passenger-account/**")
                        .uri("lb://passenger-account-service"))
                .route(predicateSpec -> predicateSpec
                        .path("/seats-booking/**")
                        .uri("lb://seats-booking-system-service"))
                .build();
    }

}
