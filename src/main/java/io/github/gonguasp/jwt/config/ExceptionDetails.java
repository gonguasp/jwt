package io.github.gonguasp.jwt.config;

import java.time.Instant;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@RequiredArgsConstructor
public class ExceptionDetails {

  @NonNull
  private HttpStatus status;
  private Instant dateTime = Instant.now();
  @NonNull
  private String errorDescription;
}
