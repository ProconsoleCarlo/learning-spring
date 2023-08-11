package it.proconsole.usecase;

import it.proconsole.model.AggregatedResult;
import it.proconsole.repository.FastRepository;
import it.proconsole.repository.SlowRepository;
import it.proconsole.repository.SlowestRepository;
import java.time.Duration;
import java.time.Instant;

public class SerialExecutionUseCase implements ExecutionUseCase {
    private final FastRepository fastRepository;
    private final SlowRepository slowRepository;
    private final SlowestRepository slowestRepository;

    public SerialExecutionUseCase(FastRepository fastRepository, SlowRepository slowRepository, SlowestRepository slowestRepository) {
        this.fastRepository = fastRepository;
        this.slowRepository = slowRepository;
        this.slowestRepository = slowestRepository;
    }

    @Override
    public AggregatedResult execute() {
        var start = Instant.now();
        var fastRepoResult = fastRepository.retrieve();
        var slowestRepoResult = slowestRepository.retrieve();
        var slowRepoResult = slowRepository.retrieve();
        return new AggregatedResult(
                fastRepoResult,
                slowestRepoResult,
                slowRepoResult,
                Duration.between(start, Instant.now())
        );
    }
}
