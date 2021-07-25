package com.meembusoft.ln.controller;

import android.animation.Animator;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.alexvasilkov.foldablelayout.UnfoldableView;
import com.alexvasilkov.foldablelayout.shading.GlanceFoldShading;
import com.github.florent37.expansionpanel.ExpansionHeader;
import com.github.florent37.expansionpanel.ExpansionLayout;
import com.meembusoft.addtocart.AddToCartManager;
import com.meembusoft.addtocart.model.CartItem;
import com.meembusoft.ln.R;
import com.meembusoft.ln.activity.CategoryActivity;
import com.meembusoft.ln.adapter.ImageSliderAdapter;
import com.meembusoft.ln.interfaces.OnCartResetListener;
import com.meembusoft.ln.model.Image;
import com.meembusoft.ln.model.Product;
import com.meembusoft.ln.model.Unit;
import com.meembusoft.ln.util.AppUtil;
import com.meembusoft.ln.util.DataUtil;
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

import me.wangyuwei.shoppoing.ShoppingView;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

import static com.meembusoft.addtocart.util.Constant.DB_KEY_ID;

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
    private ImageView ivProductFavorite;
    private TextView tvProductName, tvProductOriginalPrice, tvProductOfferPrice, tvProductVendor, tvProductNote, tvProductDescription, tvProviders, tvNote, tvDescription;
    private ShoppingView svAddToCart;
    private MaterialRatingBar mrbRating;

    // Unit
    private FlowLayout flowLayoutUnit;
    private FlowLayoutManager flowLayoutManagerUnit;
    private ExpansionHeader expansionHeader;
    private ExpansionLayout expansionLayout;

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
        mrbRating = parentView.findViewById(R.id.mrb_rating);
        svAddToCart = parentView.findViewById(R.id.sv_add_to_cart);
        ivProductFavorite = parentView.findViewById(R.id.iv_product_favorite);
        tvProductName = parentView.findViewById(R.id.tv_product_name);
        tvProductOriginalPrice = parentView.findViewById(R.id.tv_product_original_price);
        tvProductOfferPrice = parentView.findViewById(R.id.tv_product_offer_price);
        tvProductVendor = parentView.findViewById(R.id.tv_product_vendor);
        tvProductNote = parentView.findViewById(R.id.tv_product_note);
        tvProductDescription = parentView.findViewById(R.id.tv_product_description);
        tvProviders = parentView.findViewById(R.id.tv_providers);
        tvNote = parentView.findViewById(R.id.tv_note);
        tvDescription = parentView.findViewById(R.id.tv_description);
        // Unit
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

    public boolean isDetailOpen() {
        return unfoldableView.isUnfolded() || unfoldableView.isUnfolding();
    }

    public void closeDetail() {
        unfoldableView.foldBack();
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
            AppUtil.applyViewTint(ivProductFavorite, (product.isFavorite() ? R.color.colorPink : R.color.subtitleTextColor));
            mrbRating.setRating(product.getRating());
            mrbRating.setEnabled(false);
            expansionHeader.setEnabled(false);
            tvProductName.setText(product.getName());
            if (!TextUtils.isEmpty(product.getVendor())) {
                tvProviders.setVisibility(View.VISIBLE);
                tvProductVendor.setVisibility(View.VISIBLE);
                tvProductVendor.setText(product.getVendor());
            } else {
                tvProviders.setVisibility(View.GONE);
                tvProductVendor.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(product.getNote())) {
                tvNote.setVisibility(View.VISIBLE);
                tvProductNote.setVisibility(View.VISIBLE);
                tvProductNote.setText(product.getNote());
            } else {
                tvNote.setVisibility(View.GONE);
                tvProductNote.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(product.getDescription())) {
                tvDescription.setVisibility(View.VISIBLE);
                tvProductDescription.setVisibility(View.VISIBLE);
                tvProductDescription.setText(product.getDescription());
            } else {
                tvDescription.setVisibility(View.GONE);
                tvProductDescription.setVisibility(View.GONE);
            }

            initUnit(product);

            initShoppingView(product);
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
                            product.setSelectedUnit(DataUtil.getUnit(tempSelectedSize, units));
                            updateUnitSelection(product);
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

    private void updateUnitSelection(Product product) {
        if (product != null) {
            Unit unit = product.getSelectedUnit();
            if (unit != null) {
                if (unit.getOfferPrice() > 0) {
                    tvProductOriginalPrice.setText(mActivity.getString(R.string.txt_amount_with_taka, unit.getOriginalPrice()));
                    AppUtil.applyStrike(tvProductOriginalPrice, true);
                    tvProductOfferPrice.setText(mActivity.getString(R.string.txt_amount_with_taka, unit.getOfferPrice()));
                    tvProductOfferPrice.setVisibility(View.VISIBLE);
                } else {
                    tvProductOriginalPrice.setText(mActivity.getString(R.string.txt_amount_with_taka, unit.getOriginalPrice()));
                    AppUtil.applyStrike(tvProductOriginalPrice, false);
                    tvProductOfferPrice.setText(mActivity.getString(R.string.txt_amount_with_taka, unit.getOfferPrice()));
                    tvProductOfferPrice.setVisibility(View.GONE);
                }

                // Show previous selected quantity
                int quantity = product.getSelectedQuantity(unit.getName());
                if (svAddToCart.getText() > 0 || quantity > 0) {
                    svAddToCart.setTextNum(quantity);
                }
            }
        }
    }

    private void initShoppingView(Product data) {
        if (data != null) {
            svAddToCart.setOnShoppingClickListener(new ShoppingView.ShoppingClickListener() {
                @Override
                public void onAddClick(int num) {
                    Log.d(TAG, "@=> " + "add.......num=> " + num);
                    // Prepare cart item
                    CartItem cartItem = DataUtil.prepareCartItem(data, data.getSelectedUnit(), num);

                    // Add to cart
                    addToCart(data, cartItem);
                }

                @Override
                public void onMinusClick(int num) {
                    Log.d(TAG, "@=> " + "minus.......num=> " + num);
                    // Prepare cart item
                    CartItem cartItem = DataUtil.prepareCartItem(data, data.getSelectedUnit(), num);

                    // Add to cart
                    addToCart(data, cartItem);
                }
            });
        }
    }

    private void addToCart(Product product, CartItem cartItem) {
        Log.d(TAG, "<<<onOrderNowClick>>>: " + "count: " + cartItem.getQuantity());

        // Store and update cart information
        product.setSelectedQuantity(product.getSelectedUnit().getName(), cartItem.getQuantity());

        if (cartItem.getQuantity() == 0) {
            if (AddToCartManager.getInstance().isCartItemExist(CartItem.class, DB_KEY_ID, cartItem.getId())) {
                Log.d(TAG, "<<<onOrderNowClick>>>: " + "Delete>>>: data is exist" + cartItem.toString());
                //Delete the CartItem from database
                AddToCartManager.getInstance().deleteItem(CartItem.class, DB_KEY_ID, cartItem.getId());

                //Reset counter view into toolbar
                DataUtil.resetCartCounterView(((CategoryActivity) mActivity).getCartCounter(), new OnCartResetListener() {
                    @Override
                    public void onOrderCompleted(boolean isOrderCompleted) {

                    }
                });
            }
        } else if (cartItem.getQuantity() == 1) {
            if (AddToCartManager.getInstance().isCartItemExist(CartItem.class, DB_KEY_ID, cartItem.getId())) {
                Log.d(TAG, "<<<onOrderNowClick>>>: " + "Update>>>: " + cartItem.toString());
                //Update data into database
                AddToCartManager.getInstance().addOrUpdateCart(cartItem);
            } else {
                //Add item into database
                Log.d(TAG, "<<<onOrderNowClick>>>: " + "<<Add>>>: " + cartItem.getQuantity());
                AddToCartManager.getInstance().addOrUpdateCart(cartItem);

                //make fly animation for adding item
                AppUtil.makeFlyAnimation(((CategoryActivity) mActivity), svAddToCart, svAddToCart.getAddIcon(), ((CategoryActivity) mActivity).getCartIcon(), 1000, new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        DataUtil.resetCartCounterView(((CategoryActivity) mActivity).getCartCounter(), new OnCartResetListener() {
                            @Override
                            public void onOrderCompleted(boolean isOrderCompleted) {

                            }
                        });
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }
        } else {
            //Update data into database
            Log.d(TAG, "<<<onOrderNowClick>>>: " + "Update>>>: " + cartItem.getQuantity());
            Log.d(TAG, "<<<onOrderNowClick>>>: " + "Update>>>: " + cartItem.toString());

            AddToCartManager.getInstance().addOrUpdateCart(cartItem);
        }
    }
}