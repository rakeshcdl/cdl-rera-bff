package com.cdl.escrow.audit;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class AuditContextFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String username = request.getHeader("X-User"); if (username == null || username.isBlank()) username = "anonymous";
            String ip = request.getHeader("X-Forwarded-For"); if (ip == null || ip.isBlank()) ip = request.getRemoteAddr();
            String ua = request.getHeader("User-Agent");
            String reqId = request.getHeader("X-Request-ID"); if (reqId == null || reqId.isBlank()) reqId = UUID.randomUUID().toString();
            AuditContext.set(username, ip, ua, reqId);
            filterChain.doFilter(request, response);
        } finally {
            AuditContext.clear();
        }
    }
}