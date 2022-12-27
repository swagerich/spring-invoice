package com.erich.factura.Security.Jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;


@Component
public class JwtTokenUtil {

    @Value("${app.jwt.key}")
    public String key;

    @Value("${app.jwt.expiration}")
    public Long expiration;

    public byte[] secretKeyBase64(){
        SecretKey secretKey = new SecretKeySpec(key.getBytes(),
                SignatureAlgorithm.HS512.getJcaName());
        return Base64.getEncoder().encode(secretKey.getEncoded());
    }
}
