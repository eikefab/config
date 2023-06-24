package com.eikefab.config;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ConfigurationInvocationHandler implements InvocationHandler {

    private final ConfigurationReader configurationReader;

    public ConfigurationInvocationHandler(ConfigurationReader configurationReader) {
        this.configurationReader = configurationReader;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
        final Class<?> clazz = method.getDeclaringClass();

        String path = "";
        boolean raw = false;

        if (clazz.isAnnotationPresent(ConfigPath.class)) {
            final ConfigPath configPath = clazz.getAnnotation(ConfigPath.class);

            path = clazz.getAnnotation(ConfigPath.class).value();
            raw = configPath.raw();

            if (!path.isEmpty() && !path.endsWith(".")) {
                path += ".";
            }
        }

        if (method.isAnnotationPresent(ConfigPath.class)) {
            final ConfigPath configPath = method.getAnnotation(ConfigPath.class);

            final Class<? extends ConfigSerializer<?>>[] serializers = configPath.serializers();
            final String value = configPath.value();

            final Class<? extends ConfigSerializer<?>> serializer = serializers.length == 0 ? null : serializers[0];
            final String actualValue = value.length() == 0 ? Pathfinder.getPath(method) : value;

            path += actualValue.replace('.', configPath.separator());
            raw = configPath.raw();

            if (serializer != null) {
                return configurationReader.get(path, serializer);
            }
        } else {
            path += Pathfinder.getPath(method);
        }

        return configurationReader.get(path, raw);
    }

}
