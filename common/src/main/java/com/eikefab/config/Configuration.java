package com.eikefab.config;

public interface Configuration {

    default int version() {
        return 1;
    }

}
