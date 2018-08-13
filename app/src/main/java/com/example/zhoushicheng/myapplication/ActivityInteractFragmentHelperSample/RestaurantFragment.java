package com.example.zhoushicheng.myapplication.ActivityInteractFragmentHelperSample;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhoushicheng.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantFragment extends Fragment implements
        InteractionHelper.OnHelperDataChangeListener, FoodAdapter.OnItemListener {


    private RecyclerView recyclerView;

    private List<FoodModule> foods;

    private FoodAdapter mAdapter;
    private InteractionHelper interactionHelper;


    public static final String TAG = "RestaurantFragment";

    public RestaurantFragment() {

    }

    public static final RestaurantFragment newInstance() {
        RestaurantFragment fragment = new RestaurantFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant, container, false);
        setUpData();
        setUpView(view);
        return view;
    }

    private void setUpData() {
        foods = new ArrayList<>();
        foods.add(new FoodModule("AAA"));
        foods.add(new FoodModule("BBB"));
        foods.add(new FoodModule("CCC"));
        foods.add(new FoodModule("DDD"));
        foods.add(new FoodModule("EEE"));
        foods.add(new FoodModule("FFF"));
        foods.add(new FoodModule("GGG"));
        foods.add(new FoodModule("HHH"));
        foods.add(new FoodModule("III"));
        foods.add(new FoodModule("JJJ"));
        foods.add(new FoodModule("KKK"));
        foods.add(new FoodModule("LLL"));
        foods.add(new FoodModule("MMM"));
        foods.add(new FoodModule("NNN"));
    }

    private void setUpView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_fragment_restaurant);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mAdapter = new FoodAdapter(getContext(), foods);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemListener(this);

        interactionHelper = InteractionHelper.get();
        interactionHelper.register(this);
    }


    @Override
    public void onItemClick(int position) {
        interactionHelper.updateFoodInCart(foods.get(position), this);
    }

    @Override
    public void onDataChange(List<FoodModule> foods) {
        Log.e("food", "Fragment ——> onDataChange ——> foods = " + foods);
        for (int i = 0; i < this.foods.size(); i++) {
            for (int j = 0; j < foods.size(); j++) {
                if (this.foods.get(i).equals(foods.get(j))) {
                    this.foods.set(i, foods.get(j));
                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        interactionHelper.unRegister(this);
    }
}
