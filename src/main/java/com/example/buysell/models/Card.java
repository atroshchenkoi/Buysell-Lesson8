package com.example.buysell.models;


import lombok.*;


@Getter
@Setter
@RequiredArgsConstructor
public class Card {



    private String number;


    private String dateExpire;


    private String CVVCode;

}
