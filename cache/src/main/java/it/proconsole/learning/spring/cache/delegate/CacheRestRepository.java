package it.proconsole.learning.spring.cache.delegate;

import org.springframework.cache.Cache;

public class CacheRestRepository implements Repository {
  private final Cache cache;
  private final Repository delegate;

  public CacheRestRepository(Cache cache, Repository delegate) {
    this.cache = cache;
    this.delegate = delegate;
  }

  @Override
  public String retrieveFor(String language) {
    return null;
  }
}
