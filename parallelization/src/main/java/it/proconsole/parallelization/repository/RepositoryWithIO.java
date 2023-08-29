package it.proconsole.parallelization.repository;

import it.proconsole.parallelization.GenericException;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class RepositoryWithIO {
  private final long ioTime;

  public RepositoryWithIO(long ioTime) {
    this.ioTime = ioTime;
  }

  public Result retrieve() {
    try {
      var start = Instant.now();
      TimeUnit.SECONDS.sleep(ioTime);
      return new Result(Thread.currentThread().getName(), Duration.between(start, Instant.now()));
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new GenericException(e);
    }
  }

  public record Result(String executorName, Duration executionTime) {}
}
