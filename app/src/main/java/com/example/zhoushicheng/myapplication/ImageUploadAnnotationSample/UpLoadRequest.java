package com.example.zhoushicheng.myapplication.ImageUploadAnnotationSample;

/**
 * Created by zhoushicheng on 2018/6/9.
 */

public class UpLoadRequest {

    @ArgumentStrump(argument = "picFirst")
    private String picFirst;
    @ArgumentStrump(argument = "picSecond")
    private String picSecond;
    @ArgumentStrump(argument = "picThird")
    private String picThird;

    public String getPicFirst() {
        return picFirst;
    }

    public void setPicFirst(String picFirst) {
        this.picFirst = picFirst;
    }

    public String getPicSecond() {
        return picSecond;
    }

    public void setPicSecond(String picSecond) {
        this.picSecond = picSecond;
    }

    public String getPicThird() {
        return picThird;
    }

    public void setPicThird(String picThird) {
        this.picThird = picThird;
    }

    @Override
    public String toString() {
        return "UpLoadRequest{" +
                "picFirst='" + picFirst + '\'' +
                ", picSecond='" + picSecond + '\'' +
                ", picThird='" + picThird + '\'' +
                '}';
    }
}
