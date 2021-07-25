package com.meembusoft.ln.controller;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexvasilkov.foldablelayout.UnfoldableView;
import com.alexvasilkov.foldablelayout.shading.GlanceFoldShading;
import com.meembusoft.ln.R;
import com.meembusoft.ln.activity.OrdersActivity;
import com.meembusoft.ln.adapter.OrderDetailItemListAdapter;
import com.meembusoft.ln.adapter.OrderDetailStatusAdapter;
import com.meembusoft.ln.model.Order;
import com.meembusoft.ln.model.OrderDetail;
import com.meembusoft.ln.model.OrderInformation;
import com.meembusoft.ln.model.OrderItem;
import com.meembusoft.ln.model.OrderStatus;
import com.meembusoft.ln.util.DataUtil;
import com.meembusoft.ln.util.Logger;

import java.util.List;

public class OrderDetailController {

    private ViewGroup parentView;
    private Activity mActivity;

    // Foldable layout
    private View listTouchInterceptor;
    private LinearLayout detailsLayout;
    private UnfoldableView unfoldableView;

    // View items
    private OrderDetail mOrderDetail;
    private String TAG = "OrderDetailController";
    private RecyclerView rvOrderDetailStatus, rvOrderDetailItem;
    private OrderDetailStatusAdapter orderDetailStatusAdapter;
    private OrderDetailItemListAdapter orderDetailItemListAdapter;
    private TextView tvOrderReceiversName, tvOrderReceiversMobileNumber, tvOrderReceiverAddress, tvOrderPaymentSystem, tvOrderSubtotal, tvOrderShippingCharge;

    public OrderDetailController(Activity activity, ViewGroup view) {
        mActivity = activity;
        parentView = view;

        initView();
        initFoldableLayout();
    }

    private void initView() {
        listTouchInterceptor = parentView.findViewById(R.id.touch_interceptor_view);
        detailsLayout = parentView.findViewById(R.id.details_layout);
        unfoldableView = parentView.findViewById(R.id.unfoldable_view);
        // View items
        rvOrderDetailStatus = parentView.findViewById(R.id.rv_order_detail_status);
        rvOrderDetailItem = parentView.findViewById(R.id.rv_order_detail_items);
        tvOrderReceiversName = parentView.findViewById(R.id.tv_order_receiver_name);
        tvOrderReceiversMobileNumber = parentView.findViewById(R.id.tv_order_receiver_mobile_number);
        tvOrderReceiverAddress = parentView.findViewById(R.id.tv_order_receiver_address);
        tvOrderPaymentSystem = parentView.findViewById(R.id.tv_order_payment_system);
        tvOrderSubtotal = parentView.findViewById(R.id.tv_order_subtotal);
        tvOrderShippingCharge = parentView.findViewById(R.id.tv_order_shipping_charge);
    }

    private void initFoldableLayout() {
        listTouchInterceptor.setClickable(false);
        detailsLayout.setVisibility(View.INVISIBLE);
        // This is for enabling scrollview inside detail view
        unfoldableView.setGesturesEnabled(false);

        Bitmap glance = BitmapFactory.decodeResource(mActivity.getResources(), R.drawable.unfold_glance);
        unfoldableView.setFoldShading(new GlanceFoldShading(glance));

        unfoldableView.setOnFoldingListener(new UnfoldableView.SimpleFoldingListener() {

            @Override
            public void onUnfolding(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(true);
                detailsLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onUnfolded(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(false);
                updateTitle(mOrderDetail.getOrder_id_name());
            }

            @Override
            public void onFoldingBack(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(true);
            }

            @Override
            public void onFoldedBack(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(false);
                detailsLayout.setVisibility(View.INVISIBLE);
                updateTitle(mActivity.getString(R.string.txt_orders));
            }
        });
    }

    public void openDetails(View rowItemView, Order order) {
        mOrderDetail = DataUtil.getOrderDetail(mActivity, order.getCurrent_status());

        // Initialize order detail views
        initOrderDetailStatus(mOrderDetail.getOrder_status());
        initOrderDetailInformation(mOrderDetail.getOrder_information());
        initOrderDetailItems(mOrderDetail.getOrder_items());

        // Unfold view
        unfoldableView.unfold(rowItemView, detailsLayout);
    }


    public boolean isDetailOpen() {
        return unfoldableView.isUnfolded() || unfoldableView.isUnfolding();
    }

    public void closeDetail(){
        unfoldableView.foldBack();
    }

    private void updateTitle(String title) {
        ((OrdersActivity) mActivity).updateTitle(title);
    }

    private void initOrderDetailStatus(List<OrderStatus> orderStatuses) {
        Logger.d(TAG, "orderStatuses: " + orderStatuses.size());
        orderDetailStatusAdapter = new OrderDetailStatusAdapter(mActivity);
        rvOrderDetailStatus.setLayoutManager(new LinearLayoutManager(mActivity));

        // Disable nested scrolling
        rvOrderDetailStatus.setNestedScrollingEnabled(false);
        ViewCompat.setNestedScrollingEnabled(rvOrderDetailStatus, false);

        rvOrderDetailStatus.setAdapter(orderDetailStatusAdapter);
        orderDetailStatusAdapter.addAll(orderStatuses);
    }

    private void initOrderDetailInformation(OrderInformation orderInformation) {
        if (orderInformation != null) {
            tvOrderReceiversName.setText(orderInformation.getReceiver_name());
            tvOrderReceiversMobileNumber.setText(orderInformation.getReceiver_mobile_number());
            tvOrderReceiverAddress.setText(orderInformation.getReceiver_address());
            tvOrderPaymentSystem.setText(orderInformation.getPayment_system());
            tvOrderSubtotal.setText(mActivity.getString(R.string.txt_amount_with_taka, orderInformation.getSubtotal()));
            tvOrderShippingCharge.setText(mActivity.getString(R.string.txt_amount_with_taka, orderInformation.getShipping_charge()));
        }
    }

    private void initOrderDetailItems(List<OrderItem> orderItems) {
        Logger.d(TAG, "orderItems: " + orderItems.size());
        orderDetailItemListAdapter = new OrderDetailItemListAdapter(mActivity);
        rvOrderDetailItem.setLayoutManager(new LinearLayoutManager(mActivity));

        // Disable nested scrolling
        rvOrderDetailItem.setNestedScrollingEnabled(false);
        ViewCompat.setNestedScrollingEnabled(rvOrderDetailItem, false);

        rvOrderDetailItem.setAdapter(orderDetailItemListAdapter);
        orderDetailItemListAdapter.addAll(orderItems);
    }
}