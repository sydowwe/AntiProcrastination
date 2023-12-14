package com.timeOrganizer.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Service
public class JwtService {
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private List<String> blacklist;

    @Value("${token.blacklistCleanupPeriodInSec}")
    private int BLACKLIST_CLEANUP_PERIOD_IN_SEC = 300;

    public JwtService(){
        this.blacklist = new ArrayList<>();
        try (ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2)) {
            scheduler.scheduleAtFixedRate(this::blacklistCleanUp, 0, BLACKLIST_CLEANUP_PERIOD_IN_SEC, TimeUnit.SECONDS);
        }
        generateAndRefreshKeys();
    }
    @Scheduled(cron = "0 0 4 * * 1")
    private void generateAndRefreshKeys(){
        ECDSAKeyGenerator.generateAuthKeys();
        this.privateKey = ECDSAKeyGenerator.readPrivateKey();
        this.publicKey = ECDSAKeyGenerator.readPublicKey();
    }
    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getVerificationKey()).build().parseClaimsJws(token).getBody();
    }

    private Key getVerificationKey() {
        return this.publicKey;
    }
    private Key getSigningKey() {
        return this.privateKey;
    }

    public String generateToken(Map<String, Object> extraClaims, String email,int expirationInHours) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * expirationInHours))
                .signWith(getSigningKey(), SignatureAlgorithm.ES384).compact();
    }

    public String generateToken(String email,int expirationInHours) {
        return generateToken(new HashMap<>(), email, expirationInHours);
    }

    public void invalidateToken(String token) {
        blacklist.add(token);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && isTokenNotExpired(token) && !blacklist.contains(token);
    }

    private void blacklistCleanUp() {
        this.blacklist = this.blacklist.stream().filter(this::isTokenNotExpired).toList();
    }

    private boolean isTokenNotExpired(String token) {
        return !extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }
}
