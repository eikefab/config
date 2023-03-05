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
        final Class<?> clazz = method.getDeclaringClass();

        String path = "";

        if (clazz.isAnnotationPresent(ConfigPath.class)) {
            path = clazz.getAnnotation(ConfigPath.class).value();
        }

        if (method.isAnnotationPresent(ConfigPath.class)) {
            path += method.getAnnotation(ConfigPath.class).value();
        } else {
            path += Pathfinder.getPath(method);
        }

        return configurationReader.get(path);
    }

}
