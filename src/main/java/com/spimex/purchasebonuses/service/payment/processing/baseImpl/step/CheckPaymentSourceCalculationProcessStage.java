package com.spimex.purchasebonuses.service.payment.processing.baseImpl.step;

import com.spimex.purchasebonuses.service.payment.processing.baseImpl.PaymentCalculationProcessContextImpl;
import com.spimex.purchasebonuses.service.payment.processing.core.ProcessStageName;
import com.spimex.purchasebonuses.web.dict.PaymentSource;
import com.spimex.purchasebonuses.exception.NotSupportedTypeException;
import org.springframework.stereotype.Component;

import static com.spimex.purchasebonuses.exception.NotSupportedTypeException.NOT_SUPPORTED_TEMPLATE;

@Component
public class CheckPaymentSourceCalculationProcessStage extends CalculationProcessStageBase {

    @Override
    public ProcessStageName getStageName() {
        return ProcessStageName.CHECK_PAYMENT_SOURCE;
    }

    @Override
    public void process(PaymentCalculationProcessContextImpl processContext) {
        logInfoContextState(processContext);
        PaymentSource paymentSource = processContext.getPaymentSource();

        if (PaymentSource.Online.equals(paymentSource)) {
            processContext.setStage(this.stageFactory.getStage(ProcessStageName.ADD_ONLINE_E_MONEY_BONUS_17));
        } else if (PaymentSource.Shop.equals(paymentSource)) {
            processContext.setStage(this.stageFactory.getStage(ProcessStageName.ADD_SHOP_E_MONEY_BONUS_10));
        } else {
            throw new NotSupportedTypeException(NOT_SUPPORTED_TEMPLATE.formatted(paymentSource));
        }
    }
}
