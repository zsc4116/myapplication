package com.example.zhoushicheng.myapplication.RetrofitAndRxJavaSample;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhoushicheng.myapplication.R;
import com.example.zhoushicheng.myapplication.RetrofitAndRxJavaSample.Loader.RepoLoader;
import com.example.zhoushicheng.myapplication.RetrofitAndRxJavaSample.Op.IRxJavaClick;
import com.example.zhoushicheng.myapplication.RetrofitAndRxJavaSample.Op.OpdoOnNextUse;
import com.example.zhoushicheng.myapplication.RetrofitAndRxJavaSample.Op.OpdoOnSubscribeUse;
import com.example.zhoushicheng.myapplication.RetrofitAndRxJavaSample.Op.OpfilterUse;
import com.example.zhoushicheng.myapplication.RetrofitAndRxJavaSample.Op.OpflatMapUse;
import com.example.zhoushicheng.myapplication.RetrofitAndRxJavaSample.Op.OpmapUse;
import com.example.zhoushicheng.myapplication.RetrofitAndRxJavaSample.Op.OpsubscribeOnobserveOnUse;
import com.example.zhoushicheng.myapplication.RetrofitAndRxJavaSample.Op.OpsubscribeOnobserveOnUseAnother;
import com.example.zhoushicheng.myapplication.RetrofitAndRxJavaSample.Op.SimpleUse;
import com.example.zhoushicheng.myapplication.RetrofitAndRxJavaSample.Op.StandardUse;
import com.example.zhoushicheng.myapplication.RetrofitAndRxJavaSample.RetrofitService.AntService;
import com.example.zhoushicheng.myapplication.RetrofitAndRxJavaSample.module.BaseResponse;
import com.example.zhoushicheng.myapplication.RetrofitAndRxJavaSample.module.HomebannerModule;
import com.example.zhoushicheng.myapplication.RetrofitAndRxJavaSample.module.Repo;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class RetrofitAndRxJavaActivity extends AppCompatActivity {

    TextView textView;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_and_rx_java);

        textView = (TextView) findViewById(R.id.tv_retrofit_rxjava_content);
        linearLayout = (LinearLayout) findViewById(R.id.ll_retrofit_rxjava_list);

        initRxJavaSample();

        initRxJavaRetrofitSample();

        //initRxJavaRetrofitComplex();

        okhttpRequest();

    }

    private void okhttpRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                //设置读取数据的时间
                .readTimeout(5, TimeUnit.SECONDS)
                //对象的创建
                .build();
        //创建一个网络请求的对象，如果没有写请求方式，默认的是get
        //在请求对象里面传入链接的URL地址
        Request request = new Request.Builder()
                .url("http://api.srsjk.com/api_1_0//article/userhelps").build();

        //call就是我们可以执行的请求类
        Call call = client.newCall(request);
        //异步方法，来执行任务的处理，一般都是使用异步方法执行的
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("zzz", response.body().string());
            }
        });
    }


    //RxPermission使用
    private void permission() {
        RxPermissions.getInstance(this)
                .request(Manifest.permission.CAMERA)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (granted) {
                            Log.e("zzz", "camera granted");
                        } else {
                            Log.e("zzz", "camera not granted");
                        }
                    }
                });
    }

    //初始化RxJava操作
    private void initRxJavaSample() {
        final ArrayList<IRxJavaClick> list = new ArrayList<>();

        IRxJavaClick standardUse = new StandardUse();
        IRxJavaClick simpleUse = new SimpleUse();
        IRxJavaClick opmapUse = new OpmapUse();
        IRxJavaClick opflatMapUse = new OpflatMapUse();
        IRxJavaClick opfilterUse = new OpfilterUse();
        IRxJavaClick opdoOnNextUse = new OpdoOnNextUse();
        IRxJavaClick opsubscribeOnobserveOnUse = new OpsubscribeOnobserveOnUse();
        IRxJavaClick opsubscribeOnobserveOnUseAnother = new OpsubscribeOnobserveOnUseAnother();
        IRxJavaClick opdoOnSubscribeUse = new OpdoOnSubscribeUse();

        list.add(standardUse);
        list.add(simpleUse);
        list.add(opmapUse);
        list.add(opflatMapUse);
        list.add(opfilterUse);
        list.add(opdoOnNextUse);
        list.add(opsubscribeOnobserveOnUse);
        list.add(opsubscribeOnobserveOnUseAnother);
        list.add(opdoOnSubscribeUse);

        for (int i = 0; i < list.size(); i++) {
            Button button = new Button(this);
            button.setText(list.get(i).getName());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            final int finalI = i;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //标准使用
                    list.get(finalI).reaction(textView);
                }
            });
            linearLayout.addView(button, lp);
        }
    }

    //RxJava+Retrofit简单应用
    private void initRxJavaRetrofitSample() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5, TimeUnit.SECONDS);
        //        builder.addInterceptor(new MyInterceptor(this, "octocat", R.raw.test));//使用OkHttp的Interceptor模拟数据返回

        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                //                .baseUrl("http://api.github.com/")
                .baseUrl("http://api.srsjk.com/api_1_0/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        AntService service = retrofit.create(AntService.class);

        Subscription subscription = service.listRepos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse<List<HomebannerModule>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("zzz", "失败, " + e.toString());
                    }

                    @Override
                    public void onNext(BaseResponse<List<HomebannerModule>> listBaseResponse) {
                        Log.e("zzz", "size = " + listBaseResponse.response.size());
                    }
                });

    }

    //RxJava+Retrofit封装应用
    private void initRxJavaRetrofitComplex() {
        RepoLoader loader = new RepoLoader();
        loader.getRepos("octocat").subscribe(new Action1<List<Repo>>() {
            @Override
            public void call(List<Repo> repos) {
                Log.e("zzz", "response = " + repos.get(1).toString());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable e) {

            }
        });
    }
}
