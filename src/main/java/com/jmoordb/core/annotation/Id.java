package com.jmoordb.core.annotation;

import com.jmoordb.core.annotation.enumerations.AutogeneratedActive;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Id {

    String value() default "";
    AutogeneratedActive autogeneratedActive() default  AutogeneratedActive.OFF;
    
}
