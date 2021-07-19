package com.meembusoft.ln.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.meembusoft.ln.R;
import com.meembusoft.ln.adapter.OrderListAdapter;
import com.meembusoft.ln.base.BaseActivity;
import com.meembusoft.ln.controller.OrderDetailController;
import com.meembusoft.ln.util.DataUtil;
import com.meembusoft.recyclerview.MRecyclerView;
import com.meembusoft.recyclerview.adapter.RecyclerArrayAdapter;

public class OrdersActivity extends BaseActivity {

    // Toolbar
    private TextView tvTitle;
    private LinearLayout llClose;

    // View items
    private MRecyclerView rvOrders;
    private OrderListAdapter mOrderListAdapter;
    private OrderDetailController mOrderDetailController;

    @Override
    public int initToolbarLayout() {
        return R.layout.toolbar_base;
    }

    @Override
    public int initScreenLayout() {
        return R.layout.activity_orders;
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
        rvOrders = findViewById(R.id.rv_order);
    }

    @Override
    public void initViewsData(Bundle savedInstanceState) {
        // Initialize order detail
        mOrderDetailController = new OrderDetailController(getActivity(), getParentView());
        // Toolbar
        tvTitle.setText(R.string.txt_orders);

        // Setup order recyclerview
        rvOrders.setLayoutManager(new LinearLayoutManager(getActivity()));
        mOrderListAdapter = new OrderListAdapter(getActivity());
        mOrderListAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                mOrderDetailController.openDetails((view.findViewById(R.id.item_view)), mOrderListAdapter.getItem(i));
            }
        });
        // Load order adapter
        rvOrders.setAdapter(mOrderListAdapter);
        // Load order items into adapter
        mOrderListAdapter.addAll(DataUtil.getAllOrders(getActivity()));
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
        if (mOrderDetailController.isDetailFolded()) {
            return;
        }
        finish();
    }

    @Override
    public void initDestroyTasks() {

    }

    @Override
    public void onAllPermissionsAccepted() {

    }

    public void updateTitle(String title) {
        tvTitle.setText(title);
    }
}