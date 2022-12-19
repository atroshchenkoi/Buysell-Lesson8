package com.example.buysell.repositories;

import com.example.buysell.models.Deposit;
import com.example.buysell.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByCashAccountId(Long id);

    Payment findByProductId(Long id);

    void deleteByProductId(Long id);
}
