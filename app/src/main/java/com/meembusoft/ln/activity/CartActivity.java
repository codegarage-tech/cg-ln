package com.meembusoft.ln.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.meembusoft.ln.R;
import com.meembusoft.ln.adapter.CartListAdapter;
import com.meembusoft.ln.base.BaseActivity;
import com.meembusoft.ln.util.DataUtil;
import com.meembusoft.recyclerview.MRecyclerView;

public class CartActivity extends BaseActivity {

    // Toolbar
    private TextView tvTitle;
    private LinearLayout llClose;

    // View items
    private MRecyclerView rvCart;
    private CartListAdapter mCartListAdapter;

    @Override
    public int initToolbarLayout() {
        return R.layout.toolbar_cart;
    }

    @Override
    public int initScreenLayout() {
        return R.layout.activity_cart;
    }

    @Override
    public void initIntentData(Bundle savedInstanceState, Intent intent) {

    }

    @Override
    public void initViews() {
        // Toolbar
        tvTitle = findViewById(R.id.tv_title);
        llClose = findViewById(R.id.ll_close);

        // View items
        rvCart = findViewById(R.id.rv_cart);
    }

    @Override
    public void initViewsData(Bundle savedInstanceState) {
        // Toolbar
        tvTitle.setText(R.string.txt_cart);

        // Setup cart recyclerview
        rvCart.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCartListAdapter = new CartListAdapter(getActivity());
        // Load cart adapter
        rvCart.setAdapter(mCartListAdapter);
        // Load cart items into adapter
        mCartListAdapter.addAll(DataUtil.getAllCartItems());
    }

    @Override
    public void initActions(Bundle savedInstanceState) {
        llClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initBackPress();
            }
        });
    }

    @Override
    public void initActivityOnResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void initBackPress() {
        finish();
    }

    @Override
    public void initDestroyTasks() {

    }
}