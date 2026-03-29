package com.example.TaskManagement.filter;

import com.example.TaskManagement.model.UserLogin;
import com.example.TaskManagement.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
public class JwtFilterChain extends OncePerRequestFilter {
    @Autowired
    JwtService jwtService;
    @Autowired
    UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer ")){
            String token = header.substring(7);
            String usernameToken = jwtService.extractUsername(token);
            if(usernameToken != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = userDetailsService.loadUserByUsername(usernameToken);
                if(jwtService.isTokenValid(token, userDetails.getUsername())){
                    UsernamePasswordAuthenticationToken userPassToken =
                            new UsernamePasswordAuthenticationToken
                                    (userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(userPassToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
