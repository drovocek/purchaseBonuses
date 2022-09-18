package com.spimex.purchasebonuses.service.payment.processing.baseImpl.step;

import com.spimex.purchasebonuses.service.payment.processing.baseImpl.PaymentCalculationProcessContextImpl;
import com.spimex.purchasebonuses.service.payment.processing.core.ProcessStageName;
import org.springframework.stereotype.Component;

@Component
public class CompleteCalculationProcessStage extends CalculationProcessStageBase {

    @Override
    public ProcessStageName getStageName() {
        return ProcessStageName.CALCULATION_COMPLETE;
    }

    @Override
    public void process(PaymentCalculationProcessContextImpl processContext) {
        logInfoContextState(processContext);
    }
}
