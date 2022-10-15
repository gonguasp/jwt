package io.github.gonguasp.jwt.annotation;

import static org.junit.jupiter.api.Assertions.assertThrows;

import io.github.gonguasp.jwt.exception.UnauthorizedException;
import io.github.gonguasp.jwt.service.JwtService;
import javax.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AccessTokenVerificationAspectTest {

  private static HttpServletRequest request;
  private static JwtService jwtService;

  private static AccessTokenVerificationAspect accessTokenVerificationAspect;

  @BeforeAll
  static void beforeAll() {
    request = Mockito.mock(HttpServletRequest.class);
    jwtService = Mockito.mock(JwtService.class);
    accessTokenVerificationAspect = new AccessTokenVerificationAspect(request, jwtService);
  }

  @Test
  @SneakyThrows
  void processProgressIdVerificationTestUnauthorizedException() {
    ProceedingJoinPoint joinPoint = Mockito.mock(ProceedingJoinPoint.class);
    Mockito.when(request.getHeader(Mockito.any())).thenReturn("");
    assertThrows(UnauthorizedException.class,
        () -> accessTokenVerificationAspect.processProgressIdVerification(joinPoint));
  }

  @Test
  @SneakyThrows
  void processProgressIdVerificationTestUnauthorizedExceptionNotValidToken() {
    ProceedingJoinPoint joinPoint = Mockito.mock(ProceedingJoinPoint.class);
    Mockito.when(request.getHeader(Mockito.any())).thenReturn("test");
    Mockito.reset(jwtService);
    assertThrows(UnauthorizedException.class,
        () -> accessTokenVerificationAspect.processProgressIdVerification(joinPoint));
  }

  @Test
  @SneakyThrows
  void processProgressIdVerificationTestOk() {
    ProceedingJoinPoint joinPoint = Mockito.mock(ProceedingJoinPoint.class);
    Mockito.when(request.getHeader(Mockito.any())).thenReturn("test");
    Mockito.when(jwtService.validateToken(Mockito.any())).thenReturn(true);
    accessTokenVerificationAspect.processProgressIdVerification(joinPoint);
  }

}
