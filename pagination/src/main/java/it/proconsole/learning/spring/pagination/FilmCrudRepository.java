package it.proconsole.learning.spring.pagination;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmCrudRepository extends JpaRepository<FilmEntity, Long> {
  List<FilmEntity> findAllByYear(Integer year, Pageable pageable);
}
