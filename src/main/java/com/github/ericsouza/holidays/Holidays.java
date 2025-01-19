package com.github.ericsouza.holidays;

import java.time.LocalDate;


public class Holidays {

    public static void main(String[] args) {
        LocalDate dataInicial2 = LocalDate.of(2024, 2, 28); // Data inicial
        int diasUteis2 = 10; // Número de dias úteis a adicionar

        LocalDate dataFinal2 = WorkingDaysToCalendar.calcularDataFinal(dataInicial2, diasUteis2);
        System.out.println("Data final: " + dataFinal2);
    }
}
