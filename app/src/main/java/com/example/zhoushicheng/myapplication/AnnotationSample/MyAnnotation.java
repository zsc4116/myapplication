package com.example.zhoushicheng.myapplication.AnnotationSample;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zhoushicheng on 2017/11/7.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface MyAnnotation {
    String color() default "blue";

    String value() default "tuogusa";

    int[] array() default {1, 2, 3};

    Gender gender() default Gender.MAN;

    MetaAnnotation metaAnnotation() default @MetaAnnotation(birthday = "1987");

    String number() default "004946";
}
