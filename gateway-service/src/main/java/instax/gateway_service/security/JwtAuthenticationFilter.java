 package instax.gateway_service.security;

 import io.jsonwebtoken.JwtParser;
 import io.jsonwebtoken.Jwts;
 import io.jsonwebtoken.security.Keys;
 import org.springframework.cloud.gateway.filter.GatewayFilter;
 import org.springframework.cloud.gateway.filter.GatewayFilterChain;
 import org.springframework.http.HttpStatus;
 import org.springframework.stereotype.Component;
 import org.springframework.web.server.ServerWebExchange;
 import reactor.core.publisher.Mono;

 import java.security.Key;

 @Component
 public class JwtAuthenticationFilter implements GatewayFilter {
  private final Key key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS512);
  private final JwtParser jwtParser = Jwts.parser()
          .setSigningKey(key)
          .build();

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
   String token = extractToken(exchange.getRequest().getHeaders().getFirst("Authorization"));
   String path = exchange.getRequest().getPath().value();

   if (!isPublicPath(path) && token == null) {
    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
    return exchange.getResponse().setComplete();
   }

   if (token != null) {
    try {
     jwtParser.parse(token);
     // Add user email to headers for downstream services
     exchange.getRequest().mutate()
             .header("X-User-Email", jwtParser.parseSignedClaims(token).getPayload().getSubject())
             .build();
    } catch (Exception e) {
     exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
     return exchange.getResponse().setComplete();
    }
   }
   return chain.filter(exchange);
  }

  private String extractToken(String bearerToken) {
   if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
    return bearerToken.substring(7);
   }
   return null;
  }

  private boolean isPublicPath(String path) {
   return path.startsWith("/api/auth") || path.startsWith("/graphql/user") || path.equals("/actuator/health");
  }
 }