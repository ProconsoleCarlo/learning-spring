package it.proconsole;

import it.proconsole.model.AggregatedResult;
import it.proconsole.repository.FastRepository;
import it.proconsole.repository.SlowRepository;
import it.proconsole.repository.SlowestRepository;
import it.proconsole.usecase.ConcurrentExecutionUseCase;
import it.proconsole.usecase.SerialExecutionUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main2 {
    private static final Logger logger = LoggerFactory.getLogger(Main2.class);

    public static void main(String[] args) {
      stream();

      allOf();
    }

  private static void allOf() {
    System.err.println("allOf");
    // Creo una lista di CompletableFuture che rappresentano delle query

    List<CompletableFuture<String>> futures = Arrays.asList(
            CompletableFuture.supplyAsync(() -> {
              // Simulo una query che impiega 2 secondi per completarsi
              try {
                Thread.sleep(2000);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
              return "Query 1";
            }),
            CompletableFuture.supplyAsync(() -> {
              // Simulo una query che impiega 4 secondi per completarsi
              try {
                Thread.sleep(4000);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
              return "Query 2";
            }),
            CompletableFuture.supplyAsync(() -> {
              // Simulo una query che impiega 6 secondi per completarsi
              try {
                Thread.sleep(6000);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
              return "Query3";
            })
    );

    long startTime = System.currentTimeMillis();
// Uso il metodo allOf per avviare tutte le query in parallelo e poi aspettare che siano completate
    CompletableFuture<Void> allDone = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
    List<Long> responseTime = new ArrayList<>();
// Uso il metodo join per ottenere i risultati delle query quando sono tutti pronti
    List<String> results = allDone.thenApply(v -> futures.stream()
            .map(it -> it.thenApply(s -> {
              responseTime.add(System.currentTimeMillis() - startTime);
              return s;
            }))
            .map(future -> future.join()) .collect(Collectors.toList())) .join();
    long endTime = System.currentTimeMillis();
// Stampo i risultati
    System.out.println(results);
    System.out.println(responseTime);
    System.out.println(endTime - startTime);
  }

  private static void stream() {
    System.err.println("stream");
    // Creo una lista di CompletableFuture che rappresentano delle query
    List<CompletableFuture<String>> futures = Arrays.asList(
            CompletableFuture.supplyAsync(() -> {
              // Simulo una query che impiega 2 secondi per completarsi
              try {
                Thread.sleep(2000);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
              return "Query 1";
            }),
            CompletableFuture.supplyAsync(() -> {
              // Simulo una query che impiega 4 secondi per completarsi
              try {
                Thread.sleep(4000);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
              return "Query 2";
            }),
            CompletableFuture.supplyAsync(() -> {
              // Simulo una query che impiega 6 secondi per completarsi
              try {
                Thread.sleep(6000);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
              return "Query 3";
            })
    );

    // Creo una lista per memorizzare i tempi di risposta
    List<Long> responseTime = new ArrayList<>();

    long startTime = System.currentTimeMillis();
// Uso lo stream per unire i risultati delle query
    List<String> results = futures.stream()
            .map(it -> it.thenApply(s -> {
              responseTime.add(System.currentTimeMillis() - startTime);
              return s;
            }))
            .map(it -> it.join())
            .collect(Collectors.toList());
    long endTime = System.currentTimeMillis();

// Stampo i risultati
    System.out.println(results);
    System.out.println(responseTime);
    System.out.println(endTime - startTime);
  }


}