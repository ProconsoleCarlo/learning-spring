package it.proconsole.learning.spring.pagination;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class FilmCrudRepositoryTest {
  @Autowired
  private FilmCrudRepository repository;

  @Test
  void findAllList() {
    var year = 2020;
    var savedFilms = repository.saveAll(
            List.of(
                    new FilmEntity("Film1", year),
                    new FilmEntity("Film2", year),
                    new FilmEntity("Film3", year)
            )
    );

    var pageSize = 1;
    var firstPage = repository.findAllByYear(year, PageRequest.of(0, pageSize));
    var secondPage = repository.findAllByYear(year, PageRequest.of(1, pageSize));
    var thirdPage = repository.findAllByYear(year, PageRequest.of(2, pageSize));

    assertEquals(firstPage.size(), pageSize);
    assertEquals(secondPage.size(), pageSize);
    assertEquals(thirdPage.size(), pageSize);
    assertEquals(List.of(savedFilms.get(0)), firstPage);
    assertEquals(List.of(savedFilms.get(1)), secondPage);
    assertEquals(List.of(savedFilms.get(2)), thirdPage);
  }
}