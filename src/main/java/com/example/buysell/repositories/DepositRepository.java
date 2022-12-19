package com.example.buysell.repositories;

import com.example.buysell.models.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepositRepository extends JpaRepository<Deposit, Long> {

    List<Deposit> findByCashAccountId(Long id);
}
