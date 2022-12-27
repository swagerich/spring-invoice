package com.erich.factura.Security.Jwt;

import com.erich.factura.Security.Jwt.Impl.JwtToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;


import java.io.IOException;


@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private static final String BEARER = "Bearer ";
    private final JwtToken jwtToken;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,JwtToken jwtToken) {
        super(authenticationManager);
        this.jwtToken = jwtToken;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if (!validationAuthenticate(header)) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authenticationToken = null;

        if (jwtToken.validate(header)) {
            authenticationToken = new UsernamePasswordAuthenticationToken(jwtToken.getUsername(header), null, jwtToken.getRoles(header));
        }
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    protected boolean validationAuthenticate(String header) {
        return header != null && StringUtils.startsWithIgnoreCase(header,BEARER);
    }
}
