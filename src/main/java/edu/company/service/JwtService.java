package edu.company.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.key}")
    private String SECRET;

    /**
     * Why do we use SECRET - I am encrypting this token that we created with the help of
     * SECRET key and no one who does not know this password and secret cannot extract this token,
     * cannot extract the value inside
     */
    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        //claims.put("can", "wia");
        return createToken(claims, userName);
    }

    // We validate the incoming token
    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUser(token);
        Date expirationDate = extractExpiration(token);
        return userDetails.getUsername().equals(username) && !expirationDate.before(new Date());
    }

    /**
     * In order to be able to validate, we must first extract the incoming token, that is,
     * -if the extract Date is valid, it will return it (payload).
     * <p>
     * Key value values in Claims-> payload
     */
    private Date extractExpiration(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration();
    }

    public String extractUser(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 2))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * It will decode the SECRET value we gave and give us a key.
     * <p>
     * if we want we can create our own key ->> return Keys.hmacShaKeyFor(SignatureAlgorithm)
     */
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);

        return Keys.hmacShaKeyFor(keyBytes);
    }


}