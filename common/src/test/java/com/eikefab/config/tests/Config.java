package com.eikefab.config.tests;

import com.eikefab.config.ConfigPath;

public interface Config {

    String hey();
    String hello();
    int limit();
    boolean is();
    String helloThereHowItsGoing();

    @ConfigPath("just-testing")
    String justTestingADefaultPathNotCreatedAutomatically();

    @ConfigPath(separator = '-')
    String testSeparator();

}
