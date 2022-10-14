package io.github.gonguasp.jwt.annotation;

import io.github.gonguasp.jwt.exception.UnauthorizedException;
import io.github.gonguasp.jwt.service.JwtService;
import javax.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Data
@Slf4j
public class AccessTokenVerificationAspect {

  private final HttpServletRequest request;

  private final JwtService jwtService;

  @Value("${jwt.access-token:accessToken}")
  private String accessTokenLiteral;

  @Around("@annotation(io.github.gonguasp.jwt.annotation.AccessTokenVerification)")
  public Object processProgressIdVerification(ProceedingJoinPoint joinPoint) throws Throwable {
    log.info("Validating token {}", accessTokenLiteral);
    String accessToken = request.getHeader(accessTokenLiteral);
    if (!StringUtils.isEmpty(accessToken) &&
        jwtService.validateToken(request.getHeader(accessTokenLiteral))) {
      log.info("Token {} validated.", accessTokenLiteral);
      return joinPoint.proceed();
    } else {
      throw new UnauthorizedException("The header " + accessTokenLiteral + " was null or empty.");
    }
  }
}
