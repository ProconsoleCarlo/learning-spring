package it.proconsole.learning.spring.cache.cacheable;

import it.proconsole.learning.spring.cache.delegate.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
class RestRepositoryTest {
  private static final String LANGUAGE = "it";

  @MockBean
  private RestTemplate restTemplate;

  private Repository repository;

  @BeforeEach
  void setUp() {
    repository = new RestRepository(restTemplate);
  }

  @Test
  void retrieveFor() {
    when(restTemplate.getForObject("https://www.google.com", String.class)).thenReturn("");

    repository.retrieveFor(LANGUAGE);
    repository.retrieveFor(LANGUAGE);

    verify(restTemplate).getForObject("https://www.google.com", String.class);
  }
}