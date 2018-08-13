package com.example.zhoushicheng.myapplication.RetrofitAndRxJavaSample.module;

import rx.functions.Func1;

/**
 * 剥离最终数据
 * <p>
 * Created by zhoushicheng on 2018/1/28.
 */
public class PayLoad<T> implements Func1<BaseResponse<T>, T> {

    @Override
    public T call(BaseResponse<T> tBaseResponse) {
        //获取数据失败时，包装一个Fault，抛给上层处理
//        if (!tBaseResponse.isSuccess()) {
//            //throw new Fault(tBaseResponse.status,tBaseResponse.message);
//        }
//        return tBaseResponse.data;
        return  null;
    }
}
