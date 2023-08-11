package it.proconsole.usecase;

import it.proconsole.GenericException;
import it.proconsole.model.AggregatedResult;
import it.proconsole.repository.FastRepository;
import it.proconsole.repository.SlowRepository;
import it.proconsole.repository.SlowestRepository;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ConcurrentExecutionUseCase implements ExecutionUseCase {
    private final FastRepository fastRepository;
    private final SlowRepository slowRepository;
    private final SlowestRepository slowestRepository;

    public ConcurrentExecutionUseCase(FastRepository fastRepository, SlowRepository slowRepository, SlowestRepository slowestRepository) {
        this.fastRepository = fastRepository;
        this.slowRepository = slowRepository;
        this.slowestRepository = slowestRepository;
    }

    @Override
    public AggregatedResult execute() {
        var fastRepoResult = CompletableFuture.supplyAsync(fastRepository::retrieve);
        var slowRepoResult = CompletableFuture.supplyAsync(slowRepository::retrieve);
        var slowestRepoResult = CompletableFuture.supplyAsync(slowestRepository::retrieve);

        try {
            var start = Instant.now();
            CompletableFuture.allOf(fastRepoResult, slowRepoResult, slowestRepoResult).get();
            return new AggregatedResult(
                    fastRepoResult.get(),
                    slowestRepoResult.get(),
                    slowRepoResult.get(),
                    Duration.between(start, Instant.now())
            );
        } catch (ExecutionException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new GenericException(e);
        }
    }
}
