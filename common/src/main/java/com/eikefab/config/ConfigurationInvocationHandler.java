package com.eikefab.config;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ConfigurationInvocationHandler implements InvocationHandler {

    private final ConfigurationReader configurationReader;

    public ConfigurationInvocationHandler(ConfigurationReader configurationReader) {
        this.configurationReader = configurationReader;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        final String path = Pathfinder.getPath(method);

        return configurationReader.get(path);
    }

}
