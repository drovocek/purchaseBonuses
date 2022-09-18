package com.spimex.purchasebonuses.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BankAccount {

    private long cashMoney;

    private long nonCashMoney;

    private long eMoney;
}
