package com.example.zhoushicheng.myapplication.RetrofitAndRxJavaSample.RetrofitService;

import com.example.zhoushicheng.myapplication.RetrofitAndRxJavaSample.module.BaseResponse;
import com.example.zhoushicheng.myapplication.RetrofitAndRxJavaSample.module.Repo;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by zhoushicheng on 2018/1/28.
 */

public interface GitHubService {
    @GET("users/{user}/repos")
    Observable<BaseResponse<List<Repo>>> listRepos(@Path("user") String user);
}
