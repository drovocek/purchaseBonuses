package com.spimex.purchasebonuses.service.payment.processing.core;

public interface PaymentProcessStageFactory {

    String BASE_IMPL = "paymentProcessStageFactoryImpl";

    CalculationProcessStage getStage(ProcessStageName stageName);
}
