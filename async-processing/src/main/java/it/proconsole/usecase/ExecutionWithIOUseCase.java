package it.proconsole.usecase;

import it.proconsole.repository.RepositoryWithIO;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public class ExecutionWithIOUseCase {
  private static final Random RANDOM = new Random();
  private static final int MIN_RESPONSE_TIME = 1;
  private static final int MAX_RESPONSE_TIME = 4;

  private final long taskCount;
  private final ExecutorService executorService;

  public ExecutionWithIOUseCase(long taskCount, ExecutorService executorService) {
    this.taskCount = taskCount;
    this.executorService = executorService;
  }

  public Result execute() {
    var responseTimes = RANDOM.longs(1, 4).limit(taskCount).toArray();
    var totalResponseTime = Arrays.stream(responseTimes).sum();
    var futureResults =
        Arrays.stream(responseTimes)
            .mapToObj(RepositoryWithIO::new)
            .map(it -> CompletableFuture.supplyAsync(it::retrieve, executorService))
            .toArray(CompletableFuture[]::new);
    var start = Instant.now();
    CompletableFuture.allOf(futureResults).join();

    return new Result(
            totalResponseTime,
            Duration.between(start, Instant.now()).getSeconds()
    );
  }

  public record Result(long totalResponseTime, long executionResponseTime) {}
}
