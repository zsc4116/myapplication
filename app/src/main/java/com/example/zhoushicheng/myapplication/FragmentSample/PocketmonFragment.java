package com.example.zhoushicheng.myapplication.FragmentSample;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhoushicheng.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PocketmonFragment extends Fragment {


    public static HomeFragment newInstance() {
        final HomeFragment delegate = new HomeFragment();
        return delegate;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pocketmon, container, false);
    }

}
