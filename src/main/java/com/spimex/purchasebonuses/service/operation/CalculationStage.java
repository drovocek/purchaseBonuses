package com.spimex.purchasebonuses.service.operation;

import com.spimex.purchasebonuses.web.dict.PaymentSource;

public interface CalculationStage {

    CalculationStage doStage(PaymentSource paymentSource, long debit);

    long getDebit();

    long getEMoneyBonus();
}
