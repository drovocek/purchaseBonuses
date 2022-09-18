package com.spimex.purchasebonuses.service.payment.processing.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PercentCalculator {

    public final static int SHOP_10_E_BONUS = 10;
    public final static int ONLINE_17_E_BONUS = 17;
    public final static int MORE_THEN_300_E_BONUS_30 = 30;
    public final static int BANK_COMMISSION_10 = 10;

    public long calculate(long amount, int percent) {
        return amount * percent / 100;
    }
}
