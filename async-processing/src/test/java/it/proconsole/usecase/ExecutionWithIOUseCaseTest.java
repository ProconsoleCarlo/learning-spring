package it.proconsole.usecase;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

class ExecutionWithIOUseCaseTest {
  @Test
  void execute() {
    var executor = new ThreadPoolExecutor(4, 4, 1000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
    var useCase = new ExecutionWithIOUseCase(10, executor);

    var current = useCase.execute();

    System.out.printf("Total response time %d, execution time %d", current.totalResponseTime(), current.executionResponseTime());
  }
}
