package com.github.ericsouza.holidays;

import java.time.LocalDate;


public class BrazilianHolidays {

    public static void main(String[] args) {
        LocalDate initialDate = LocalDate.of(2024, 2, 28);
        int workingDays = 10;

        LocalDate finalDate = WorkingDaysToCalendar.calculateEndDate(initialDate, workingDays);
        System.out.println("Final date: " + finalDate);
    }
}
