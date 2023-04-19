package com.eikefab.config.tests;

import com.eikefab.config.ConfigSerializer;
import com.eikefab.config.ConfigurationReader;

import java.util.HashMap;
import java.util.Map;

public class CustomConfigReader extends ConfigurationReader {

    private final Map<String, Object> values = new HashMap<>() {{
       put("hey", "hello!");
       put("hello", "hi!");
       put("limit", 1);
       put("is", false);
       put("hello.there.how.its.going", "Hello! It's fine! :)");
       put("just-testing", "It works.");
       put("test-separator", "Separator works.");
    }};

    @Override
    public Object get(String path) {
        return values.get(path);
    }

    @Override
    public Object get(String path, Class<? extends ConfigSerializer<?>> clazz) {
        return get(path);
    }

    @Override
    public void set(String path, Object value) {
        values.put(path, value);
    }

    @Override
    public void save() {

    }
}
