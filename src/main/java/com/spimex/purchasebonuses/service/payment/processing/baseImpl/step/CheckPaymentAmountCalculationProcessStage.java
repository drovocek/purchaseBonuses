package com.spimex.purchasebonuses.service.payment.processing.baseImpl.step;

import com.spimex.purchasebonuses.service.payment.processing.baseImpl.PaymentCalculationProcessContextImpl;
import com.spimex.purchasebonuses.service.payment.processing.core.ProcessStageName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CheckPaymentAmountCalculationProcessStage extends CalculationProcessStageBase {

    @Override
    public ProcessStageName getStageName() {
        return ProcessStageName.CHECK_PAYMENT_AMOUNT;
    }

    @Override
    public void process(PaymentCalculationProcessContextImpl processContext) {
        logInfoContextState(processContext);
        long paymentAmount = processContext.getPaymentAmount();

        if (paymentAmount < 20L) {
            processContext.setStage(this.stageFactory.getStage(ProcessStageName.ADD_COMMISSION_10));
        } else if (paymentAmount > 300L) {
            processContext.setStage(this.stageFactory.getStage(ProcessStageName.ADD_E_MONEY_BONUS_30));
        } else {
            processContext.setStage(this.stageFactory.getStage(ProcessStageName.CALCULATION_COMPLETE));
        }
    }
}
