package io.github.gonguasp.jwt.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class JwtService {

  @Value("${jwt.password:secret}")
  private String SECRET_KEY;

  @Value("${jwt.expiration:10}")
  private Long expiration;

  public String extractSubject(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public Object extractClaim(String token, String claim) {
    return extractAllClaims(token).get(claim);
  }

  public String generateToken(Map<String, Object> claimMap, String subject) {
    return createToken(claimMap, subject, expiration);
  }

  public Boolean validateToken(String token) {
    return !isTokenExpired(token);
  }

  private String createToken(Map<String, Object> claims, String subject, Long hoursDuration) {
    return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * hoursDuration))
        .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
  }

  public Claims extractAllClaims(String token) {
    return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
  }

  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }
}
