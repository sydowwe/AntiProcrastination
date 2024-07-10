package com.timeOrganizer.security.config;

import com.timeOrganizer.exception.IdInTokenFormatException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

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
    //TODO FIX value to work from config
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
    public String extractEmail(String token) {
        return extractClaims(token, Claims::getSubject);
    }
    public long extractId(String token) throws IdInTokenFormatException {
        String id = extractClaims(token, Claims::getId);
        try {
            return Long.parseLong(id);
        }catch (Exception e)
        {
            throw new IdInTokenFormatException(id);
        }
    }
    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        token = token.substring(7);
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(this.publicKey).build().parseClaimsJws(token).getBody();
    }
    public String generateToken(Map<String, Object> extraClaims, String email, long id, int expirationInHours) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setId(String.valueOf(id))
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * expirationInHours))
                .signWith(this.privateKey, SignatureAlgorithm.ES384).compact();
    }

    public String generateToken(String email, long id,int expirationInHours) {
        return generateToken(new HashMap<>(), email, id, expirationInHours);
    }

    public void invalidateToken(String token) {
        blacklist.add(token.substring(7));
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractEmail(token);
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
