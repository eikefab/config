package com.eikefab.config.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.eikefab.config.ConfigurationLoader;
import com.eikefab.config.Pathfinder;
import org.junit.jupiter.api.Test;

public class ConfigTests {

    @Test
    public void pathTest() {
        final String example = "marketValueLimit";

        assertEquals(Pathfinder.getPath(example), "market.value.limit");
    }

    @Test
    public void configReaderTest() {
        final Config config = new ConfigurationLoader().implement(Config.class, new CustomConfigReader());

        assertEquals(config.hey(), "hello!");
        assertEquals(config.hello(), "hi!");
        assertEquals(config.limit(), 1);
        assertFalse(config.is());
        assertEquals(config.helloThereHowItsGoing(), "Hello! It's fine! :)");
        assertEquals(config.justTestingADefaultPathNotCreatedAutomatically(), "It works.");
    }

}
