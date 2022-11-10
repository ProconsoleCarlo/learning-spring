package it.proconsole.learning.spring.pagination;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PaginationController {

  private final FilmCrudRepository filmCrudRepository;

  public PaginationController(FilmCrudRepository filmCrudRepository) {
    this.filmCrudRepository = filmCrudRepository;
  }

  @GetMapping("/paginationWithPageable")
  public List<FilmEntity> paginationWithPageable(Pageable pageable) {
    return filmCrudRepository.findAllByYear(2022, pageable);
  }
}
