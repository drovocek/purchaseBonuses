package com.spimex.purchasebonuses.service.payment;

import com.spimex.purchasebonuses.service.BankAccountService;
import com.spimex.purchasebonuses.service.payment.processing.core.PaymentCalculationProcessContext;
import com.spimex.purchasebonuses.web.dict.PaymentSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentService {

    private final BankAccountService bankAccountService;
    private final ObjectFactory<PaymentCalculationProcessContext> paymentProcessContextFabric;

    public PaymentService(BankAccountService bankAccountService,
                          @Qualifier(PaymentCalculationProcessContext.BASE_IMPL)
                                  ObjectFactory<PaymentCalculationProcessContext> paymentProcessContextFabric) {
        this.bankAccountService = bankAccountService;
        this.paymentProcessContextFabric = paymentProcessContextFabric;
    }

    public void makePayment(PaymentSource paymentSource, long paymentAmount) {
        log.info("makePayment(paymentSource: {}, paymentAmount: {})", paymentSource, paymentAmount);
        PaymentCalculationProcessContext processContext = this.paymentProcessContextFabric.getObject();
        processContext.setPaymentSource(paymentSource);
        processContext.setPaymentAmount(paymentAmount);
        processContext.calculate();

        long resultPaymentAmount = processContext.getPaymentAmount();
        long resultCommission = processContext.getCommission();
        long eMoneyBonus = processContext.getEMoneyBonus();

        sendToStoreAccountMock(this.bankAccountService.getMoney(paymentSource, resultPaymentAmount));
        sendToBankAccountMock(this.bankAccountService.getMoney(paymentSource, resultCommission));
        this.bankAccountService.addEMoney(eMoneyBonus);
    }

    private void sendToStoreAccountMock(long commission) {
        if (commission > 0) {
            log.info("Send to store account - {}", commission);
        }
    }

    private void sendToBankAccountMock(long paymentAmount) {
        log.info("Send to bank account - {}", paymentAmount);
    }
}
