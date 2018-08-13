package com.example.zhoushicheng.myapplication.ActivityInteractFragmentHelperSample;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.zhoushicheng.myapplication.R;
import com.example.zhoushicheng.myapplication.Utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

public class RestaurantActivity extends AppCompatActivity implements
        InteractionHelper.OnHelperDataChangeListener, FoodCartAdapter.OnItemListener {

    TextView tvCart;

    private List<FoodModule> foods = new ArrayList<>();

    private AlertDialog cartDialog;
    private FoodCartAdapter foodCartAdapter;
    private InteractionHelper interactionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        setFragment();

        interactionHelper = InteractionHelper.get();
        interactionHelper.register(this);

        tvCart = (TextView) findViewById(R.id.tv_restaurant_choose_button);
        tvCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCartDialog();
            }
        });
    }

    private void setFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fl_restaurant_container, RestaurantFragment.newInstance(), RestaurantFragment.TAG);
        transaction.commit();
    }

    private void showCartDialog() {
        if (cartDialog == null) {
            cartDialog = new AlertDialog.Builder(this).create();
            cartDialog.setCanceledOnTouchOutside(false);
            cartDialog.show();
            initCartDialog(cartDialog);
        } else {
            cartDialog.show();
        }
    }

    private void initCartDialog(AlertDialog dialog) {
        final Window window = dialog.getWindow();
        if (window != null) {
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_food_cart, null);
            initCartDialogView(view);
            window.setContentView(view);
            window.setGravity(Gravity.CENTER);
            window.setDimAmount(0);
            final WindowManager.LayoutParams params = window.getAttributes();
            params.width = DisplayUtil.dp2px(this, 300);
            params.height = DisplayUtil.dp2px(this, 450);
            params.gravity = Gravity.RIGHT | Gravity.BOTTOM;
            window.setAttributes(params);
        }
    }

    private void initCartDialogView(View view) {
        RecyclerView rvCart = (RecyclerView) view.findViewById(R.id.rv_dialog_food_cart);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        foodCartAdapter = new FoodCartAdapter(this, foods);
        rvCart.setLayoutManager(linearLayoutManager);
        rvCart.setAdapter(foodCartAdapter);
        foodCartAdapter.setOnItemListener(this);
    }

    @Override
    public void onDataChange(List<FoodModule> foods) {
        Log.e("food", "Activity ——> onDataChange ——> foods = " + foods);


        this.foods.clear();
        this.foods.addAll(foods);
        if (foodCartAdapter != null) {
            foodCartAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(int position) {
        FoodModule food = foods.get(position);
        interactionHelper.updateFoodInCart(food, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        interactionHelper.unRegister(this);
    }
}
