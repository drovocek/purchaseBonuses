package com.spimex.purchasebonuses.service.payment.processing.core;

import com.spimex.purchasebonuses.web.dict.PaymentSource;

public interface PaymentCalculationProcessContext {

    String BASE_IMPL = "paymentProcessContextImpl";

    void setStage(CalculationProcessStage currentStage);

    CalculationProcessStage getStage();

    void setPaymentSource(PaymentSource paymentSource);

    PaymentSource getPaymentSource();

    void setPaymentAmount(long paymentAmount);

    long getPaymentAmount();

    void setEMoneyBonus(long eMoneyBonus);

    long getEMoneyBonus();

    void setCommission(long commission);

    long getCommission();

    void calculate();
}
