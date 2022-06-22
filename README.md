<div style="text-align: center;"><h2>Cron Expression Parser</h2>

Tool to parse expressions of the form<br>

`*/15 0 1,15 * 1-5 /usr/bin/find`<br>into something a bit more friendly like<br>
</div>

```
minute        0 15 30 45
hour          0
day of month  1 15
month         1 2 3 4 5 6 7 8 9 10 11 12
day of week   1 2 3 4 5
command       /usr/bin/find
```

To run this you must have a JVM which is compatible with Java 17(LTS) which may be downloaded here: https://www.java.com/en/download/manual.jsp

<h3>Running The Program</h3>

You may either build and run manually or use the prepackaged jar `cron-1.0.jar` contained within the `build` dir.

`java -jar cron-1.0.jar "<expression>"`

_for example_

`java -jar cron-1.0.jar "*/15 0 1,15 * 1-5 /usr/bin/find"` should yield the following output 

```
minute        0 15 30 45
hour          0
day of month  1 15
month         1 2 3 4 5 6 7 8 9 10 11 12
day of week   1 2 3 4 5
command       /usr/bin/find
```

