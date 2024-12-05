package com.proje.healpoint.jwt;

import com.proje.healpoint.model.Patients;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtService {

    private final String SECRET_KEY="BkHrpdinMVpyGgBlPTMDfO7Tv5PRZQb6URKfdyHN1zA";

    public String generateToken(String patientTc) {
        return Jwts.builder()
                .setSubject(patientTc)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
                .compact();

    }

    public String extractPatientTc(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean isTokenValid(String token) {
        try{
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        }catch(JwtException | IllegalArgumentException e){
            return false;
        }

    }
}
