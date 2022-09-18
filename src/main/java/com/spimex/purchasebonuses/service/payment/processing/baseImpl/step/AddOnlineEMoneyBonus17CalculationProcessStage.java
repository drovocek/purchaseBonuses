package com.spimex.purchasebonuses.service.payment.processing.baseImpl.step;

import com.spimex.purchasebonuses.service.payment.processing.baseImpl.PaymentCalculationProcessContextImpl;
import com.spimex.purchasebonuses.service.payment.processing.core.ProcessStageName;
import com.spimex.purchasebonuses.service.payment.processing.util.PercentCalculator;
import org.springframework.stereotype.Component;

@Component
public class AddOnlineEMoneyBonus17CalculationProcessStage extends CalculationProcessStageBase {

    @Override
    public ProcessStageName getStageName() {
        return ProcessStageName.ADD_ONLINE_E_MONEY_BONUS_17;
    }

    @Override
    public void process(PaymentCalculationProcessContextImpl processContext) {
        logInfoContextState(processContext);
        long paymentAmount = processContext.getPaymentAmount();
        long increaseEMoneyBonus = PercentCalculator.calculate(paymentAmount, PercentCalculator.ONLINE_17_E_BONUS);
        long currentEMoneyBonus = processContext.getEMoneyBonus();

        processContext.setEMoneyBonus(currentEMoneyBonus + increaseEMoneyBonus);
        processContext.setStage(this.stageFactory.getStage(ProcessStageName.CHECK_PAYMENT_AMOUNT));
    }
}
