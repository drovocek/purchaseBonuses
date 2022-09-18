package com.spimex.purchasebonuses.dao;

import com.spimex.purchasebonuses.exception.NotEnoughMoneyException;
import com.spimex.purchasebonuses.model.BankAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@Qualifier(BankAccountRepository.MOCK_IMPL)
public class MockBankAccountRepository implements BankAccountRepository {

    private final static String NOT_ENOUGH_TEMPLATE = "Not enough %s. Current account is %s, need %s";
    public static final long START_CASH_COUNT = 2500L;
    public static final long START_NON_CASH_COUNT = 2500L;
    public static final long START_E_MONEY_COUNT = 0;
    private final static BankAccount ACCOUNT = new BankAccount();

    {
        setStartBalance();
    }

    public static void setStartBalance() {
        log.info("setStartBalance(cash: {}, nonCash: {}, eMoney: {})", START_CASH_COUNT, START_NON_CASH_COUNT, START_E_MONEY_COUNT);
        ACCOUNT.setCashMoney(START_CASH_COUNT);
        ACCOUNT.setNonCashMoney(START_NON_CASH_COUNT);
        ACCOUNT.setEMoney(START_E_MONEY_COUNT);
    }

    @Override
    public void addEMoney(long count) {
        log.info("addEMoney(count: {})", count);
        long currentEMoneyCount = getEMoneyCount();
        ACCOUNT.setEMoney(currentEMoneyCount + count);
    }

    @Override
    public long getEMoney(long count) {
        log.info("getEMoney(count: {})", count);
        long eMoneyCount = getEMoneyCount();
        if (eMoneyCount < count) {
            throw new NotEnoughMoneyException(NOT_ENOUGH_TEMPLATE.formatted("EMoney", eMoneyCount, count));
        }
        ACCOUNT.setEMoney(eMoneyCount - count);
        return count;
    }

    @Override
    public long getEMoneyCount() {
        log.info("getEMoneyCount()");
        return ACCOUNT.getEMoney();
    }

    @Override
    public void addCashMoney(long count) {
        log.info("addCashMoney(count: {})", count);
        long currentCashCount = getCashMoneyCount();
        ACCOUNT.setCashMoney(currentCashCount + count);
    }

    @Override
    public long getCashMoney(long count) {
        log.info("getCashMoney(count: {})", count);
        long cashCount = getCashMoneyCount();
        if (cashCount < count) {
            throw new NotEnoughMoneyException(NOT_ENOUGH_TEMPLATE.formatted("CashMoney", cashCount, count));
        }
        ACCOUNT.setCashMoney(cashCount - count);
        return count;
    }

    @Override
    public void addNonCashMoney(long count) {
        log.info("addNonCashMoney(count: {})", count);
        long currentNonCashCount = getNonCashMoneyCount();
        ACCOUNT.setNonCashMoney(currentNonCashCount + count);
    }

    @Override
    public long getNonCashMoney(long count) {
        log.info("getNonCashMoney(count: {})", count);
        long nonCashCount = getNonCashMoneyCount();
        if (nonCashCount < count) {
            throw new NotEnoughMoneyException(NOT_ENOUGH_TEMPLATE.formatted("NonCashMoney", nonCashCount, count));
        }
        ACCOUNT.setNonCashMoney(nonCashCount - count);
        return count;
    }

    @Override
    public long getCashMoneyCount() {
        log.info("getCashMoneyCount()");
        return ACCOUNT.getCashMoney();
    }

    @Override
    public long getNonCashMoneyCount() {
        log.info("getNonCashMoneyCount()");
        return ACCOUNT.getNonCashMoney();
    }
}
