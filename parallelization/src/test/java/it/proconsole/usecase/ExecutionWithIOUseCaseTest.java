package it.proconsole.usecase;

import static org.junit.jupiter.api.Assertions.assertTrue;

import it.proconsole.parallelization.usecase.ExecutionWithIOUseCase;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;

class ExecutionWithIOUseCaseTest {
  @Test
  void execute() {
    var cores = 4;
    var executor = Executors.newFixedThreadPool(cores);
    var useCase = new ExecutionWithIOUseCase(10, executor);

    var current = useCase.execute();

    System.out.printf(
        "Total response time %d, execution time %d",
        current.rawExecutionTime().toMillis(), current.realExecutionTime().toMillis());
    assertTrue(
        current.rawExecutionTime().toMillis() / cores < current.realExecutionTime().toMillis());
    var speedUp =
        (float) current.rawExecutionTime().toMillis() / current.realExecutionTime().toMillis();
    System.err.println(speedUp + " - " + Runtime.getRuntime().availableProcessors());
    assertTrue(speedUp > cores - 1);
    assertTrue(speedUp < cores);
  }
}
