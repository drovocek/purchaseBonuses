package com.spimex.purchasebonuses.service.payment.processing.core;

import com.spimex.purchasebonuses.service.payment.processing.baseImpl.PaymentCalculationProcessContextImpl;

public interface CalculationProcessStage {

    ProcessStageName getStageName();

    void process(PaymentCalculationProcessContextImpl processContext);
}