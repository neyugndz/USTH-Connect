package com.usth_connect.vpn_server_backend_usth.config;

import com.usth_connect.vpn_server_backend_usth.service.StudentService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class JwtService {
    private final Logger LOGGER = Logger.getLogger(JwtService.class.getName());

    private static final String SECRET_KEY = "SS8UBzZPBfmP/4TS1rz9pYNCrTKE5zQ49o9HeGoKA5QPmEYCg+27i9R+sU46ammvE3YjW5SPjNsoQ9+huLP64onbG0e9Kk7qkBjXr/LT2oKrDHXhimMNeULH6JV7gS97tzZAYaVCqz6YV4B0lYm2cjPy4a0w60t2iWB893QHagicOjaRyvgwRgqMOuFjOvoletivdHTvUBNdR3rSZxM8yPucBm2zBufmOWfCLGtciRFjA7IXjTWvJ5wDpiMCPOqOZ5E4fWpG7RUzDwXsKETHjyvnB1za+PgGP/KtkpgQoi5xNpYqVUMm3vi/4R+0LdrTE+E/nMhObz2iSu9gs8jc6u8UmtYJq15BSY2Q4/I76xQ=\n";
    private final Set<String> blacklistedTokens = new HashSet<>();

    public String extractStudentId(String token) {
        if (isTokenBlackListed(token)) {
            throw new IllegalArgumentException("Token is invalid or blacklisted");
        }
        try {
            return extractClaim(token, Claims::getSubject); // Subject of the token (ID in this situation)
        } catch (JwtException e) {
            LOGGER.warning("Token parsing failed: " + e.getMessage());
            return null;
        }
//        return extractClaim(token, Claims::getSubject); // Subject of the token (ID in this situation)
    }

    // Extract claim from the token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Generate token based on the userDetails
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.toList());
        extraClaims.put("roles", roles); // Add roles to the claim

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis())) // Help calculate the expiration date
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact(); // generate and return token
    }

    // Check if the token belongs to the User
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String id = extractStudentId(token);
        return(id.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public List<String> extractRoles(String token) {
        return extractClaim(token, claims -> claims.get("roles", List.class));  // Extract the roles
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey()) // Secret key to claim who is the sender and ensure the msg dont change
                .build()
                .parseClaimsJws(token)
                .getBody(); // Get all the claims within the token
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Method to check validate of the token
    public void invalidateToken(String token) {
        blacklistedTokens.add(token);
    }

    public boolean isTokenBlackListed(String token) {
        return blacklistedTokens.contains(token);
    }
}