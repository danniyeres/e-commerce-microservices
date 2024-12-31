package org.example.apigateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;


@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    public JwtAuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new RuntimeException("Отсутствует токен JWT");
            }

            String token = authHeader.substring(7);
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey("secret-key")
                        .parseClaimsJws(token)
                        .getBody();

                exchange.getRequest().mutate()
                        .header("user-id", claims.getSubject());
            } catch (Exception e) {
                throw new RuntimeException("Неверный токен");
            }

            return chain.filter(exchange);
        };
    }

    public static class Config {

    }
}
