// package com.connectsphere.gateway.config;

// import com.connectsphere.gateway.security.JwtAuthenticationFilter;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.cloud.gateway.route.RouteLocator;
// import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// @Configuration
// public class RouteConfig {
// @Autowired
// private JwtAuthenticationFilter jwtAuthenticationFilter;

// @Bean
// public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
// return builder.routes()
// .route("auth_route", r -> r.path("/api/auth/**")
// .uri("lb://USER-SERVICE"))
// .route("post_service", r -> r.path("/api/posts/**", "/api/comments/**")
// .filters(f -> f.filter(jwtAuthenticationFilter))
// .uri("lb://POST-SERVICE"))
// .route("user_service", r -> r.path("/api/users/**")
// .filters(f -> f.filter(jwtAuthenticationFilter))
// .uri("lb://USER-SERVICE"))
// .route("interaction_service", r -> r.path("/api/likes/**",
// "/api/notifications/**")
// .filters(f -> f.filter(jwtAuthenticationFilter))
// .uri("lb://INTERACTION-SERVICE"))
// .build();
// }
// }