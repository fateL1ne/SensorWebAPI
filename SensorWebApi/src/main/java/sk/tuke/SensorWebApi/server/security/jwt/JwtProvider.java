package sk.tuke.SensorWebApi.server.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtProvider implements Serializable
{
    private static final long serialVersionUID = 851861272L;

    /**
     *  define token lifetime for the hour
     *  milliseconds unit
     */

    private static final long JWT_TOKEN_VALIDITY = 60 * 60 * 1000;

    /**
     *  load secret key from the application.properties
     *  required for signing message
     *
     *  HMACSHA256(
     *   base64UrlEncode(header) + "." +
     *   base64UrlEncode(payload),
     *   secret)
     */

    @Value("${jwt.secret}")
    private String SECRET_PASSPHRASE;


    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS256, SECRET_PASSPHRASE)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        return ( getUsername(token).equals(userDetails.getUsername()) && !isTokenExpired(token) );
    }

    private <T> T getClaimsFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_PASSPHRASE).parseClaimsJws(token).getBody();
    }

    public String getUsername(String token) {
        return getClaimsFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDate(String token) {
        return getClaimsFromToken(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return getExpirationDate(token).before(new Date());
    }
}
