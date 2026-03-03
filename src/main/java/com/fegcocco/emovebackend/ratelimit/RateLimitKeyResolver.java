package com.fegcocco.emovebackend.ratelimit;

import jakarta.servlet.http.HttpServletRequest;

public final class RateLimitKeyResolver {

    private RateLimitKeyResolver() {}

    public static String resolveKey(HttpServletRequest request, Long userIdOrNull) {
        if (userIdOrNull != null) {
            return "user:" + userIdOrNull;
        }
        // fallback: IP
        String xff = request.getHeader("X-Forwarded-For");
        if (xff != null && !xff.isBlank()) {
            // pode vir "ip1, ip2, ..."
            return "ip:" + xff.split(",")[0].trim();
        }
        return "ip:" + request.getRemoteAddr();
    }
}