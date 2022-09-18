package com.spimex.purchasebonuses.service.payment.processing.baseImpl.step;

import com.spimex.purchasebonuses.service.payment.processing.baseImpl.PaymentCalculationProcessContextImpl;
import com.spimex.purchasebonuses.service.payment.processing.core.ProcessStageName;
import com.spimex.purchasebonuses.service.payment.processing.util.PercentCalculator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AddEMoneyBonus30CalculationProcessStage extends CalculationProcessStageBase {

    @Override
    public ProcessStageName getStageName() {
        return ProcessStageName.ADD_E_MONEY_BONUS_30;
    }

    @Override
    public void process(PaymentCalculationProcessContextImpl processContext) {
        logInfoContextState(processContext);
        long paymentAmount = processContext.getPaymentAmount();
        long increaseEMoneyBonus = PercentCalculator.calculate(paymentAmount, PercentCalculator.MORE_THEN_300_E_BONUS_30);
        long currentEMoneyBonus = processContext.getEMoneyBonus();

        processContext.setEMoneyBonus(currentEMoneyBonus + increaseEMoneyBonus);
        processContext.setStage(this.stageFactory.getStage(ProcessStageName.CALCULATION_COMPLETE));
    }
}
