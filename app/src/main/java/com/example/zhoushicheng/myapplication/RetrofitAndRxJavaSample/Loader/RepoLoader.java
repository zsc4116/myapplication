package com.example.zhoushicheng.myapplication.RetrofitAndRxJavaSample.Loader;

import com.example.zhoushicheng.myapplication.RetrofitAndRxJavaSample.RetrofitService.GitHubService;
import com.example.zhoushicheng.myapplication.RetrofitAndRxJavaSample.RetrofitService.RetrofitServiceManager;
import com.example.zhoushicheng.myapplication.RetrofitAndRxJavaSample.module.PayLoad;
import com.example.zhoushicheng.myapplication.RetrofitAndRxJavaSample.module.Repo;

import java.util.List;

import rx.Observable;

/**
 * Created by zhoushicheng on 2018/1/28.
 */

public class RepoLoader extends ObjectLoader {
    private GitHubService service;

    public RepoLoader() {
        service = RetrofitServiceManager.getInstance().create(GitHubService.class);
    }

    public Observable<List<Repo>> getRepos(String user) {
        return observe(service.listRepos(user)).map(new PayLoad<List<Repo>>());
    }
}
