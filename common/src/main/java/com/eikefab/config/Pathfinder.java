package com.eikefab.config;

import java.lang.reflect.Method;

public final class Pathfinder {

    public static String getPath(String name) {
        final StringBuilder path = new StringBuilder();

        for (char value : name.toCharArray()) {
            final String letter = Character.toString(value);

            if (letter.toUpperCase().equals(letter)) {
                path.append(".");
            }

            path.append(letter.toLowerCase());
        }

        return path.toString();
    }

    public static String getPath(Method method) {
        return getPath(method.getName());
    }

}
