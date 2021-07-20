package com.meembusoft.ln.controller;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alexvasilkov.foldablelayout.UnfoldableView;
import com.alexvasilkov.foldablelayout.shading.GlanceFoldShading;
import com.meembusoft.ln.R;
import com.meembusoft.ln.activity.OrdersActivity;
import com.meembusoft.ln.adapter.OrderDetailStatusAdapter;
import com.meembusoft.ln.model.Order;
import com.meembusoft.ln.model.OrderDetail;
import com.meembusoft.ln.util.DataUtil;
import com.meembusoft.recyclerview.MRecyclerView;

public class OrderDetailController {

    private ViewGroup parentView;
    private Activity mActivity;

    // Foldable layout
    private View listTouchInterceptor;
    private LinearLayout detailsLayout;
    private UnfoldableView unfoldableView;

    // View items
    private OrderDetail mOrderDetail;
    private String TAG = "OrderDetail";
    private MRecyclerView rvOrderDetailStatus;
    private OrderDetailStatusAdapter orderDetailStatusAdapter;

    public OrderDetailController(Activity activity, ViewGroup view) {
        mActivity = activity;
        parentView = view;

        initView();
        initViewData();
        initActions();
    }

    private void initView() {
        listTouchInterceptor = parentView.findViewById(R.id.touch_interceptor_view);
        detailsLayout = parentView.findViewById(R.id.details_layout);
        unfoldableView = parentView.findViewById(R.id.unfoldable_view);
        // View items
        rvOrderDetailStatus = parentView.findViewById(R.id.rv_order_detail_status);
    }

    private void initViewData() {
        listTouchInterceptor.setClickable(false);
        detailsLayout.setVisibility(View.INVISIBLE);
        // This is for enabling scrollview inside detail view
        unfoldableView.setGesturesEnabled(false);

        Bitmap glance = BitmapFactory.decodeResource(mActivity.getResources(), R.drawable.unfold_glance);
        unfoldableView.setFoldShading(new GlanceFoldShading(glance));

        // View items

    }

    private void initActions() {
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

        initOrderDetailStatus();

//        LinearLayout llImageBackground = (LinearLayout) detailsLayout.findViewById(R.id.ll_image_bg);
//        TextView tvDetailFileName = (TextView) detailsLayout.findViewById(R.id.tv_detail_file_name);
//        TextView tvDetailFileLocation = (TextView) detailsLayout.findViewById(R.id.tv_detail_file_location);
//
//        if (deletedFile.getTags().size() > 0) {
//            Tag firstTag = deletedFile.getTags().get(0);
//            GradientDrawable drawable = new GradientDrawable();
//            drawable.setColor(firstTag.getColor());
//            llImageBackground.setBackgroundDrawable(drawable);
//        }
//
//        tvDetailFileName.setText(deletedFile.getOriginFileName());
//        tvDetailFileLocation.setText(deletedFile.getOriginFilePath());

        unfoldableView.unfold(rowItemView, detailsLayout);
    }

    public boolean isDetailFolded() {
        if (unfoldableView.isUnfolded() || unfoldableView.isUnfolding()) {
            unfoldableView.foldBack();
            return true;
        }
        return false;
    }

    private void updateTitle(String title) {
        ((OrdersActivity) mActivity).updateTitle(title);
    }

    private void initOrderDetailStatus() {
        orderDetailStatusAdapter = new OrderDetailStatusAdapter(mActivity);
        rvOrderDetailStatus.setLayoutManager(new LinearLayoutManager(mActivity));

        //Disable nested scrolling
        rvOrderDetailStatus.setNestedScrollingEnabled(false);
        ViewCompat.setNestedScrollingEnabled(rvOrderDetailStatus, false);

        rvOrderDetailStatus.setAdapter(orderDetailStatusAdapter);
        orderDetailStatusAdapter.addAll(mOrderDetail.getStatus_list());
    }
}