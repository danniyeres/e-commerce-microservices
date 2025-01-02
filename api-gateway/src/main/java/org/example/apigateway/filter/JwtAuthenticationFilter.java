package org.example.apigateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    private final Key signingKey;

    public JwtAuthenticationFilter() {
        super(Config.class);
        this.signingKey = Keys.hmacShaKeyFor("secret-key".getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return Mono.error(new RuntimeException("Missing or invalid Authorization header"));
            }

            String token = authHeader.substring(7);
            try {
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(signingKey)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                exchange.getRequest().mutate()
                        .header("user-id", claims.getSubject())
                        .build();
            } catch (Exception e) {
                return Mono.error(new RuntimeException("Invalid token"));
            }

            return chain.filter(exchange);
        };
    }

    public static class Config {

    }
}