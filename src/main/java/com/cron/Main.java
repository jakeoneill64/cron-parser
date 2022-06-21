package com.cron;

import com.cron.model.CronExpression;
import com.cron.service.CronExpressionParser;
import com.cron.service.ParserService;

public class Main {

    public static void main(String[] args){
        ParserService<CronExpression, String> expressionParser = new CronExpressionParser();
        try {
            CronExpression parsedCron = expressionParser.parse(args[0]);
            System.out.println(parsedCron);
        }catch(Exception e) {
            System.err.println("could not evaluate cron expression - " + e.getMessage());
        }
    }

}
