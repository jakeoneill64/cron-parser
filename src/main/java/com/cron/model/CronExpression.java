package com.cron.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class CronExpression {

    public enum CronField{
        MINUTE,
        HOUR,
        DAY_OF_MONTH,
        MONTH,
        DAY_OF_WEEK
    }

    private final Set<Integer> minute;
    private final Set<Integer> hour;
    private final Set<Integer> dayOfMonth;
    private final Set<Integer> month;
    private final Set<Integer> dayOfWeek;
    private final String command;

    @Override
    public String toString(){
        String minutes = minute
                .stream()
                .sorted()
                .map(String::valueOf)
                .collect(Collectors.joining(" "));

        String hours = hour
                .stream()
                .sorted()
                .map(String::valueOf)
                .collect(Collectors.joining( " "));

        String daysOfMonth = dayOfMonth
                .stream()
                .sorted()
                .map(String::valueOf)
                .collect(Collectors.joining(" "));

        String months = month
                .stream()
                .sorted()
                .map(String::valueOf)
                .collect(Collectors.joining(" "));

        String daysOfWeek = dayOfWeek
                .stream()
                .sorted()
                .map(String::valueOf)
                .collect(Collectors.joining(" "));

        return """
                minute        %s
                hour          %s
                day of month  %s
                month         %s
                day of week   %s
                command       %s
                """
                .formatted(minutes, hours, daysOfMonth, months, daysOfWeek, command);

    }

}
