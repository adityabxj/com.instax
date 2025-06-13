// package com.connectsphere.gateway.security;

// import io.jsonwebtoken.JwtParser;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.security.Keys;
// import org.springframework.cloud.gateway.filter.GatewayFilter;
// import org.springframework.cloud.gateway.filter.GatewayFilterChain;
// import org.springframework.http.HttpStatus;
// import org.springframework.stereotype.Component;
// import org.springframework.web.server.ServerWebExchange;
// import reactor.core.publisher.Mono;

// import java.security.Key;

// @Component
// public class JwtAuthenticationFilter implements GatewayFilter {
// private final Key key =
// Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS512);
// private final JwtParser jwtParser = Jwts.parser().verifyWith(key).build();

// @Override
// public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain
// chain) {
// String token =
// extractToken(exchange.getRequest().getHeaders().getFirst("Authorization"));
// if (token == null) {
// exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
// return exchange.getResponse().setComplete();
// }

// try {
// jwtParser.parseSignedClaims(token);
// return chain.filter(exchange);
// } catch (Exception e) {
// exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
// return exchange.getResponse().setComplete();
// }
// }

// private String extractToken(String bearerToken) {
// if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
// return bearerToken.substring(7);
// }
// return null;
// }
// }