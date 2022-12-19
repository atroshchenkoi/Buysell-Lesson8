package com.example.buysell.models;


import lombok.*;


import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "cash_accounts")
@Getter
@Setter
@RequiredArgsConstructor
public class CashAccount {

    @Id
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cashAccount")
    private List<Deposit> deposits;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cashAccount")
    private List<Payment> payments;

    @OneToOne
    @MapsId
    private User user;

    private float balance;

    public void addDeposit(Deposit deposit){
        deposit.setCashAccount(this);
        this.getDeposits().add(deposit);
    }

    public void addPayment(Payment payment){
        payment.setCashAccount(this);
        this.getPayments().add(payment);
    }
}
