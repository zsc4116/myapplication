package com.example.zhoushicheng.myapplication.EventBusSample;

/**
 * Created by zhoushicheng on 2017/12/8.
 */

public class EventMessageOne extends EventMessageBase implements EventMessageInterface {

    public String message;

    public EventMessageOne(String message) {
        this.message = message;
        super.baseName = message + " : basename";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getInterfaceMessage() {
        return message + " : interface";
    }
}
