package com.cfs.bms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cfs.bms.model.Theater;
import com.cfs.bms.model.User;

public interface UserRepository extends JpaRepository<Theater, Long> {

    Optional<User> findByEmail(String email);

}
