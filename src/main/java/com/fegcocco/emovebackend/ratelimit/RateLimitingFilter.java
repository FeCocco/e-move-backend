package com.fegcocco.emovebackend.ratelimit;

import com.fegcocco.emovebackend.service.TokenService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.io.IOException;
import java.time.Duration;

public class RateLimitingFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    private final Cache<String, Bucket> buckets = Caffeine.newBuilder()
            .maximumSize(10_000)
            .expireAfterAccess(Duration.ofMinutes(5))
            .build();

    public RateLimitingFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        // não limitar health
        return "OPTIONS".equalsIgnoreCase(request.getMethod())
                || path.equals("/api/health");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        Long userId = tryResolveUserId(request);

        String path = request.getRequestURI();

        String routeType = (path.equals("/api/login") || path.equals("/api/cadastro")) ? "auth" : "general";
        String baseKey = RateLimitKeyResolver.resolveKey(request, userId);
        String key = baseKey + ":" + routeType;

        Bucket bucket = buckets.get(key, k -> newBucketFor(request, userId));

        if (bucket.tryConsume(1)) {
            filterChain.doFilter(request, response);
            return;
        }

        response.setStatus(429);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write("{\"error\":\"too_many_requests\",\"message\":\"Rate limit excedido. Tente novamente em instantes.\"}");
    }

    private Long tryResolveUserId(HttpServletRequest request) {
        String token = tokenService.resolveToken(request);
        if (token == null) return null;
        if (!tokenService.isTokenValid(token)) return null;
        try {
            return tokenService.getUserIdFromToken(token);
        } catch (Exception e) {
            return null;
        }
    }

    private Bucket newBucketFor(HttpServletRequest request, Long userIdOrNull) {
        String path = request.getRequestURI();

        Bandwidth limit;

        // Rotas sensíveis: mais restritas por IP
        if (path.equals("/api/login") || path.equals("/api/cadastro")) {
            // 5 req por minuto
            limit = Bandwidth.classic(
                    5,
                    Refill.intervally(5, Duration.ofMinutes(1))
            );
        } else {
            // usuário autenticado tem limite maior; anônimo menor
            if (userIdOrNull != null) {
                // 120 req por minuto
                limit = Bandwidth.classic(
                        120,
                        Refill.intervally(120, Duration.ofMinutes(1))
                );
            } else {
                // 30 req por minuto
                limit = Bandwidth.classic(
                        30,
                        Refill.intervally(30, Duration.ofMinutes(1))
                );
            }
        }

        return Bucket.builder()
                .addLimit(limit)
                .build();
    }
}