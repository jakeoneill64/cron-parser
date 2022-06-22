package com.cron.service;

import com.cron.model.CronExpression;
import org.junit.*;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


class CronExpressionParserTest {

    @Test
    public void testParseValid(){

        ParserService<CronExpression, String> parser = new CronExpressionParser();

        String[] tests = {
            "*/5 */2 13 4,8 1,2,3,4,6 /opt/hbase/bin/standalone.sh",
            "*/5 9-17 4-5 * * /usr/bin/find",
            "5/3,30-40,55 */13 1 * * /bin/bash"
        };

        CronExpression[] desiredOutputs = {

            new CronExpression(
                Set.of(0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55),
                Set.of(0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22),
                Set.of(13),
                Set.of(4, 8),
                Set.of(1, 2, 3, 4, 6),
                "/opt/hbase/bin/standalone.sh"
            ),

            new CronExpression(
                Set.of(0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55),
                Set.of(9, 10, 11, 12, 13, 14, 15, 16, 17),
                Set.of(4, 5),
                Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
                Set.of(1, 2, 3, 4, 5, 6, 7),
                "/usr/bin/find"
            ),

            new CronExpression(
                    Set.of(5, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 55),
                    Set.of(0, 13),
                    Set.of(1),
                    Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
                    Set.of(1, 2, 3, 4, 5, 6, 7),
                    "/bin/bash"
            ),


        };

        List<CronExpression> expressions = Arrays.stream(tests).map(parser::parse).collect(Collectors.toList());


        for(int i = 0; i < expressions.size(); i++) {
            Assert.assertEquals(expressions.get(i), desiredOutputs[i]);
        }


    }

    @Test
    public void testParseNonValid(){

        ParserService<CronExpression, String> parser = new CronExpressionParser();

        String[] tests = {
                "*/5 25 13 4,8 1,2,3,4,6 /opt/hbase/bin/standalone.sh",
                "*/5 9-17 4-5 * * /usr/ bin/find",
        };

        Arrays.stream(tests)
            .forEach(testExpression -> Assert.assertThrows(IllegalArgumentException.class, () -> parser.parse(testExpression)));

    }

}