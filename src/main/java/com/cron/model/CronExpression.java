package com.cron.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
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
                .map(String::valueOf)
                .collect(Collectors.joining());

        String hours = hour
                .stream()
                .map(String::valueOf)
                .collect(Collectors.joining());

        String daysOfMonth = dayOfMonth
                .stream()
                .map(String::valueOf)
                .collect(Collectors.joining());

        String months = month
                .stream()
                .map(String::valueOf)
                .collect(Collectors.joining());

        String daysOfWeek = dayOfWeek
                .stream()
                .map(String::valueOf)
                .collect(Collectors.joining());

        return """
                minute       %s
                hour         %s
                day of month %s
                month        %s
                day of week  %s
                command      %s
                """
                .formatted(minutes, hours, daysOfMonth, months, daysOfWeek, command);

    }

}
