package com.example.zhoushicheng.myapplication.AnnotationSample;

/**
 * Created by zhoushicheng on 2017/11/7.
 */

public enum Gender {
    MAN {
        public String getName() {
            return "male";
        }
    }, WOMAN {
        public String getName() {
            return "female";
        }
    };

    public abstract String getName();
}
