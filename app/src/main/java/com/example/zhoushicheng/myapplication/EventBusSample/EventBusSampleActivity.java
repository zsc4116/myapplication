package com.example.zhoushicheng.myapplication.EventBusSample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.zhoushicheng.myapplication.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventBusSampleActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.content_eventmessage_one_text)
    TextView contentView1 = null;

    TextView contentView2;
    TextView contentView3;
    TextView contentView4;
    Button button1;
    Button button2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus_sample);

        ButterKnife.bind(this);

        EventBus.getDefault().register(this);

        //        contentView1 = (TextView) findViewById(R.id.content_eventmessage_one_text);
        contentView2 = (TextView) findViewById(R.id.content_eventmessage_two_text);
        contentView3 = (TextView) findViewById(R.id.content_eventmessage_base_text);
        contentView4 = (TextView) findViewById(R.id.content_eventmessage_interface_text);

        button1 = (Button) findViewById(R.id.eventmessage_one_bt);
        button2 = (Button) findViewById(R.id.eventmessage_two_bt);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);


    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetEventBusMessageOne(EventMessageOne eventMsg) {
        contentView1.setText(eventMsg.getMessage());

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetEventBusMessageTwo(EventMessageTwo eventMsg) {
        contentView2.setText("current thread : " + Thread.currentThread().getId() + ", " + Thread.currentThread().getName() +
                '\n' + eventMsg.getMessage());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetEventBusMessageTwoAgain(EventMessageTwo eventMsg) {
        contentView1.setText("current thread : " + Thread.currentThread().getId() + ", " + Thread.currentThread().getName() +
                '\n' + eventMsg.getMessage());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetEventMessageBase(EventMessageBase eventMsg) {
        contentView3.setText(eventMsg.getBaseName());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetEventMessageInterface(EventMessageInterface eventMsg) {
        contentView4.setText(eventMsg.getInterfaceMessage());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.eventmessage_one_bt:
                EventBus.getDefault().post(new EventMessageOne("tuogusa"));
                break;
            case R.id.eventmessage_two_bt:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new EventMessageTwo("thread : " +
                                Thread.currentThread().getId() + ", " + Thread.currentThread().getName()));
                    }
                }).start();
                break;
        }

    }

}
