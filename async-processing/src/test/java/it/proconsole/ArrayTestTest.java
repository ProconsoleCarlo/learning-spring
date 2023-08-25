package it.proconsole;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;

@SpringBootApplication()
@ContextConfiguration(classes = ArrayTestTest.SpringAsyncConfig.class)
@EnableAsync
class ArrayTestTest {
  @Test
  void whenUsingAsync_thenUsesCommonPool() throws Exception {
    CompletableFuture<String> name = CompletableFuture.supplyAsync(() -> "Baeldung");

    CompletableFuture<Integer> nameLength =
        name.thenApplyAsync(
            value -> {
              printCurrentThread(); // will print "ForkJoinPool.commonPool-worker-1"
              return value.length();
            });

    assertThat(nameLength.get()).isEqualTo(8);
  }

  @Test
  void whenUsingAsync_thenUsesCustomExecutor() throws Exception {
    Executor testExecutor = Executors.newFixedThreadPool(5);
    CompletableFuture<String> name = CompletableFuture.supplyAsync(() -> "Baeldung");

    CompletableFuture<Integer> nameLength =
        name.thenApplyAsync(
            value -> {
              printCurrentThread(); // will print "pool-2-thread-1"
              return value.length();
            },
            testExecutor);

    assertThat(nameLength.get()).isEqualTo(8);
  }

  @Test
  void whenUsingSpringAsync_thenUsesCustomExecutor() {
    var nameLength = execute();

    assertThat(nameLength.join()).isEqualTo(8);
  }

  @Async
  public CompletableFuture<Integer> execute() {
    printCurrentThread(); // will print "pool-2-thread-1"
    return CompletableFuture.completedFuture("Baeldung".length());
  }

  private static void printCurrentThread() {
    System.out.println(Thread.currentThread().getName());
  }

  @Configuration
  @EnableAsync
  public static class SpringAsyncConfig implements AsyncConfigurer {
    @Override
    public Executor getAsyncExecutor() {
      ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
      threadPoolTaskExecutor.initialize();
      return threadPoolTaskExecutor;
    }
  }
}
