package com.example.zhoushicheng.myapplication.PaypalSample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.braintreepayments.api.BraintreeFragment;
import com.braintreepayments.api.exceptions.InvalidArgumentException;
import com.example.zhoushicheng.myapplication.R;

public class BraintreeBrowserSwitchActivity extends AppCompatActivity {

    BraintreeFragment mBraintreeFragment;
    String mAuthorization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_braintree_browser_switch);

        try {
            mBraintreeFragment = BraintreeFragment.newInstance(this, mAuthorization);
            // mBraintreeFragment is ready to use!
        } catch (InvalidArgumentException e) {
            // There was an issue with your authorization string.
        }
    }
}
