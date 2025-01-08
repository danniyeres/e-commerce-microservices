package org.example.apigateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    public AuthFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getPath().toString();

            // Исключение маршрутов, которые не требуют аутентификации
            if (path.startsWith("/auth") || path.startsWith("/public")) {
                return chain.filter(exchange);
            }

            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new RuntimeException("Отсутствует заголовок Authorization");
            }

            String token = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            } else {
                throw new RuntimeException("Некорректный токен");
            }

            try {
                Claims claims = validateToken(token);
                exchange.getRequest().mutate().header("X-auth-user-id", claims.getSubject());
            } catch (Exception e) {
                throw new RuntimeException("Токен недействителен или истек");
            }

            return chain.filter(exchange);
        };
    }


    private Claims validateToken(String token) {
        return Jwts.parser()
                .setSigningKey("MagnetoAndWolverineAreTheBestMarvelCharacters")
                .parseClaimsJws(token)
                .getBody();
    }

    public static class Config {
        // Конфигурационные параметры (если нужны)
    }
}
