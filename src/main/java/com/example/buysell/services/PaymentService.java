package com.example.buysell.services;


import com.example.buysell.models.CashAccount;
import com.example.buysell.models.Deposit;
import com.example.buysell.models.Payment;
import com.example.buysell.models.Product;
import com.example.buysell.repositories.CashAccountRepository;
import com.example.buysell.repositories.DepositRepository;
import com.example.buysell.repositories.PaymentRepository;
import com.example.buysell.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {
    private final ProductRepository productRepository;
    private final DepositRepository depositRepository;
    private final CashAccountRepository cashAccountRepository;

    private final PaymentRepository paymentRepository;

    public List<Payment> listAllPayments(){
        return paymentRepository.findAll();
    }
    public List<Payment> listPaymentsByCashAccountId(Long id){
        return paymentRepository.findByCashAccountId(id);
    }

    public boolean createPayment(CashAccount cashAccount, Product product){
        log.info("2");
        cashAccount.setBalance(cashAccount.getBalance() - 3);
        Payment payment = new Payment();
        payment.setSum(3);
        product.addPayment(payment);
        cashAccount.addPayment(payment);

        log.info("3");
        return true;


    }

    public void deletePayment(Payment payment){
        paymentRepository.delete(payment);
    }

}
