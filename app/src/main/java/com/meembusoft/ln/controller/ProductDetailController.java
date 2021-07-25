package com.meembusoft.ln.controller;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.alexvasilkov.foldablelayout.UnfoldableView;
import com.alexvasilkov.foldablelayout.shading.GlanceFoldShading;
import com.github.florent37.expansionpanel.ExpansionHeader;
import com.github.florent37.expansionpanel.ExpansionLayout;
import com.meembusoft.ln.R;
import com.meembusoft.ln.activity.CategoryActivity;
import com.meembusoft.ln.adapter.ImageSliderAdapter;
import com.meembusoft.ln.model.Image;
import com.meembusoft.ln.model.Product;
import com.meembusoft.ln.model.Unit;
import com.meembusoft.ln.util.Logger;
import com.nex3z.flowlayout.FlowLayout;
import com.nex3z.flowlayout.FlowLayoutManager;
import com.smarteist.autoimageslider.CircularSliderHandle;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailController {

    private ViewGroup parentView;
    private Activity mActivity;

    // Foldable layout
    private View listTouchInterceptor;
    private LinearLayout detailsLayout;
    private UnfoldableView unfoldableView;

    // View items
    private Product mProduct;
    private String TAG = "ProductDetailController";
    private TextView tvProductName;

    // Unit
    private FlowLayout flowLayoutUnit;
    private FlowLayoutManager flowLayoutManagerUnit;
    private ExpansionHeader expansionHeader;
    private ExpansionLayout expansionLayout;
    private TextView tvProductOriginalPrice, tvProductOfferPrice;

    // Image slider
    private SliderView sliderViewProduct;
    private ImageSliderAdapter imageSliderAdapterProduct;

    public ProductDetailController(Activity activity, ViewGroup view) {
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
        sliderViewProduct = parentView.findViewById(R.id.sliderview_product);
        tvProductName = parentView.findViewById(R.id.tv_product_name);
        // Unit
        tvProductOriginalPrice = parentView.findViewById(R.id.tv_product_original_price);
        tvProductOfferPrice = parentView.findViewById(R.id.tv_product_offer_price);
        flowLayoutUnit = parentView.findViewById(R.id.fl_size);
        expansionHeader = parentView.findViewById(R.id.eh_unit);
        expansionLayout = parentView.findViewById(R.id.expansionLayout);
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
        mProduct = product;

        // Initialize product detail views
        initImageSlider(mProduct.getImages());
        initDetailInfo(mProduct);

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

    private void initDetailInfo(Product product) {
        if (product != null) {
            tvProductName.setText(product.getName());
            initUnit(product);
        }
    }

    /***************************
     * Methods for flow layout *
     ***************************/
    public void initUnit(Product product) {
        if (product != null) {
            List<Unit> units = product.getUnits();
            if (units != null && !units.isEmpty()) {
                List<String> keys = new ArrayList<>();
                for (int i = 0; i < units.size(); i++) {
                    keys.add(units.get(i).getName());
                }
                if (!keys.isEmpty()) {
                    // Remove all previous keys
                    if (flowLayoutManagerUnit != null) {
                        flowLayoutManagerUnit.removeAllKeys();
                    }

                    //Set flow layout with connection key
                    flowLayoutManagerUnit = new FlowLayoutManager.FlowViewBuilder(mActivity, flowLayoutUnit, keys, new FlowLayoutManager.onFlowViewClick() {
                        @Override
                        public void flowViewClick(TextView updatedTextView) {
                            List<TextView> selectedSizeKeys = flowLayoutManagerUnit.getSelectedFlowViews();
                            String tempSelectedSize = (selectedSizeKeys.size() > 0) ? selectedSizeKeys.get(0).getText().toString() : "";
                            Log.d(TAG, "tempSelectedSize: " + tempSelectedSize);

                            // Store and update selected unit
//                            product.setSelectedUnit(DataUtil.getUnit(tempSelectedSize, units));
//                            updateUnitSelection(product);
//
//                            // Close expansion layout
//                            expansionLayout.collapse(true);
//
//                            //Save temp selected size
                        }
                    })
                            .setSingleChoice(true)
                            .setStickyModeForSingleChoice(true)
                            .build();

                    //Set last temp selected size key
                    if (product.getSelectedUnit() != null) {
                        flowLayoutManagerUnit.clickFlowView(product.getSelectedUnit().getName());
                    } else {
                        flowLayoutManagerUnit.clickFlowView(keys.get(0));
                    }
                }
            }
        }
    }
}