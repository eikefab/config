package com.eikefab.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.METHOD, ElementType.TYPE })
public @interface ConfigPath {

    String value() default "";
    char separator() default '.';
    Class<? extends ConfigSerializer<?>>[] serializers() default {};
    boolean raw() default false;

}
