package com.cfs.bms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cfs.bms.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByLanguage(String language);

    List<Movie> findByGenre(String genre);

    List<Movie> findByTitleContaining(String title);

}
