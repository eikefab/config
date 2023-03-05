package com.eikefab.config.tests;

import com.eikefab.config.ConfigPath;
import com.eikefab.config.Configuration;

public interface Config extends Configuration {

    String hey();
    String hello();
    int limit();
    boolean is();
    String helloThereHowItsGoing();

    @ConfigPath("just-testing")
    String justTestingADefaultPathNotCreatedAutomatically();

}
