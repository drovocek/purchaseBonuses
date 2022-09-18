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

    private final static String NOT_ENOUGHT_TEMPLATE = "Not enough %s. Current account is %s, need %s";

    private final static BankAccount ACCOUNT = new BankAccount();

    {
        ACCOUNT.setCashMoney(2500L);
        ACCOUNT.setNonCashMoney(2500L);
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
            throw new NotEnoughMoneyException(NOT_ENOUGHT_TEMPLATE.formatted("EMoney", eMoneyCount, count));
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
        long currentCashCount = getCashMoneyCount();
        ACCOUNT.setCashMoney(currentCashCount + count);
    }

    @Override
    public long getCashMoney(long count) {
        log.info("getCashMoney(count: {})", count);
        long cashCount = getCashMoneyCount();
        if (cashCount < count) {
            throw new NotEnoughMoneyException(NOT_ENOUGHT_TEMPLATE.formatted("CashMoney", cashCount, count));
        }
        ACCOUNT.setCashMoney(cashCount - count);
        return count;
    }

    @Override
    public long getCashMoneyCount() {
        log.info("getCashMoneyCount()");
        return ACCOUNT.getCashMoney();
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
        long nonCashCount = getCashMoneyCount();
        if (nonCashCount < count) {
            throw new NotEnoughMoneyException(NOT_ENOUGHT_TEMPLATE.formatted("NonCashMoney", nonCashCount, count));
        }
        ACCOUNT.setNonCashMoney(nonCashCount - count);
        return count;
    }

    @Override
    public long getNonCashMoneyCount() {
        log.info("getNonCashMoneyCount()");
        return ACCOUNT.getNonCashMoney();
    }
}
