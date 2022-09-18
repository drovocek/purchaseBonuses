package com.spimex.purchasebonuses.service.operation;

import com.spimex.purchasebonuses.web.dict.PaymentSource;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PaymentCalculator {

    private final PaymentSource paymentSource;
    private final long startDebit;

    @Getter
    private long resultDebit;
    @Getter
    private long resultEMoneyBonus;
    private CalculationStage currentStage;

    public PaymentCalculator(PaymentSource paymentSource, long debit) {
        this.paymentSource = paymentSource;
        this.startDebit = debit;
        this.currentStage = new ByStoreTypeCalculationStage();
    }

    public void performCalculation() {
        log.info("performCalculation()");
        while (true) {
            CalculationStage nextStage = this.currentStage.doStage(this.paymentSource, this.startDebit);
            this.resultDebit += this.currentStage.getDebit();
            this.resultEMoneyBonus += this.currentStage.getEMoneyBonus();

            if (nextStage == null) {
                break;
            }

            this.setCurrentStage(nextStage);
        }
        this.resultDebit += startDebit;

        log.info("Calculation result - debit: {}, eMoneyBonus: {}", getResultDebit(), getResultEMoneyBonus());
    }

    private void setCurrentStage(CalculationStage currentStage) {
        this.currentStage = currentStage;
    }
}
