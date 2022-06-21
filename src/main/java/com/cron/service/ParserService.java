package com.cron.service;

public interface ParserService <S, T>{

    S parse(T toParse);

}
