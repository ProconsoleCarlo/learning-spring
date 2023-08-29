package it.proconsole.parallelization.usecase;

import it.proconsole.parallelization.repository.RepositoryWithIO;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

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
    var responseTimes =
        RANDOM.longs(MIN_RESPONSE_TIME, MAX_RESPONSE_TIME).limit(taskCount).toArray();
    var rawExecutionTime = Duration.ofSeconds(Arrays.stream(responseTimes).sum());
    var futureResults =
        Arrays.stream(responseTimes)
            .mapToObj(RepositoryWithIO::new)
            .map(it -> CompletableFuture.supplyAsync(it::retrieve, executorService))
            .toArray(CompletableFuture[]::new);
    var start = Instant.now();
    var results =
        CompletableFuture.allOf(futureResults)
            .thenApply(
                ignored ->
                    Arrays.stream(futureResults)
                        .map(CompletableFuture::join)
                        .map(it -> (RepositoryWithIO.Result) it))
            .join()
            .toList();
    var realExecutionTime = Duration.between(start, Instant.now());
    var executors =
        results.stream().map(RepositoryWithIO.Result::executorName).collect(Collectors.toSet());

    return new Result(rawExecutionTime, realExecutionTime, executors);
  }

  public record Result(Duration rawExecutionTime, Duration realExecutionTime, Set<String> executors) {}
}
