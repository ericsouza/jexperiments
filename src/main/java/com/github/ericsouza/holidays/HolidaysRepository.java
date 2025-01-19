package com.github.ericsouza.holidays;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class HolidaysRepository {

    private static Map<Integer, Set<LocalDate>> cache = new ConcurrentHashMap<>();

    private static LocalDate calcularPascoa(int ano) {
        int a = ano % 19;
        int b = ano / 100;
        int c = ano % 100;
        int d = b / 4;
        int e = b % 4;
        int f = (b + 8) / 25;
        int g = (b - f + 1) / 3;
        int h = (19 * a + b - d - g + 15) % 30;
        int i = c / 4;
        int k = c % 4;
        int l = (32 + 2 * e + 2 * i - h - k) % 7;
        int m = (a + 11 * h + 22 * l) / 451;
        int mes = (h + l - 7 * m + 114) / 31;
        int dia = ((h + l - 7 * m + 114) % 31) + 1;

        return LocalDate.of(ano, mes, dia);
    }

    private static List<LocalDate> calcularFeriadosMoveis(int ano) {
        List<LocalDate> feriados = new ArrayList<>();
        LocalDate dataPascoa = calcularPascoa(ano);
        LocalDate tercaCarnaval = dataPascoa.minus(47, ChronoUnit.DAYS);
        feriados.add(tercaCarnaval.minusDays(1));  // Segunda-feira de Carnaval
        feriados.add(tercaCarnaval);                            // Terça-feira de Carnaval
        feriados.add(tercaCarnaval.plusDays(1));      // Quarta-feira de Cinzas
        feriados.add(dataPascoa.minusDays(2));     // Corpus Christi
        feriados.add(dataPascoa.plusDays(60));        // Sexta-Feira santa
        return feriados;
    }

    public static Set<LocalDate> calcularFeriados(int ano) {
        if(cache.containsKey(ano)) {
            return cache.get(ano);
        }

        Set<LocalDate> feriados = new TreeSet<>();

        // Feriados fixos
        feriados.add(LocalDate.of(ano, 1, 1));      // Confraternização Universal
        feriados.add(LocalDate.of(ano, 4, 21));     // Tiradentes
        feriados.add(LocalDate.of(ano, 5, 1));      // Dia do Trabalho
        feriados.add(LocalDate.of(ano, 9, 7));      // Independência do Brasil
        feriados.add(LocalDate.of(ano, 10, 12));    // Nossa Senhora Aparecida
        feriados.add(LocalDate.of(ano, 11, 2));     // Finados
        feriados.add(LocalDate.of(ano, 11, 15));    // Proclamação da República
        feriados.add(LocalDate.of(ano, 11, 20));    // Dia Nacional da Consciência Negra
        feriados.add(LocalDate.of(ano, 12, 25));    // Natal

        feriados.addAll(calcularFeriadosMoveis(ano));

        cache.put(ano, feriados);
        return feriados;
    }
}
