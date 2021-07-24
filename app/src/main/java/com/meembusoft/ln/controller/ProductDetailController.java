package com.meembusoft.ln.controller;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alexvasilkov.foldablelayout.UnfoldableView;
import com.alexvasilkov.foldablelayout.shading.GlanceFoldShading;
import com.meembusoft.ln.R;
import com.meembusoft.ln.activity.CategoryActivity;
import com.meembusoft.ln.model.Product;

public class ProductDetailController {

    private ViewGroup parentView;
    private Activity mActivity;

    // Foldable layout
    private View listTouchInterceptor;
    private LinearLayout detailsLayout;
    private UnfoldableView unfoldableView;

    //    // View items
//    private OrderDetail mOrderDetail;
    private String TAG = "ProductDetailController";

    public ProductDetailController(Activity activity, ViewGroup view) {
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
                ((CategoryActivity) mActivity).updateFoldingView(false);
            }

            @Override
            public void onFoldingBack(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(true);
            }

            @Override
            public void onFoldedBack(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(false);
                detailsLayout.setVisibility(View.INVISIBLE);
                ((CategoryActivity) mActivity).updateFoldingView(true);
            }
        });
    }

    public void openDetails(View rowItemView, Product product) {
//        mOrderDetail = DataUtil.getOrderDetail(mActivity, order.getCurrent_status());
//
        // Initialize product detail views
//        initOrderDetailStatus(mOrderDetail.getOrder_status());
//        initOrderDetailInformation(mOrderDetail.getOrder_information());
//        initOrderDetailItems(mOrderDetail.getOrder_items());

        // Unfold view
        unfoldableView.unfold(rowItemView, detailsLayout);
    }

    public boolean isDetailFolded() {
        if (unfoldableView.isUnfolded() || unfoldableView.isUnfolding()) {
            unfoldableView.foldBack();
            return true;
        }
        return false;
    }
}