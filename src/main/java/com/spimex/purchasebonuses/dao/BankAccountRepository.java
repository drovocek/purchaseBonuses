package com.spimex.purchasebonuses.dao;

public interface BankAccountRepository {

    String MOCK_IMPL = "bankAccountOfEMoneyServiceImpl";
    String DATA_JPA_IMPL = "";

    void addEMoney(long count);

    long getEMoney(long count);

    long getEMoneyCount();

    void addCashMoney(long count);

    long getCashMoney(long count);

    long getCashMoneyCount();

    void addNonCashMoney(long count);

    long getNonCashMoney(long count);

    long getNonCashMoneyCount();
}
