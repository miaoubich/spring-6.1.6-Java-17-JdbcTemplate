package com.miaoubich.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret.key}")
    private String secretKey;

    public String extractUsername(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    private <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);

    }

    private Claims extractAllClaims(String jwtToken) {
        return Jwts
                    .parser() // user jjwt 0.12.3 not 0.11.x with parserBuilder()
                    .verifyWith(getSigningKey()) // use jjwt 0.12.3 not 0.11.x with setSigningKey
                    .build()
                    .parseSignedClaims(jwtToken)
                    .getPayload(); // user jjwt 0.12.3 not 0.11.x with getBody()
    }

    // user jjwt 0.12.3 not 0.11.x with Key
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Generate token from username of userDetails
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    private <K, V> String generateToken(HashMap<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                        .builder()
                        .claims(extraClaims)
                        .subject(userDetails.getUsername())
                        .issuedAt(new Date(System.currentTimeMillis()))
                        .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 hours
                        .signWith(getSigningKey())
                        .compact();
    }

    public boolean isValidateToken(String jwtToken, UserDetails userDetails) {
        final String username = extractUsername(jwtToken);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(jwtToken);
    }

    private boolean isTokenExpired(String jwtToken) {
    return extractExpirationDateFromToken(jwtToken).before(new Date());
    }

    private Date extractExpirationDateFromToken(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }
}
