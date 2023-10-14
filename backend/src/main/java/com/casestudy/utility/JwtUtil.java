package com.casestudy.utility;

import com.casestudy.config.TokenConfig;
import com.casestudy.constant.VendingMachineConstants;
import com.casestudy.exception.UnauthorizedRestException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Autowired
    private TokenConfig tokenConfig;

    public String generateToken() {
        Map<String, Object> claims = new HashMap<>();
        String subject = VendingMachineConstants.ADMIN_UUID;
        return createToken(claims, subject);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(tokenConfig.getExpirationTime())))
                .signWith(SignatureAlgorithm.HS256, tokenConfig.getSecretKey().getBytes())
                .compact();
    }

    public void validateToken(String token) {
        final String adminUUID = getAdminUUIDFromToken(token);
        if (isTokenExpired(token) && !adminUUID.equals(VendingMachineConstants.ADMIN_UUID)) {
            throw new UnauthorizedRestException("Invalid token");
        }
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(tokenConfig.getSecretKey().getBytes()).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            throw new UnauthorizedRestException(e.getMessage());
        }
    }

    private boolean isTokenExpired(String token) {
        final Date expiration = extractClaim(token, Claims::getExpiration);
        return expiration.before(new Date());
    }

    public String getAdminUUIDFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

}
