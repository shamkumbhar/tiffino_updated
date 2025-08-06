//package com.tiffino.orderservice.util;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.JwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.stereotype.Component;
//
//import java.security.Key;
//import java.util.Date;
//import java.util.List;
//
//@Component
//public class JwtToken {
//
//
//    private final String SECRET = "mysupersecretkeyforjwttokenexample123456";
//
//    private final long EXPIRATION = 1000 * 60 * 60; // 1 hour
//
//    private Key getKey() {
//        return Keys.hmacShaKeyFor(SECRET.getBytes());
//    }
//
//    public String generateToken(String username) {
//        return Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
//                .signWith(getKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    public String extractUsername(String token) {
//        return Jwts.parserBuilder().setSigningKey(SECRET.getBytes())
//                .build()
//                .parseClaimsJws(token)
//                .getBody().getSubject();
//    }
//
//
//
//    public boolean isTokenValid(String token) {
//        try {
//            Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token);
//            return true;
//        } catch (JwtException e) {
//            return false;
//        }
//    }
//
//
//
//
//
//
//    public List<String> extractRoles(String token) {
//        Claims claims = Jwts.parserBuilder()
//                .setSigningKey(getKey())    // reuse your getKey() method for secret
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//
//        return claims.get("roles", List.class);
//    }
//
//
//
//
//
//
//}
