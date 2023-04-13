package com.eikefab.config;

@Deprecated
public interface Configuration {

    default int version() {
        return 1;
    }

}
