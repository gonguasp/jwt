package io.github.gonguasp.jwt.config;

import io.github.gonguasp.jwt.exception.UnauthorizedException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class JwtExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler {

  @ExceptionHandler({JwtException.class})
  public final ResponseEntity<Object> handleJwtException(
      JwtException th, WebRequest request) {
    log.error(th.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new ExceptionDetails(HttpStatus.BAD_REQUEST, th.getMessage()));
  }

  @ExceptionHandler({UnauthorizedException.class})
  public final ResponseEntity<Object> handleNotValidUserException(
      UnauthorizedException th, WebRequest request) {
    log.error(th.getMessage());
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(new ExceptionDetails(HttpStatus.UNAUTHORIZED, th.getMessage()));
  }

  @ExceptionHandler({SignatureException.class})
  public final ResponseEntity<Object> handleConstraintException(
      SignatureException th, WebRequest request) {
    log.error(th.getMessage());
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(new ExceptionDetails(HttpStatus.UNAUTHORIZED, th.getMessage()));
  }
}
