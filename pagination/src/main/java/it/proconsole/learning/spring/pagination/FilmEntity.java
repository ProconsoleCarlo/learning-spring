package it.proconsole.learning.spring.pagination;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.Hibernate;
import org.springframework.lang.Nullable;

import java.util.Objects;

@Table(name = "film")
@Entity
public class FilmEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  @Nullable
  private Long id;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "\"year\"", nullable = false)
  private Integer year;

  public FilmEntity() {
    this.title = "";
    this.year = 0;
  }

  public FilmEntity(
          Long id,
          String title,
          Integer year
  ) {
    this.id = id;
    this.title = title;
    this.year = year;
  }

  public FilmEntity(
          String title,
          Integer year
  ) {
    this.id = null;
    this.title = title;
    this.year = year;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    FilmEntity film = (FilmEntity) o;
    return Objects.equals(id, film.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}