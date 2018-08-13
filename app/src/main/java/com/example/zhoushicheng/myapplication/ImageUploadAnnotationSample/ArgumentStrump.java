package com.example.zhoushicheng.myapplication.ImageUploadAnnotationSample;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zhoushicheng on 2018/6/9.
 */

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ArgumentStrump {
    String argument() default "";
}
