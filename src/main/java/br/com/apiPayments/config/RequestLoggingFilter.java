package br.com.apiPayments.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String queryString = request.getQueryString();
        String fullUrl = request.getRequestURI() + (queryString != null ? "?" + queryString : "");

        log.info("=== START REQUEST ===");
        log.info("URL: {} {}", request.getMethod(), fullUrl);
        log.info("Headers: {}", Collections.list(request.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(h -> h, request::getHeader)));
        log.info("=== END REQUEST ===");

        filterChain.doFilter(request, response);

        log.info("=== RESPONSE STATUS: {} ===", response.getStatus());
    }
}