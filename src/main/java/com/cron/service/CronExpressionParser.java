package com.cron.service;

import com.cron.model.CronExpression;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CronExpressionParser implements ParserService<CronExpression, String> {

    private final Pattern fieldPattern = Pattern.compile("((\\*)|(\\d+)-(\\d+)|(\\d+))(/(\\d+))?");

    private static final Map<CronExpression.CronField, List<Integer>> rangeByField = Map.of(
        CronExpression.CronField.MINUTE, List.of(0, 59),
        CronExpression.CronField.HOUR, List.of(0, 23),
        CronExpression.CronField.DAY_OF_MONTH, List.of(1, 31),
        CronExpression.CronField.MONTH, List.of(1, 12),
        CronExpression.CronField.DAY_OF_WEEK, List.of(1, 7)
    );

    //parse something like "*/15 0 1,15 * 1-5 /usr/bin/find" into
    /*
      minute [0, 15, 30, 45]
      hour [0]
      dayOfMonth [1, 15]
      month [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
      dayOfWeek [1, 2, 3, 4, 5]
      command "/usr/bin/find"
    */
    @Override
    public CronExpression parse(String toParse) {
        String[] parts = toParse.split("\\s+");

        if(parts.length != 6)
            throw new IllegalArgumentException("could not parse cron expression, the format is incorrect");

        return new CronExpression(
            parseNumericField(parts[0], CronExpression.CronField.MINUTE),
            parseNumericField(parts[1], CronExpression.CronField.HOUR),
            parseNumericField(parts[2], CronExpression.CronField.DAY_OF_MONTH),
            parseNumericField(parts[3], CronExpression.CronField.MONTH),
            parseNumericField(parts[4], CronExpression.CronField.DAY_OF_WEEK),
            parts[5]
        );
    }

    private Set<Integer> parseNumericField(String field, CronExpression.CronField cronField){
        String[] listedUnits = field.split(",");

        //use a hashset because we are only interested in unique values.
        Set<Integer> result = new HashSet<>();

        for(String item: listedUnits) {
            Matcher fieldMatcher = fieldPattern.matcher(item);

            if(!fieldMatcher.find())
                throw new IllegalArgumentException("could not parse cron expression, the format is incorrect");

            int intervalStart = 0, intervalEnd = 0;

            if(fieldMatcher.group(2) != null) { // asterisk is present.
                List<Integer> fieldRange = rangeByField.get(cronField);
                intervalStart = fieldRange.get(0);
                intervalEnd = fieldRange.get(1);
            }

            else if(fieldMatcher.group(5) != null) { // single number is present
                intervalStart = Integer.parseInt(fieldMatcher.group(5));
                intervalEnd = Integer.parseInt(fieldMatcher.group(5));
            }

            else if(fieldMatcher.group(3) != null) { // a range is present ie. 3-5
                intervalStart = Integer.parseInt(fieldMatcher.group(3));
                intervalEnd = Integer.parseInt(fieldMatcher.group(4));
            }

            int iterateInterval = fieldMatcher.group(7) != null ? Integer.parseInt(fieldMatcher.group(7)) : 1;

            int finalIntervalEnd = intervalEnd; // final var for lambda.

            Set<Integer> generated = IntStream
                .iterate(intervalStart, i -> i <= finalIntervalEnd, i -> i + iterateInterval)
                .boxed()
                .collect(Collectors.toSet());

            result.addAll(generated);

        }

        return result;
    }

    private int[] getRepeatedTimes(int start, int end, int interval){
        return IntStream.iterate(start, i -> i <= end, i -> i + interval).toArray();
    }

}
