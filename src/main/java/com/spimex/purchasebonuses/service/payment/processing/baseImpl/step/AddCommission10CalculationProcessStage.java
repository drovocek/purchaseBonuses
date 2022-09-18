package com.spimex.purchasebonuses.service.payment.processing.baseImpl.step;

import com.spimex.purchasebonuses.service.payment.processing.baseImpl.PaymentCalculationProcessContextImpl;
import com.spimex.purchasebonuses.service.payment.processing.core.ProcessStageName;
import com.spimex.purchasebonuses.service.payment.processing.util.PercentCalculator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.spimex.purchasebonuses.service.payment.processing.util.PercentCalculator.BANK_COMMISSION_10;

@Slf4j
@Component
public class AddCommission10CalculationProcessStage extends CalculationProcessStageBase {

    @Override
    public ProcessStageName getStageName() {
        return ProcessStageName.ADD_COMMISSION_10;
    }

    @Override
    public void process(PaymentCalculationProcessContextImpl processContext) {
        logInfoContextState(processContext);
        long currentPaymentAmount = processContext.getPaymentAmount();
        long bankCommission = PercentCalculator.calculate(currentPaymentAmount, BANK_COMMISSION_10);
        long currentCommission = processContext.getCommission();

        processContext.setCommission(currentCommission + bankCommission);
        processContext.setStage(this.stageFactory.getStage(ProcessStageName.CALCULATION_COMPLETE));
    }
}
