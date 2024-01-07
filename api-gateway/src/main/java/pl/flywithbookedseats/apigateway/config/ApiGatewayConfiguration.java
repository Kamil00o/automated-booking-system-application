package pl.flywithbookedseats.apigateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.flywithbookedseats.apigateway.filter.JWTAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
public class ApiGatewayConfiguration {

    private final JWTAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder routeLocatorBuilder) {

        return routeLocatorBuilder.routes()
                .route(predicateSpec -> predicateSpec
                        .path("/passengers/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://passenger-account-service"))
                .route(predicateSpec -> predicateSpec
                        .path("/seats-booking/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://seats-booking-system-service"))
                .route(predicateSpec -> predicateSpec
                        .path("/seats-scheme/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://seats-booking-system-service"))
                .route(predicateSpec -> predicateSpec
                        .path("/auth/**")
                        .uri("lb://auth-service"))
                .build();
    }

}
