package com.example.zhoushicheng.myapplication.ThreadPoolSample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.example.zhoushicheng.myapplication.R;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolSampleActivity extends AppCompatActivity {

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE = 1;

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger();

        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r, "task #" + mCount.getAndIncrement());
        }
    };

    private static final BlockingDeque<Runnable> sPoolWorkQueue = new LinkedBlockingDeque<>(128);

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE,
            TimeUnit.MINUTES, sPoolWorkQueue, sThreadFactory);
    Runnable command = new Runnable() {
        @Override
        public void run() {
            //TODO
        }
    };

    Callable<String> callable = new Callable<String>() {
        @Override
        public String call() throws Exception {
            return "callable";
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool_sample);

        threadPoolExecutor.execute(command);

        ScheduledThreadPoolExecutor scheduledThreadPool = new ScheduledThreadPoolExecutor(4);
        /**2000ms后执行*/
        scheduledThreadPool.schedule(command, 2000, TimeUnit.MILLISECONDS);
        /**延迟10ms后，每隔1000ms执行一次*/
        scheduledThreadPool.scheduleAtFixedRate(command, 10, 1000, TimeUnit.MILLISECONDS);

        ScheduledFuture<String> result = scheduledThreadPool.schedule(callable, 2000, TimeUnit.MILLISECONDS);
        try {
            String value = result.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
