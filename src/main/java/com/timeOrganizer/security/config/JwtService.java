package com.timeOrganizer.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Service
//@ConfigurationProperties("rsa")
public class JwtService {
    private static final String SECRET_KEY = "c070114f02ae3d1021fdf3a8fe44d2c15a6e39d940e6585c8e7d38d3f24afd73";
//    private final PrivateKey privateKey;
//    private final PublicKey publicKey;
    private static final long TOKEN_EXPIRATION_IN_HOURS_LONG = 5L;
    private static final long TOKEN_EXPIRATION_IN_HOURS_SHORT = 1L;
    private List<String> blacklist;
    private static final int BLACKLIST_CLEANUP_PERIOD_IN_SEC = 60;

//    public JwtService(String privateKeyPath, String publicKeyPath) {
//        this.privateKey = loadPrivateKey(privateKeyPath);
//        this.publicKey = loadPublicKey(publicKeyPath);
//    }
    public JwtService(){
        this.blacklist = new ArrayList<>();
        try (ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2)) {
            scheduler.scheduleAtFixedRate(this::blacklistCleanUp, 0,BLACKLIST_CLEANUP_PERIOD_IN_SEC, TimeUnit.SECONDS);
        }
    }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Map<String, Object> extraClaims, String email) throws InvalidKeyException {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * TOKEN_EXPIRATION_IN_HOURS_LONG))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
    }

    public String generateToken(String email){
        return generateToken(new HashMap<>(),email);
    }
    public void invalidateToken(String token){
        blacklist.add(token);
    }
    public boolean isTokenValid(String token,UserDetails userDetails){
        final  String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && isTokenNotExpired(token) && !blacklist.contains(token);
    }
    private void blacklistCleanUp(){
        this.blacklist = this.blacklist.stream().filter(this::isTokenNotExpired).toList();
    }
    private boolean isTokenNotExpired(String token) {
        return !extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token,Claims::getExpiration);
    }
}
