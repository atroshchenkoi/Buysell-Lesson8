package com.example.buysell.services;


import com.example.buysell.models.CashAccount;
import com.example.buysell.models.User;
import com.example.buysell.repositories.CashAccountRepository;
import com.example.buysell.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CashAccountService {
    private final UserRepository userRepository;

    private final CashAccountRepository cashAccountRepository;

    public CashAccount findByUserId(Long id){

        return cashAccountRepository.findByUserId(id);
    }

    public void deposit(Long id, float cash){
        CashAccount cashAccount = cashAccountRepository.findByUserId(id);
        cashAccount.setBalance(cashAccount.getBalance() + cash);
        cashAccountRepository.save(cashAccount);
    }


}
