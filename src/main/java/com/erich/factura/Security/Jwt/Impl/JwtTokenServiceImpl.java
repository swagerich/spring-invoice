package com.erich.factura.Security.Jwt.Impl;

import com.erich.factura.Security.Jwt.JwtTokenUtil;
import com.erich.factura.Security.Jwt.SimpleGrantedAuthorityMixin;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtTokenServiceImpl implements JwtToken {

    private static final String BEARER = "Bearer ";
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public String create(Authentication auth) throws IOException {

        String username = ((User) auth.getPrincipal()).getUsername();


        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        Claims claims = Jwts.claims();
        claims.put("authorities",new ObjectMapper().writeValueAsString(authorities));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS512,jwtTokenUtil.secretKeyBase64())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (jwtTokenUtil.expiration) * 2)).compact();
    }

    @Override
    public boolean validate(String token) {
        try {
             getClaims(token);
            return true;
        } catch (JwtException e) {
            log.error("Token invalido");
            return false;
        }
    }

    @Override
    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtTokenUtil.secretKeyBase64())
                .parseClaimsJws(resolve(token))
                .getBody();
    }

    @Override
    public String getUsername(String token) {
       return getClaims(token).getSubject();
    }

    @Override
    public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException {
        Object roles = getClaims(token).get("authorities");
        return Arrays.asList(new ObjectMapper()
                .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
                .readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));
    }

    @Override
    public String resolve(String token) {
        if(token != null && token.startsWith(BEARER)){
            return token.replace(BEARER, "");
        }
        return null;
    }
}
