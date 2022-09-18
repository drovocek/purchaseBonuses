package com.spimex.purchasebonuses.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BankAccount {

    private long cashMoney;

    private long nonCashMoney;

    private long eMoney;
}
