package com.eikefab.config;

public abstract class ConfigurationReader {

    public abstract Object get(String path);
    public abstract void set(String path, Object value);
    public abstract void save();

}
