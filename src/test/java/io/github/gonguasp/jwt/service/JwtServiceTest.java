package io.github.gonguasp.jwt.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class JwtServiceTest {

  private static JwtService jwtService;
  private static String token;

  @BeforeAll
  static void beforeAll() {
    jwtService = new JwtService("secret", 10L);
    token = jwtService.generateToken(new HashMap<>(Map.of("id", 1, "user", "test")), "test");
  }

  @Test
  void generateTokenTest() {
    jwtService.generateToken(new HashMap<>(), "");
  }

  @Test
  void validateTokenTest() {
    jwtService.validateToken(token);
  }

  @Test
  void extractExpirationTest() {
    assertTrue(new Date().before(jwtService.extractExpiration(token)));
  }

  @Test
  void extractClaimTest() {
    assertEquals(jwtService.extractClaim(token, "id"), 1);
  }

  @Test
  void extractAllClaimsTest() {
    assertEquals(jwtService.extractAllClaims(token).size(), 5);
  }

  @Test
  void extractSubjectTest() {
    assertEquals(jwtService.extractSubject(token), "test");
  }

}
