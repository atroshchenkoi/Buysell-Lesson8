package com.example.buysell.services;

import com.example.buysell.models.Card;
import com.example.buysell.models.CashAccount;
import com.example.buysell.models.Deposit;
import com.example.buysell.repositories.CashAccountRepository;
import com.example.buysell.repositories.DepositRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class DepositService {
    private final CashAccountRepository cashAccountRepository;

    private final DepositRepository depositRepository;

    public List<Deposit> listDepositsByCashAccountId(Long id){
        return depositRepository.findByCashAccountId(id);
    }
    public void createDeposit(CashAccount cashAccount, Card card, float sum) {
        Deposit deposit = new Deposit();
        deposit.setCardNumber(card.getNumber());
        deposit.setSum(sum);
        cashAccount.addDeposit(deposit);
        cashAccountRepository.save(cashAccount);
    }
}
