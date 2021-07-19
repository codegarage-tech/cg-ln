package com.meembusoft.ln.controller;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.core.widget.NestedScrollView;

import com.alexvasilkov.foldablelayout.UnfoldableView;
import com.alexvasilkov.foldablelayout.shading.GlanceFoldShading;
import com.meembusoft.ln.R;
import com.meembusoft.ln.activity.OrdersActivity;
import com.meembusoft.ln.model.Order;
import com.meembusoft.ln.util.Logger;

public class OrderDetailController {

    private ViewGroup parentView;
    private Activity mActivity;

    // Foldable layout
    private View listTouchInterceptor;
    private LinearLayout detailsLayout;
    private UnfoldableView unfoldableView;

    // View items
    private Order mOrder;
    private NestedScrollView nsvOrderDetail;
    private String TAG = "OrderDetail";

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
        nsvOrderDetail = parentView.findViewById(R.id.nsv_order_detail);
    }

    private void initViewData() {
        listTouchInterceptor.setClickable(false);
        detailsLayout.setVisibility(View.INVISIBLE);

        Bitmap glance = BitmapFactory.decodeResource(mActivity.getResources(), R.drawable.unfold_glance);
        unfoldableView.setFoldShading(new GlanceFoldShading(glance));

        // View items
        nsvOrderDetail.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
                    Logger.d(TAG, TAG + "nsvOrderDetail>>Scroll DOWN");
                }
                if (scrollY < oldScrollY) {
                    Logger.d(TAG, TAG + "nsvOrderDetail>>Scroll UP");
                }
                if (scrollY == 0) {
                    Logger.d(TAG, TAG + "nsvOrderDetail>>TOP SCROLL");
                }
                if (scrollY == (v.getMeasuredHeight() - v.getChildAt(0).getMeasuredHeight())) {
                    Logger.d(TAG, TAG + "nsvOrderDetail>>BOTTOM SCROLL");
                }
            }
        });
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
                updateTitle(mOrder.getOrder_id_name());
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
        mOrder = order;

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
}