package com.example.zhoushicheng.myapplication.RetrofitAndRxJavaSample.RetrofitService;

import com.example.zhoushicheng.myapplication.RetrofitAndRxJavaSample.module.BaseResponse;
import com.example.zhoushicheng.myapplication.RetrofitAndRxJavaSample.module.HomebannerModule;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by zhoushicheng on 2018/5/15.
 */

public interface AntService {
    @GET("advert/homebanner")
    Observable<BaseResponse<List<HomebannerModule>>> listRepos();
}
