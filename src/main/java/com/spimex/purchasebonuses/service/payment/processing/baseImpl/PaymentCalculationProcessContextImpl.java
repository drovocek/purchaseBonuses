package com.spimex.purchasebonuses.service.payment.processing.baseImpl;

import com.spimex.purchasebonuses.service.payment.processing.core.CalculationProcessStage;
import com.spimex.purchasebonuses.service.payment.processing.core.PaymentCalculationProcessContext;
import com.spimex.purchasebonuses.service.payment.processing.core.PaymentProcessStageFactory;
import com.spimex.purchasebonuses.service.payment.processing.core.ProcessStageName;
import com.spimex.purchasebonuses.web.dict.PaymentSource;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Slf4j
@Getter
@Setter
@Component
@Scope("prototype")
@Qualifier(PaymentCalculationProcessContext.BASE_IMPL)
public class PaymentCalculationProcessContextImpl implements PaymentCalculationProcessContext {

    private CalculationProcessStage stage;

    private PaymentSource paymentSource;
    private long paymentAmount;
    private long eMoneyBonus;
    private long commission;

    public PaymentCalculationProcessContextImpl(
            @Qualifier(PaymentProcessStageFactory.BASE_IMPL)
                    PaymentProcessStageFactory stageFactory) {
        this.setStage(stageFactory.getStage(ProcessStageName.CHECK_PAYMENT_SOURCE));
    }

    public void calculate() {
        log.info("calculate()");

        while (processingCondition()) {
            this.stage.process(this);
        }

        log.info("Calculation result - type: {}, paymentAmount: {}, currentEMoneyBonus: {}, currentCommission: {})",
                getPaymentSource(), getPaymentAmount(),
                getEMoneyBonus(), getCommission());
    }

    private boolean processingCondition(){
        return !this.stage.getStageName().equals(ProcessStageName.CALCULATION_COMPLETE);
    }
}