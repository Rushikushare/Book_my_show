package com.cfs.bms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cfs.bms.model.Screen;

public interface ScreenRepository extends JpaRepository<Screen, Long> {

    List<Screen> findByTheaterId(Long id);

}
