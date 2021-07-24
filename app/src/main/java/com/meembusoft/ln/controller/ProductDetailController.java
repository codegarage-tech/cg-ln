package com.meembusoft.ln.controller;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.alexvasilkov.foldablelayout.UnfoldableView;
import com.alexvasilkov.foldablelayout.shading.GlanceFoldShading;
import com.meembusoft.ln.R;
import com.meembusoft.ln.activity.CategoryActivity;
import com.meembusoft.ln.adapter.ImageSliderAdapter;
import com.meembusoft.ln.model.Image;
import com.meembusoft.ln.model.Product;
import com.meembusoft.ln.model.ProductDetail;
import com.meembusoft.ln.util.DataUtil;
import com.meembusoft.ln.util.Logger;
import com.smarteist.autoimageslider.CircularSliderHandle;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;

public class ProductDetailController {

    private ViewGroup parentView;
    private Activity mActivity;

    // Foldable layout
    private View listTouchInterceptor;
    private LinearLayout detailsLayout;
    private UnfoldableView unfoldableView;

    // View items
    private ProductDetail mProductDetail;
    private String TAG = "ProductDetailController";

    // Image slider
    private SliderView sliderViewProduct;
    private ImageSliderAdapter imageSliderAdapterProduct;

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
        sliderViewProduct = parentView.findViewById(R.id.sliderview_product);
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
        Logger.d(TAG, "product: " + product.toString());
        mProductDetail = DataUtil.getProductDetail(mActivity, product.getId());

        // Initialize product detail views
        initImageSlider(mProductDetail.getImages());

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

    private void initImageSlider(List<Image> productImages) {
        if (productImages != null && productImages.size() > 0) {
            imageSliderAdapterProduct = new ImageSliderAdapter(mActivity);
            imageSliderAdapterProduct.setData(productImages);

            sliderViewProduct.setSliderAdapter(imageSliderAdapterProduct);
            sliderViewProduct.setIndicatorAnimation(IndicatorAnimations.getRandomIndicatorAnimation()); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
            sliderViewProduct.setSliderTransformAnimation(SliderAnimations.getRandomSliderTransformAnimation());
            sliderViewProduct.setIndicatorGravity(Gravity.BOTTOM | Gravity.RIGHT);
            sliderViewProduct.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
            sliderViewProduct.setIndicatorSelectedColor(ContextCompat.getColor(mActivity, R.color.colorPrimaryDark));
            sliderViewProduct.setIndicatorUnselectedColor(Color.GRAY);
            sliderViewProduct.setCurrentPageListener(new CircularSliderHandle.CurrentPageListener() {
                @Override
                public void onCurrentPageChanged(int currentPosition) {
//                    Image mImage = productImages.get(currentPosition);
                }
            });
            sliderViewProduct.setOnIndicatorClickListener(new DrawController.ClickListener() {
                @Override
                public void onIndicatorClicked(int position) {
                    sliderViewProduct.setCurrentPagePosition(position);
//                    Image mImage = productImages.get(position);
                }
            });
            sliderViewProduct.startAutoCycle();
//            // For the very first time show first item as selected
//            sliderViewProduct.setCurrentPagePosition(0);
//            Image mImage = productImages.get(0);
        }
    }
}