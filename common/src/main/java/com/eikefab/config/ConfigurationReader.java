package com.eikefab.config;

import java.util.Map;

public abstract class ConfigurationReader {

    public abstract Object get(String path, boolean raw);
    public abstract Object get(String path, Class<? extends ConfigSerializer<?>> clazz);
    public abstract void set(String path, Object value);
    public abstract void save();

    public final <T> void set(String path, T value, Class<? extends ConfigSerializer<T>> clazz) {
        try {
            final ConfigSerializer<T> serializer = clazz.newInstance();
            final Map<String, Object> values = serializer.serialize(value);

            for (Map.Entry<String, Object> entry : values.entrySet()) {
                final String actualPath = path + "." + entry.getKey();

                set(actualPath, entry.getValue());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
