package com.example.amdscs.api.filters;

import com.example.amdscs.exceptions.ApiError;
import com.example.amdscs.util.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtil;
    private final ObjectMapper mapper;

    public JwtAuthorizationFilter(JwtUtils jwtUtil, ObjectMapper mapper) {
        this.jwtUtil = jwtUtil;
        this.mapper = mapper;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Map<String, Object> errorDetails = new HashMap<>();

        try {
            String accessToken = jwtUtil.resolveToken(request);
            if (accessToken == null ) {
                filterChain.doFilter(request, response);
                return;
            }
            Claims claims = jwtUtil.resolveClaims(request);

            if(claims != null & jwtUtil.validateClaims(claims)){
                String email = jwtUtil.getEmail(claims);
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                for(var role : jwtUtil.getRoles(claims)) {
                    authorities.add(new SimpleGrantedAuthority(role));
                }
                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(email,"", authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }catch (Exception e){
            errorDetails.put("success", false);
            errorDetails.put("error", "Authentication Error: " + e.getMessage());
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            mapper.writeValue(response.getWriter(), errorDetails);
        }
        filterChain.doFilter(request, response);
    }
}