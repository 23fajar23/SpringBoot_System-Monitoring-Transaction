package com.monitor.transaction.security;

import com.monitor.transaction.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            //validasi token jwt
            String headerAuth = request.getHeader(HttpHeaders.AUTHORIZATION);
            String token = null;
            if (headerAuth != null && headerAuth.startsWith("Bearer ")){
                token = headerAuth.substring(7);
            }
            if (token != null && jwtUtil.verifyJwtToken(token)){
                //set auth ke spring security
                Map<String, String> userInfo = jwtUtil.getUserInfoByToken(token);
                UserDetails user = userService.loadUserByUserId(userInfo.get("userId"));

                //validasi token
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                //menambahkan informasi berupa alamat IP ke security
                authenticationToken.setDetails(new WebAuthenticationDetailsSource());

                //menyimpan auth ke spring
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }catch (Exception e){
            e.getMessage();
        }
        filterChain.doFilter(request,response);
    }
}