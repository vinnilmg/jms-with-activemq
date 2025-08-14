package com.vinnilmg.jms.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.vinnilmg.jms.utils.RandomUtils.generateRandomNumber;
import static com.vinnilmg.jms.utils.RandomUtils.generateRandomPrice;

public class PedidoFactory {

    public static Pedido generateRandomOrder() {
        return new Pedido(
                generateRandomNumber(),
                new Date(120, Calendar.JANUARY, 10),
                new Date(120, Calendar.JANUARY, 15),
                generateRandomPrice(),
                List.of(
                        generateRandomItem(),
                        generateRandomItem(),
                        generateRandomItem()
                )
        );
    }

    private static Item generateRandomItem() {
        final var randomNumber = generateRandomNumber();
        return new Item(randomNumber, "Item " + randomNumber);
    }
}
