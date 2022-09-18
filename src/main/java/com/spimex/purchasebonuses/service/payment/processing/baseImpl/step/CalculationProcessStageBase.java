package com.spimex.purchasebonuses.service.payment.processing.baseImpl.step;

import com.spimex.purchasebonuses.service.payment.processing.baseImpl.PaymentCalculationProcessContextImpl;
import com.spimex.purchasebonuses.service.payment.processing.baseImpl.PaymentProcessStageFactoryImpl;
import com.spimex.purchasebonuses.service.payment.processing.core.CalculationProcessStage;
import com.spimex.purchasebonuses.service.payment.processing.core.PaymentProcessStageFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;

@Slf4j
public abstract class CalculationProcessStageBase implements CalculationProcessStage {

    @Lazy
    @Autowired
    @Qualifier(PaymentProcessStageFactory.BASE_IMPL)
    protected PaymentProcessStageFactory stageFactory;

    protected void logInfoContextState(PaymentCalculationProcessContextImpl processContext) {
        log.info("{}.process(type: {}, paymentAmount: {}, currentEMoneyBonus: {}, currentCommission: {})",
                getStageName(),
                processContext.getPaymentSource(), processContext.getPaymentAmount(),
                processContext.getEMoneyBonus(), processContext.getCommission());
    }
}
