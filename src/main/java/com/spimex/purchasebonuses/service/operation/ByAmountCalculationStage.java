package com.spimex.purchasebonuses.service.operation;

import com.spimex.purchasebonuses.web.dict.PaymentSource;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import static com.spimex.purchasebonuses.util.PercentCalculator.*;

@Slf4j
@Getter
@Setter(AccessLevel.PRIVATE)
public class ByAmountCalculationStage implements CalculationStage {

    private long debit;
    private long eMoneyBonus;

    @Override
    public CalculationStage doStage(PaymentSource paymentSource, long debit) {
        log.info("doStage(type: {}, debit: {})", paymentSource, debit);

        if (debit < 20L) {
            long bankCommission = calculate(debit, COMMISSION);
            setDebit(bankCommission);
        } else if (debit >= 300L) {
            setEMoneyBonus(calculate(debit, MORE_THEN_300));
        }

        log.info("Stage result - debit: {}, eMoneyBonus: {}", getDebit(), getEMoneyBonus());
        return null;
    }
}
