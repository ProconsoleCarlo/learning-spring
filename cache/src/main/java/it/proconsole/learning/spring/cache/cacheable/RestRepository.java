package it.proconsole.learning.spring.cache.cacheable;

import it.proconsole.learning.spring.cache.delegate.Repository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

public class RestRepository implements Repository {
  private final RestTemplate restTemplate;

  public RestRepository(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Cacheable(cacheNames = "retrieveForLanguage")
  @Override
  public String retrieveFor(String language) {
    restTemplate.getForObject("https://www.google.com", String.class);
    return String.valueOf(Objects.hash(language));
  }
}
