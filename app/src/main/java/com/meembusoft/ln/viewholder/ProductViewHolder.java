package com.meembusoft.ln.viewholder;

import android.animation.Animator;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.expansionpanel.ExpansionHeader;
import com.github.florent37.expansionpanel.ExpansionLayout;
import com.meembusoft.ln.R;
import com.meembusoft.ln.activity.CategoryActivity;
import com.meembusoft.ln.model.colormatchtab.Product;
import com.meembusoft.ln.model.colormatchtab.Size;
import com.meembusoft.ln.util.AppUtil;
import com.meembusoft.ln.util.DataUtil;
import com.meembusoft.ln.util.RandomManager;
import com.meembusoft.recyclerview.viewholder.BaseViewHolder;
import com.nex3z.flowlayout.FlowLayout;
import com.nex3z.flowlayout.FlowLayoutManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import me.wangyuwei.shoppoing.ShoppingView;

public class ProductViewHolder extends BaseViewHolder<Product> {

    private TextView tvProductName, tvProductNote, tvProductOriginalPrice, tvProductOfferPrice, tvProductSize;
    private ImageView ivProductImage, ivProductFavorite;
    private ShoppingView svAddToCart;

    // Size
    private FlowLayout flowLayoutSize;
    private FlowLayoutManager flowLayoutManagerSize;
    private ExpansionHeader expansionHeader;
    private ExpansionLayout expansionLayout;
    private String TAG = "ProductViewHolder";

    public ProductViewHolder(ViewGroup parent) {
        super(parent, R.layout.row_item_product_linear_right);

        ivProductImage = $(R.id.iv_product_image);
        ivProductFavorite = $(R.id.iv_product_favorite);
        tvProductName = $(R.id.tv_product_name);
        tvProductSize = $(R.id.tv_product_size);
        tvProductNote = $(R.id.tv_product_availability_time);
        tvProductOriginalPrice = $(R.id.tv_product_original_price);
        tvProductOfferPrice = $(R.id.tv_product_offer_price);
        flowLayoutSize = $(R.id.fl_size);
        svAddToCart = $(R.id.sv_add_to_cart);
        expansionHeader = $(R.id.eh_unit);
        expansionLayout = $(R.id.expansionLayout);
    }

    @Override
    public void setData(final Product data) {
        tvProductName.setText(data.getName());
        AppUtil.applyViewTint(ivProductFavorite, (data.isFavorite() ? R.color.colorBlueDark : R.color.colorBlue));
        Picasso.get().load(data.getImage()).into(ivProductImage);
        initSize(data.getSize());

        svAddToCart.setOnShoppingClickListener(new ShoppingView.ShoppingClickListener() {
            @Override
            public void onAddClick(int num) {
                Log.d(TAG, "@=> " + "add.......num=> " + num);
//                if (cartItem != null ) {
//                    cartItem.setDiscountPercentage(5);
//                    cartItem.setQuantity(num);
//                    onOrderNowClick(cartItem, svAddToCart);
//                }

                //make fly animation for adding item
                AppUtil.makeFlyAnimation(((CategoryActivity) getContext()), svAddToCart, svAddToCart.getAddIcon(), ((CategoryActivity) getContext()).getCart(), 1000, new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
//                        resetCounterView();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }

            @Override
            public void onMinusClick(int num) {
                Log.d(TAG, "@=> " + "minus.......num=> " + num);
//                if (cartItem != null ) {
//                    cartItem.setDiscountPercentage(5);
//                    cartItem.setQuantity(num);
//                    onOrderNowClick(cartItem, svAddToCart);
//                }
            }
        });
    }

    /***************************
     * Methods for flow layout *
     ***************************/
    public void initSize(List<Size> sizes) {
        if (sizes != null && !sizes.isEmpty()) {
            List<String> keys = new ArrayList<>();
            for (int i = 0; i < sizes.size(); i++) {
                keys.add(sizes.get(i).getName());
            }
            if (!keys.isEmpty()) {
                // Remove all previous keys
                if (flowLayoutManagerSize != null) {
                    flowLayoutManagerSize.removeAllKeys();
                }

                //Set flow layout with connection key
                flowLayoutManagerSize = new FlowLayoutManager.FlowViewBuilder(getContext(), flowLayoutSize, keys, new FlowLayoutManager.onFlowViewClick() {
                    @Override
                    public void flowViewClick(TextView updatedTextView) {
                        List<TextView> selectedSizeKeys = flowLayoutManagerSize.getSelectedFlowViews();
                        String tempSelectedSize = (selectedSizeKeys.size() > 0) ? selectedSizeKeys.get(0).getText().toString() : "";
                        Log.d(TAG, "tempSelectedSize: " + tempSelectedSize);

                        updateSizeSelection(DataUtil.getSize(tempSelectedSize, sizes));

                        // Close expansion layout
                        expansionLayout.collapse(true);

                        //Save temp selected size
                    }
                })
                        .setSingleChoice(true)
                        .build();

                //Set last temp selected size key
//            String lastTempSelectedRoom = SessionUtil.getTempSelectedConnectionType(getActivity());
//            if (!AllSettingsManager.isNullOrEmpty(lastTempSelectedRoom)) {
//                flowLayoutManagerSize.clickFlowView(lastTempSelectedRoom);
//            }
                flowLayoutManagerSize.clickFlowView(keys.get(0));
            }
        }
    }

    private void updateSizeSelection(Size size) {
        if (size != null) {
            tvProductSize.setText(size.getName());
            tvProductOriginalPrice.setText(size.getPrice().getOriginal() + " TK");
            tvProductOfferPrice.setText(size.getPrice().getOffer()+ " TK");
//            if (!isFirstTime) {
//                svAddToCart.setTextNum(0);
//            }
        }
    }
}