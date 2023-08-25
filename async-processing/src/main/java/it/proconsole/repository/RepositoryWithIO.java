package it.proconsole.repository;

import it.proconsole.GenericException;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class RepositoryWithIO {
  private final long ioTime;

  public RepositoryWithIO(long ioTime) {
    this.ioTime = ioTime;
  }

  public Result retrieve() {
    System.out.println(Thread.currentThread().getName());
    try {
      var start = Instant.now();
      TimeUnit.SECONDS.sleep(ioTime);
      return new Result("Hello!", Duration.between(start, Instant.now()));
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new GenericException(e);
    }
  }

  public record Result(String value, Duration executionTime) {}
}
