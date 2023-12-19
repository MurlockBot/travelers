package com.empresa.travelers.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

public class TravelersUtility {
    private static final Random random = new Random();

    public static LocalDateTime getRandomSoon() {
        var randomHours = random.nextInt(5 - 2) + 2;
        var now = LocalDateTime.now();
        return now.plusHours(randomHours);
    }

    public static LocalDateTime getRandomLater() {
        var randomHours = random.nextInt(12 - 6) + 2;
        var now = LocalDateTime.now();
        return now.plusHours(randomHours);
    }

    public static LocalDate getRandomDaySoon() {
        var randomDays = random.nextInt(5 - 2) + 2;
        var now = LocalDate.now();
        return now.plusDays(randomDays);
    }

    public static LocalDate getRandomDayLater() {
        var randomDays = random.nextInt(20 - 8) + 8;
        var now = LocalDate.now();
        return now.plusDays(randomDays);
    }


}
