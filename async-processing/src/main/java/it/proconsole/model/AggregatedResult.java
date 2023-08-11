package it.proconsole.model;

import it.proconsole.repository.FastRepository;
import it.proconsole.repository.SlowRepository;
import it.proconsole.repository.SlowestRepository;
import java.time.Duration;

public record AggregatedResult(
        FastRepository.Result fastRepoResult,
        SlowestRepository.Result slowestRepoResult,
        SlowRepository.Result slowRepoResult,
        Duration executionTime
) {
}
