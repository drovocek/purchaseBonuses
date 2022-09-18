package com.spimex.purchasebonuses.service;

import com.spimex.purchasebonuses.dao.BankAccountRepository;
import com.spimex.purchasebonuses.web.dict.PaymentSource;
import com.spimex.purchasebonuses.dto.MoneyDto;
import com.spimex.purchasebonuses.exception.NotSupportedTypeException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import static com.spimex.purchasebonuses.exception.NotSupportedTypeException.NOT_SUPPORTED_TYPE_TEMPLATE;

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

    public long getMoney(PaymentSource paymentSource, long paymentAmount) {
        if (PaymentSource.Shop.equals(paymentSource)) {
            return getCashMoney(paymentAmount);
        } else if (PaymentSource.Online.equals(paymentSource)) {
            return getNonCashMoney(paymentAmount);
        }
        throw new NotSupportedTypeException(NOT_SUPPORTED_TYPE_TEMPLATE.formatted(paymentSource));
    }

    public long getCashMoney(long eMoney) {
        return this.bankAccountRepository.getCashMoney(eMoney);
    }

    public long getNonCashMoney(long eMoney) {
        return this.bankAccountRepository.getNonCashMoney(eMoney);
    }

    public MoneyDto getMoneyCount() {
        long cashMoney = this.bankAccountRepository.getCashMoneyCount();
        long noCashMoney = this.bankAccountRepository.getNonCashMoneyCount();
        return new MoneyDto(cashMoney, noCashMoney);
    }
}
