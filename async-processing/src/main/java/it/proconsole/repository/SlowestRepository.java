package it.proconsole.repository;

import it.proconsole.GenericException;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class SlowestRepository {
  public Result retrieve() {
    try {
      var start = Instant.now();
      TimeUnit.SECONDS.sleep(1);
      return new Result(4L, Duration.between(start, Instant.now()));
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new GenericException(e);
    }
  }

  public record Result(Long value, Duration executionTime) {}
}
