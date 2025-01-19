package com.github.ericsouza.holidays;

import org.openjdk.jmh.annotations.*;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 2, warmups = 1, jvmArgs = {"-Xms3G", "-Xmx3G"})
public class WorkingDaysToCalendarBenchmark {

    @Param({"10", "50", "100", "600"})
    private int workingDays;

    @Param({"2024-01-15", "2027-06-10", "2032-03-05"})
    private String startDateString;

    @Benchmark
    public LocalDate benchmarkCalculateEndDate() {
        return WorkingDaysToCalendar.calculateEndDate(LocalDate.parse(startDateString), workingDays);
    }

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }
}
