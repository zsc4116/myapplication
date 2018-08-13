package com.example.zhoushicheng.myapplication.RetrofitAndRxJavaSample.OkHttpInterceptor;

import android.content.Context;
import android.support.annotation.RawRes;

import com.example.zhoushicheng.myapplication.Utils.FileUtil;

import java.io.IOException;
import java.util.LinkedHashMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;

/**
 * Created by zhoushicheng on 2018/1/28.
 */

public class MyInterceptor implements Interceptor {

    private final String DEBUG_URL;
    private final int DEBUG_RAW_ID;
    private Context context;

    public MyInterceptor(Context context, String debugUrl, int rawId) {
        this.context = context;
        this.DEBUG_URL = debugUrl;
        this.DEBUG_RAW_ID = rawId;
    }

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        final String url = chain.request().url().toString();
        if (url.contains(DEBUG_URL)) {
            return debugResponse(chain, DEBUG_RAW_ID);
        }
        return chain.proceed(chain.request());
    }

    private okhttp3.Response getResponse(Chain chain, String json) {
        return new okhttp3.Response.Builder()
                .code(200)
                .addHeader("Content-Type", "application/json")
                .body(okhttp3.ResponseBody.create(MediaType.parse("application/json"), json))
                .message("ok")
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .build();
    }

    private okhttp3.Response debugResponse(Chain chain, @RawRes int rawId) {
        final String json = FileUtil.getRawFile(context, rawId);
        return getResponse(chain, json);
    }
    //以上部分为模拟数据的方法


    protected LinkedHashMap<String, String> getUrlParameters(Chain chain) {
        final HttpUrl url = chain.request().url();
        int size = url.querySize();
        final LinkedHashMap<String, String> params = new LinkedHashMap<>();
        for (int i = 0; i < size; i++) {
            params.put(url.queryParameterName(i), url.queryParameterValue(i));
        }
        return params;
    }

    protected String getUrlParameters(Chain chain, String key) {
        final Request request = chain.request();
        return request.url().queryParameter(key);
    }

    protected LinkedHashMap<String, String> getBodyParameters(Chain chain) {
        final FormBody formBody = (FormBody) chain.request().body();
        int size = formBody.size();
        final LinkedHashMap<String, String> params = new LinkedHashMap<>();
        for (int i = 0; i < size; i++) {
            params.put(formBody.name(i), formBody.value(i));
        }
        return params;
    }

    protected String getBodyParameters(Chain chain, String key) {
        return getBodyParameters(chain).get(key);
    }
}
