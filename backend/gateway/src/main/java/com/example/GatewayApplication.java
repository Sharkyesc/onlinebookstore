package com.example;

import com.example.filters.JwtCheckFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    /*
     * @Autowired
     * JwtCheckFilter jwtCheckFilter;
     */

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/author/**")
                        .filters(f -> f.rewritePath("/author", ""))
                        .uri("lb://AUTHOR-SERVICE"))
                .route(r -> r.path("/bookPrice")
                        .uri("lb://PRICE-SERVICE"))
                .route(r -> r.path("/**")
                        .uri("lb://MAIN-SERVICE"))
                .build();
    }
}
