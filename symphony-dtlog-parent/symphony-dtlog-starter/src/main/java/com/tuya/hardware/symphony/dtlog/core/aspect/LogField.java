package com.tuya.hardware.symphony.dtlog.core.aspect;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 */
@Component
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface LogField {
    String name() default "";
    String searchName() default "";
    String searchKey() default "";
    String searchType() default "Text";
    String options() default "";
}
