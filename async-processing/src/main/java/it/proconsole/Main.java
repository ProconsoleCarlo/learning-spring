package it.proconsole;

import it.proconsole.model.AggregatedResult;
import it.proconsole.repository.FastRepository;
import it.proconsole.repository.SlowRepository;
import it.proconsole.repository.SlowestRepository;
import it.proconsole.usecase.ConcurrentExecutionUseCase;
import it.proconsole.usecase.SerialExecutionUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        var fastRepository = new FastRepository();
        var slowRepository = new SlowRepository();
        var slowestRepository = new SlowestRepository();

        var serialExecutionUseCase = new SerialExecutionUseCase(fastRepository, slowRepository, slowestRepository);
        var concurrentExecutionUseCase = new ConcurrentExecutionUseCase(fastRepository, slowRepository, slowestRepository);

        logResults("Serial", serialExecutionUseCase.execute());
        logResults("Concurrent", concurrentExecutionUseCase.execute());
    }

    private static void logResults(String type, AggregatedResult result) {
        logger.info("""
                        {} execution
                        Total execution time: {}
                        Fast repository time: {}
                        Slow repository time: {}
                        Slowest repository time: {}
                        """,
                type,
                result.executionTime(),
                result.fastRepoResult().executionTime(),
                result.slowRepoResult().executionTime(),
                result.slowestRepoResult().executionTime()
        );
    }
}