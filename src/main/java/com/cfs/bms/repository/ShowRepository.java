package com.cfs.bms.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cfs.bms.model.Show;

public interface ShowRepository extends JpaRepository<Show, Long> {

    List<Show> findbyMovieId(Long movieid);

    List<Show> findbyScreenId(Long screenid);

    List<Show> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);

    List<Show> findByMovie_IdAndScreen_Theater_city(Long movieId, String city);
}
