package com.cfs.bms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cfs.bms.model.Theater;

public interface TheaterRepository extends JpaRepository<Theater, Long> {

    List<Theater> findByCity(String city);

}
