package com.example.buysell.repositories;

import com.example.buysell.models.CashAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CashAccountRepository extends JpaRepository<CashAccount, Long> {
    CashAccount findByUserId(Long id);
}
