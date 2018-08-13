package com.example.zhoushicheng.myapplication.RetrofitAndRxJavaSample.module;

/**
 * Created by zhoushicheng on 2018/5/15.
 */

public class HomebannerModule {

    /**
     * image : /public/upload/ad/2018/04-25/aa8c52ed513f1e215fbbb0efb90cb5c2.jpg
     * mediaType : 5
     * mediaValue : 10
     */

    private String image;
    private int mediaType;
    private String mediaValue;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getMediaType() {
        return mediaType;
    }

    public void setMediaType(int mediaType) {
        this.mediaType = mediaType;
    }

    public String getMediaValue() {
        return mediaValue;
    }

    public void setMediaValue(String mediaValue) {
        this.mediaValue = mediaValue;
    }

    @Override
    public String toString() {
        return "HomebannerModule{" +
                "image='" + image + '\'' +
                ", mediaType=" + mediaType +
                ", mediaValue='" + mediaValue + '\'' +
                '}';
    }
}
