package com.spimex.purchasebonuses.service;

import com.spimex.purchasebonuses.dao.BankAccountRepository;
import com.spimex.purchasebonuses.web.dto.MoneyDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    public BankAccountService(
            @Qualifier(BankAccountRepository.MOCK_IMPL)
                    BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public long getEMoneyCount() {
        return this.bankAccountRepository.getEMoneyCount();
    }

    public void addEMoney(long eMoney) {
        this.bankAccountRepository.addEMoney(eMoney);
    }

    public long getCashMoney(long eMoney) {
        return bankAccountRepository.getCashMoney(eMoney);
    }

    public long getNonCashMoney(long eMoney) {
        return bankAccountRepository.getNonCashMoney(eMoney);
    }

    public MoneyDto getMoneyCount() {
        long cashMoney = this.bankAccountRepository.getCashMoneyCount();
        long noCashMoney = this.bankAccountRepository.getNonCashMoneyCount();
        return new MoneyDto(cashMoney, noCashMoney);
    }
}
