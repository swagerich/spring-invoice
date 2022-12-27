package com.erich.factura.Security.Jwt;


import com.erich.factura.Security.Jwt.Impl.JwtToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


import java.io.IOException;
import java.util.*;

@Slf4j
public class JwtRequestFilter extends UsernamePasswordAuthenticationFilter {
    private static final String BEARER = "Bearer ";

    private final JwtToken jwtToken;
    private final AuthenticationManager authenticationManager;

    public JwtRequestFilter(AuthenticationManager authenticationManager,JwtToken jwtToken) {
        this.authenticationManager = authenticationManager;
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login", "POST"));
        this.jwtToken = jwtToken;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter(SPRING_SECURITY_FORM_USERNAME_KEY);
        username = username != null ? username.trim() : null;
        String password = request.getParameter(SPRING_SECURITY_FORM_PASSWORD_KEY);
        password = password != null ? password : "";
        if(username == null){
            try {
                com.erich.factura.Entity.User user = new ObjectMapper().readValue(request.getInputStream(),com.erich.factura.Entity.User.class);
                username = user.getUsername();
                password = user.getPassword();
                log.info("Username utilizando request InputStream (raw)" + username);
                log.info("Passwrod utilizando request InputStream (raw)" + password);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
        return authenticationManager.authenticate(authRequest);
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {

        String token = jwtToken.create(authResult);

        response.addHeader("Authorization", BEARER + token);
        Map<String, Object> body = new HashMap<>();
        body.put("token", token);
        body.put("user", authResult.getPrincipal());
        body.put("mensaje", String.format("hola %s, has iniciado session con exito!", authResult.getName()));
        response.getWriter().write(new ObjectMapper().writeValueAsString(body)); //convertimos a un json/convertimos a un json
        response.setStatus(200);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        Map<String, Object> body = new HashMap<>();
        body.put("mensaje","Error de Authenticacion: username o password incorrecto");
        body.put("error",failed.getMessage());
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(401);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    }
}
