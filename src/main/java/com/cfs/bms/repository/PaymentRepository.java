package com.cfs.bms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cfs.bms.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByTransactionId(String transactionid);

}
