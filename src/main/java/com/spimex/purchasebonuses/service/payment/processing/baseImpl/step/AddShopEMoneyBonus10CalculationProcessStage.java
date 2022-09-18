package com.spimex.purchasebonuses.service.payment.processing.baseImpl.step;

import com.spimex.purchasebonuses.service.payment.processing.baseImpl.PaymentCalculationProcessContextImpl;
import com.spimex.purchasebonuses.service.payment.processing.core.ProcessStageName;
import com.spimex.purchasebonuses.service.payment.processing.util.PercentCalculator;
import org.springframework.stereotype.Component;

@Component
public class AddShopEMoneyBonus10CalculationProcessStage extends CalculationProcessStageBase {

    @Override
    public ProcessStageName getStageName() {
        return ProcessStageName.ADD_SHOP_E_MONEY_BONUS_10;
    }

    @Override
    public void process(PaymentCalculationProcessContextImpl processContext) {
        logInfoContextState(processContext);
        long paymentAmount = processContext.getPaymentAmount();
        long increaseEMoneyBonus = PercentCalculator.calculate(paymentAmount, PercentCalculator.SHOP_10_E_BONUS);
        long currentEMoneyBonus = processContext.getEMoneyBonus();

        processContext.setEMoneyBonus(currentEMoneyBonus + increaseEMoneyBonus);
        processContext.setStage(this.stageFactory.getStage(ProcessStageName.CHECK_PAYMENT_AMOUNT));
    }
}
