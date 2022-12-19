package com.example.buysell.models;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "paymentse")
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class Payment {

    @Id
    private Long id;

    @ManyToOne
    private CashAccount cashAccount;

    @OneToOne
    @MapsId
    private Product product;

    
    @Column(nullable = false)
    private float sum;
    @Column(nullable = false)
    private LocalDateTime dateOfCreated;

    @PrePersist
    private void onCreate() { dateOfCreated = LocalDateTime.now(); }

}

