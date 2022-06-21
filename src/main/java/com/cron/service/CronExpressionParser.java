package com.cron.service;

import com.cron.model.CronExpression;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CronExpressionParser implements ParserService<CronExpression, String> {

    private static final Map<CronExpression.CronField, List<Integer>> rangeByField = Map.of(
        CronExpression.CronField.MINUTE.MINUTE, List.of(0, 59),
        CronExpression.CronField.HOUR, List.of(0, 23),
        CronExpression.CronField.DAY_OF_MONTH, List.of(1, 31),
        CronExpression.CronField.MONTH, List.of(0, 11),
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

    private int[] parseNumericField(String field, CronExpression.CronField fieldName){
        String[] listedUnits = field.split(",");

        //use a hashset because we are only interested in unique values.
        Set<Integer> result = new HashSet<>();
        for(String item: listedUnits) {
            if(item.charAt(0).equals("")){

            }
        }
    }

}
