package com.vinnilmg.jms.utils;

import java.math.BigDecimal;
import java.util.Random;

public class RandomUtils {

    public static int generateRandomNumber() {
        return new Random()
                .nextInt(99) + 1;
    }

    public static BigDecimal generateRandomPrice() {
        final var randomDoubleGreatherThanOneHundred = 100 + (new Random().nextDouble()) * 100;
        return BigDecimal.valueOf(randomDoubleGreatherThanOneHundred)
                .setScale(2, BigDecimal.ROUND_UP);
    }
}
