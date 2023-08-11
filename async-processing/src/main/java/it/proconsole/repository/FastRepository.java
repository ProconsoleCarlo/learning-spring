package it.proconsole.repository;

import it.proconsole.GenericException;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class FastRepository {
  public Result retrieve() {
    try {
      var start = Instant.now();
      TimeUnit.MILLISECONDS.sleep(100);
      return new Result("Hello!", Duration.between(start, Instant.now()));
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new GenericException(e);
    }
  }

  public record Result(String value, Duration executionTime) {}
}
