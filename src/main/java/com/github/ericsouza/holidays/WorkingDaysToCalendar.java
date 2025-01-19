package com.github.ericsouza.holidays;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

public class WorkingDaysToCalendar {

    private static boolean isWorkingDay(LocalDate date, Set<LocalDate> holidays) {
        return !DayOfWeek.SATURDAY.equals(date.getDayOfWeek())
                && !DayOfWeek.SUNDAY.equals(date.getDayOfWeek())
                && !holidays.contains(date);
    }

    public static LocalDate calculateEndDate(LocalDate startDate, int workingDays) {
        if (workingDays < 0) {
            throw new IllegalArgumentException("The number of working days must be non-negative.");
        }
        if (workingDays == 0) {
            return startDate;
        }

        LocalDate currentDate = startDate;
        int elapsedWorkingDays = 0;
        int currentYearForHolidaysCalculation = startDate.getYear();
        Set<LocalDate> holidays = BrazilianHolidaysRepository.calculateHolidays(currentYearForHolidaysCalculation);

        while (elapsedWorkingDays < workingDays) {
            currentDate = currentDate.plusDays(1);
            if (currentDate.getYear() != currentYearForHolidaysCalculation) {
                currentYearForHolidaysCalculation = currentDate.getYear();
                holidays = BrazilianHolidaysRepository.calculateHolidays(currentYearForHolidaysCalculation);
            }
            if (isWorkingDay(currentDate, holidays)) {
                elapsedWorkingDays++;
            }
        }

        return currentDate;
    }
}