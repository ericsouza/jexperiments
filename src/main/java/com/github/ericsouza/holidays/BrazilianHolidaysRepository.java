package com.github.ericsouza.holidays;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

class BrazilianHolidaysRepository {

    private static Map<Integer, Set<LocalDate>> cache = new ConcurrentHashMap<>();

    private static LocalDate calculateEaster(int year) {
        int a = year % 19;
        int b = year / 100;
        int c = year % 100;
        int d = b / 4;
        int e = b % 4;
        int f = (b + 8) / 25;
        int g = (b - f + 1) / 3;
        int h = (19 * a + b - d - g + 15) % 30;
        int i = c / 4;
        int k = c % 4;
        int l = (32 + 2 * e + 2 * i - h - k) % 7;
        int m = (a + 11 * h + 22 * l) / 451;
        int month = (h + l - 7 * m + 114) / 31;
        int day = ((h + l - 7 * m + 114) % 31) + 1;

        return LocalDate.of(year, month, day);
    }

    private static Set<LocalDate> calculateMovableHolidays(int year) {
        Set<LocalDate> holidays = new TreeSet<>();
        LocalDate easterDate = calculateEaster(year);
        LocalDate carnivalTuesday = easterDate.minusDays(47);
        holidays.add(carnivalTuesday.minusDays(1));     // Carnival Monday
        holidays.add(carnivalTuesday);                               // Carnival Tuesday
        holidays.add(carnivalTuesday.plusDays(1));         // Ash Wednesday
        holidays.add(easterDate.minusDays(2));          // Good Friday
        holidays.add(easterDate.plusDays(60));             // Corpus Christi
        return holidays;
    }

    static Set<LocalDate> calculateHolidays(int year) {
        if (cache.containsKey(year)) {
            return cache.get(year);
        }

        Set<LocalDate> holidays = new TreeSet<>();

        // Fixed holidays
        holidays.add(LocalDate.of(year, 1, 1));      // New Year's Day
        holidays.add(LocalDate.of(year, 4, 21));     // Tiradentes Day
        holidays.add(LocalDate.of(year, 5, 1));      // Labor Day
        holidays.add(LocalDate.of(year, 9, 7));      // Independence Day
        holidays.add(LocalDate.of(year, 10, 12));    // Our Lady Aparecida Day
        holidays.add(LocalDate.of(year, 11, 2));     // All Souls' Day
        holidays.add(LocalDate.of(year, 11, 15));    // Proclamation of the Republic
        holidays.add(LocalDate.of(year, 11, 20));    // Black Awareness Day
        holidays.add(LocalDate.of(year, 12, 25));    // Christmas Day

        holidays.addAll(calculateMovableHolidays(year));

        cache.put(year, holidays);
        return holidays;
    }
}