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
import com.meembusoft.ln.model.Order;

public class OrderDetailController {

    private ViewGroup parentView;
    private Activity mActivity;

    // Foldable layout
    private View listTouchInterceptor;
    private LinearLayout detailsLayout;
    private UnfoldableView unfoldableView;

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
    }

    private void initViewData() {
        listTouchInterceptor.setClickable(false);
        detailsLayout.setVisibility(View.INVISIBLE);

        Bitmap glance = BitmapFactory.decodeResource(mActivity.getResources(), R.drawable.unfold_glance);
        unfoldableView.setFoldShading(new GlanceFoldShading(glance));
    }

    private void initActions() {
        unfoldableView.setOnFoldingListener(new UnfoldableView.SimpleFoldingListener() {

            @Override
            public void onUnfolding(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(true);
                detailsLayout.setVisibility(View.VISIBLE);

//                // change layout view while unfolding
//                mFilter.setVisibility(View.INVISIBLE);
//                ivListToGrid.setVisibility(View.INVISIBLE);
//                tvTitle.setText(getString(R.string.title_activity_file_detail));
//                ivContentHamburger.setVisibility(View.GONE);
//                ivContentBack.setVisibility(View.VISIBLE);
//
//                // after folding view it always lost action when it is unfolded again
//                setBackButtonAction(unfoldableView);

            }

            @Override
            public void onUnfolded(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(false);

//                fabFilter.setVisibility(View.INVISIBLE);
//                rlCart.setVisibility(View.INVISIBLE);
//                tvTitle.setText(getString(R.string.txt_product_detail));
            }

            @Override
            public void onFoldingBack(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(true);
            }

            @Override
            public void onFoldedBack(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(false);
                detailsLayout.setVisibility(View.INVISIBLE);

//                // change layout view while unfolding
//                fabFilter.setVisibility(View.VISIBLE);
//                rlCart.setVisibility(View.VISIBLE);
//                tvTitle.setText(getString(R.string.txt_product));
            }
        });
    }

    public void openDetails(View detailLayout, View rowItemView, Order order) {

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
}