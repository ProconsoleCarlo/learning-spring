package it.proconsole.repository;

import it.proconsole.GenericException;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class SlowRepository {
  public Result retrieve() {
    try {
      var start = Instant.now();
      TimeUnit.MILLISECONDS.sleep(500);
      return new Result(2, Duration.between(start, Instant.now()));
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new GenericException(e);
    }
  }

  public record Result(Integer value, Duration executionTime) {}
}
