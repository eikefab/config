package com.eikefab.config;

import java.util.List;
import java.util.Map;

public abstract class ConfigSerializer<T> {

    public abstract List<String> fields();
    public abstract Map<String, Object> serialize(T item);
    public abstract T deserialize(Map<String, Object> item);

}
