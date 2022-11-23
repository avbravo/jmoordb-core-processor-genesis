package com.jmoordb.core.annotation.view;


import com.jmoordb.core.annotation.*;
import com.jmoordb.core.annotation.enumerations.JakartaSource;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface View {
 
   Class<?> entity();

    JakartaSource jakartaSource() default JakartaSource.JAKARTA;

    String collection() default "";

    /**
     * database_name --> Es un nombre de base de datos que indique el
     * desarrollador {mongodb.database} --> Es el parametro en
     * Microorofile-config.properties
     *
     * @return
     */
    String database() default "{mongodb.database}";
}
