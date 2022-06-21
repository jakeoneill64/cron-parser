package com.cron.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
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

    private final int[] minute;
    private final int[] hour;
    private final int[] dayOfMonth;
    private final int[] month;
    private final int[] dayOfWeek;
    private final String command;

    @Override
    public String toString(){
        String minutes = Arrays.stream(minute)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining());

        String hours = Arrays.stream(hour)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining());

        String daysOfMonth = Arrays.stream(dayOfMonth)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining());

        String months = Arrays.stream(month)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining());

        String daysOfWeek = Arrays.stream(dayOfWeek)
                .mapToObj(Integer::toString)
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
