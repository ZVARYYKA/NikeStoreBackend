package dev.zvaryyka.nikestorebackend.security.jwt;

import dev.zvaryyka.nikestorebackend.security.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.secret}")
    private String jwtSecret;
    private final long jwtExpirationMs = 86400000;
    private Key key;

    private Key getKey() {
        if (key == null) {
            byte[] decodedKey = jwtSecret.getBytes();
            key = Keys.hmacShaKeyFor(decodedKey);
        }
        return key;
    }

    private Claims parseJwt(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return parseJwt(token).getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            parseJwt(authToken);
            return true;
        } catch (JwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }
}
