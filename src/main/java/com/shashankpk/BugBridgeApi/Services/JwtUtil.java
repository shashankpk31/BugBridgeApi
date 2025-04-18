package com.shashankpk.BugBridgeApi.Services;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtUtil {
	@Value("${security.jwt.secret}")
    private String secret; 

    @Value("${security.jwt.expiration}")
    private long expiration;
    
    private Key getSigningKey() {
		return Keys.hmacShaKeyFor(secret.getBytes());
	}
    
    public String generateToken(String email, String role) {
        return Jwts.builder()  // builder for factory class or empty box to set values
                .setSubject(email) 
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

	public Claims getClaimFromToken(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	public String getEmailFromToken(String token) {
		return getClaimFromToken(token).getSubject();
	}
	
	public String getRoleFromToken(String token) {
		return getClaimFromToken(token).get("role", String.class);
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
            return false;
        }
	}
}
