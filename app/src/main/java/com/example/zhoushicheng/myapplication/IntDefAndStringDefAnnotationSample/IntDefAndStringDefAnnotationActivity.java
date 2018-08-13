package com.example.zhoushicheng.myapplication.IntDefAndStringDefAnnotationSample;

import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.zhoushicheng.myapplication.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class IntDefAndStringDefAnnotationActivity extends AppCompatActivity {

    public static final int SUNDAY = 0;
    public static final int MONDAY = 1;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY = 4;
    public static final int FRIDAY = 5;
    public static final int SATURDAY = 6;

    @IntDef({SUNDAY, MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface WeekDays {}

    @WeekDays int currentDay = SUNDAY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_int_def_and_string_def_annotation);

        setCurrentDay(WEDNESDAY);

        @WeekDays int today = getCurrentDay();

        switch (today){
            case SUNDAY:
                Log.e("INTDEF","SUNDAY" + SUNDAY);
                break;
            case MONDAY:
                Log.e("INTDEF","MONDAY" + MONDAY);
                break;
            case TUESDAY:
                Log.e("INTDEF","TUESDAY" + TUESDAY);
                break;
            case WEDNESDAY:
                Log.e("INTDEF","WEDNESDAY" + WEDNESDAY);
                break;
            case THURSDAY:
                Log.e("INTDEF","THURSDAY" + THURSDAY);
                break;
            case FRIDAY:
                Log.e("INTDEF","FRIDAY" + FRIDAY);
                break;
            case SATURDAY:
                Log.e("INTDEF","SATURDAY" + SATURDAY);
                break;
            default:
                break;
        }
    }

    public void setCurrentDay(@WeekDays int currentDay) {
        this.currentDay = currentDay;
    }

    @WeekDays
    public int getCurrentDay() {
        return currentDay;
    }
}
