package com.spimex.purchasebonuses.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PercentCalculator {

    public final static int SHOP_BONUS = 10;
    public final static int ONLINE_BONUS = 17;
    public final static int MORE_THEN_300 = 30;
    public final static int COMMISSION = 10;

    public long calculate(long amount, int percent) {
        return amount * percent / 100;
    }
}
