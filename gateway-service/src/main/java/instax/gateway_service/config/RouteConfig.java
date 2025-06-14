 package instax.gateway_service.config;
 import instax.gateway_service.security.JwtAuthenticationFilter;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.cloud.gateway.route.RouteLocator;
 import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;

 @Configuration
 public class RouteConfig {
  @Autowired
  private JwtAuthenticationFilter jwtAuthenticationFilter;

  @Bean
  public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
   return builder.routes()
           .route("user_service", r -> r.path("/api/auth/**", "/graphql/user")
                   .uri("lb://USER-SERVICE"))
           .route("post_service", r -> r.path("/api/posts/**", "/api/comments/**", "/api/stories/**", "/api/reels/**", "/graphql/post")
                   .filters(f -> f.filter(jwtAuthenticationFilter))
                   .uri("lb://POST-SERVICE"))
           .route("interaction_service", r -> r.path("/api/likes/**", "/api/notifications/**", "/api/chat/**", "/graphql/interaction")
                   .filters(f -> f.filter(jwtAuthenticationFilter))
                   .uri("lb://INTERACTION-SERVICE"))
           .build();
  }
 }