package com.bci.auth.utils;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtUtils {

    private String SECRET_KEY = "secret";

    public String generateToken(String source) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, source);
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            System.err.println("Firma del JWT no válida: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.err.println("JWT no válido: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.err.println("JWT no soportado: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("JWT vacío: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            // Ignora la excepción de JWT expirado y devuelve true
            return true;
        }
        return false;
    }
}
