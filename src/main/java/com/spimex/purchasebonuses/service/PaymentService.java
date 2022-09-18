package com.spimex.purchasebonuses.service;

import com.spimex.purchasebonuses.service.operation.PaymentCalculator;
import com.spimex.purchasebonuses.web.dict.PaymentSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentService {

    private final BankAccountService bankAccountService;

    public void makePayment(PaymentSource paymentSource, long amount) {
        PaymentCalculator paymentCalculator = new PaymentCalculator(paymentSource, amount);
        paymentCalculator.performCalculation();
        long debit = paymentCalculator.getResultDebit();
        long eMoneyBonus = paymentCalculator.getResultEMoneyBonus();

        if (PaymentSource.Shop.equals(paymentSource)) {
            long сashMoney = this.bankAccountService.getCashMoney(debit);
        } else if (PaymentSource.Online.equals(paymentSource)) {
            long nonCashMoney = this.bankAccountService.getNonCashMoney(debit);
        }

        this.bankAccountService.addEMoney(eMoneyBonus);

        log.info("makePayment(debit: {})", debit);
        log.info("makePayment(eMoneyBonus: {})", eMoneyBonus);
    }
}
