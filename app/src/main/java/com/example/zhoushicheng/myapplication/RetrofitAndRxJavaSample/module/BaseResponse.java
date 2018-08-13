package com.example.zhoushicheng.myapplication.RetrofitAndRxJavaSample.module;

/**
 * 网络请求响应的基类
 * <p>
 * Created by zhoushicheng on 2018/1/28.
 */
public class BaseResponse<T> {
//    public int status;
//    public String message;
//    public T data;
//
//    public boolean isSuccess() {
//        return status == 200;
//    }

    public int errorCode;
    public T response;
}
