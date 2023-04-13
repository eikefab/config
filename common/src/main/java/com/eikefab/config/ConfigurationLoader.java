package com.eikefab.config;

import java.lang.reflect.Proxy;

public class ConfigurationLoader {

    public <T> T implement(Class<?> clazz, ConfigurationReader reader) {
        return (T)
                Proxy.newProxyInstance(
                    clazz.getClassLoader(),
                    new Class[] { clazz },
                    new ConfigurationInvocationHandler(reader)
                );
    }

}
