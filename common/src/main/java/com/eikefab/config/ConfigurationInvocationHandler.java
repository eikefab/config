package com.eikefab.config;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ConfigurationInvocationHandler implements InvocationHandler {

    private final ConfigurationReader configurationReader;

    public ConfigurationInvocationHandler(ConfigurationReader configurationReader) {
        this.configurationReader = configurationReader;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        final Class<?> clazz = method.getDeclaringClass();

        String path = "";

        if (clazz.isAnnotationPresent(ConfigPath.class)) {
            path = clazz.getAnnotation(ConfigPath.class).value();

            if (!path.endsWith(".")) {
                path += ".";
            }
        }

        if (method.isAnnotationPresent(ConfigPath.class)) {
            final ConfigPath configPath = method.getAnnotation(ConfigPath.class);

            final String value = configPath.value();
            final String actualValue = value.length() == 0 ? Pathfinder.getPath(method) : value;

            path += actualValue.replace('.', configPath.separator());
        } else {
            path += Pathfinder.getPath(method);
        }

        return configurationReader.get(path);
    }

}
