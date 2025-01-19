package com.github.ericsouza.holidays;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

public class WorkingDaysToCalendar {
    public static LocalDate calcularDataFinal(LocalDate dataInicial, int diasUteis) {
        if (diasUteis < 0) {
            throw new IllegalArgumentException("O número de dias úteis deve ser não negativo.");
        }
        if (diasUteis == 0) {
            return dataInicial;
        }

        LocalDate dataAtual = dataInicial;
        int diasUteisPassados = 0;
        int anoInicial = dataInicial.getYear();
        Set<LocalDate> feriados = HolidaysRepository.calcularFeriados(anoInicial);

        while (diasUteisPassados < diasUteis) {
            dataAtual = dataAtual.plusDays(1);
            if (dataAtual.getYear() != anoInicial) {
                anoInicial = dataAtual.getYear();
                feriados = HolidaysRepository.calcularFeriados(anoInicial);
            }
            if (isDiaUtil(dataAtual, feriados)) {
                diasUteisPassados++;
            }
        }

        return dataAtual;
    }

    private static boolean isDiaUtil(LocalDate data, Set<LocalDate> feriados) {
        return !DayOfWeek.SATURDAY.equals(data.getDayOfWeek())
                && !DayOfWeek.SUNDAY.equals(data.getDayOfWeek())
                && !feriados.contains(data);
    }
}
