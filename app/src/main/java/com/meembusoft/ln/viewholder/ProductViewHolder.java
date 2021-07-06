package com.meembusoft.ln.viewholder;

import android.animation.Animator;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.expansionpanel.ExpansionHeader;
import com.github.florent37.expansionpanel.ExpansionLayout;
import com.meembusoft.addtocart.AddToCartManager;
import com.meembusoft.addtocart.model.CartItem;
import com.meembusoft.ln.R;
import com.meembusoft.ln.activity.CategoryActivity;
import com.meembusoft.ln.model.colormatchtab.Product;
import com.meembusoft.ln.model.colormatchtab.Unit;
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

import static com.meembusoft.addtocart.util.Constant.DB_KEY_ID;

public class ProductViewHolder extends BaseViewHolder<Product> {

    private TextView tvProductName, tvProductNote, tvProductOriginalPrice, tvProductOfferPrice, tvProductSize;
    private ImageView ivProductImage, ivProductFavorite;
    private ShoppingView svAddToCart;

    // Size
    private FlowLayout flowLayoutUnit;
    private FlowLayoutManager flowLayoutManagerUnit;
    private ExpansionHeader expansionHeader;
    private ExpansionLayout expansionLayout;
    private String TAG = "ProductViewHolder";
    private Unit mSelectedUnit;

    public ProductViewHolder(ViewGroup parent) {
        super(parent, R.layout.row_item_product_linear_right);

        ivProductImage = $(R.id.iv_product_image);
        ivProductFavorite = $(R.id.iv_product_favorite);
        tvProductName = $(R.id.tv_product_name);
        tvProductSize = $(R.id.tv_product_size);
        tvProductNote = $(R.id.tv_product_availability_time);
        tvProductOriginalPrice = $(R.id.tv_product_original_price);
        tvProductOfferPrice = $(R.id.tv_product_offer_price);
        flowLayoutUnit = $(R.id.fl_size);
        svAddToCart = $(R.id.sv_add_to_cart);
        expansionHeader = $(R.id.eh_unit);
        expansionLayout = $(R.id.expansionLayout);
    }

    @Override
    public void setData(final Product data) {
        tvProductName.setText(data.getName());
        AppUtil.applyViewTint(ivProductFavorite, (data.isFavorite() ? R.color.colorBlueDark : R.color.colorBlue));
        if (data.getImages() != null && !data.getImages().isEmpty()) {
            Picasso.get().load(data.getImages().get(RandomManager.getRandom(0, data.getImages().size())).getUrl()).into(ivProductImage);
        }
        initUnit(data.getUnits());

        svAddToCart.setOnShoppingClickListener(new ShoppingView.ShoppingClickListener() {
            @Override
            public void onAddClick(int num) {
                Log.d(TAG, "@=> " + "add.......num=> " + num);
                // Prepare cart item
                CartItem cartItem = prepareCartItem(data, mSelectedUnit, num);

                // Add to cart
                addToCart(cartItem, svAddToCart);
            }

            @Override
            public void onMinusClick(int num) {
                Log.d(TAG, "@=> " + "minus.......num=> " + num);
                // Prepare cart item
                CartItem cartItem = prepareCartItem(data, mSelectedUnit, num);

                // Add to cart
                addToCart(cartItem, svAddToCart);
            }
        });
    }

    private CartItem prepareCartItem(Product data, Unit selectedUnit, int quantity) {
        CartItem cartItem = new CartItem();
        cartItem.setId(getCartId(data, selectedUnit));
        cartItem.setName(data.getName());
        cartItem.setSize(selectedUnit.getName());
        cartItem.setPrice(((double) selectedUnit.getOriginalPrice()));
        cartItem.setQuantity(quantity);
        cartItem.setImage(data.getImages().get(0).getUrl());
        return cartItem;
    }

    private String getCartId(Product product, Unit unit) {
        return product.getId() + ">>" + unit.getName();
    }

    private void addToCart(CartItem cartItem, ShoppingView shoppingView) {
        Log.d(TAG, "<<<onOrderNowClick>>>: " + "count: " + cartItem.getQuantity());

        if (cartItem.getQuantity() == 0) {
            if (AddToCartManager.getInstance().isCartItemExist(CartItem.class, DB_KEY_ID, cartItem.getId())) {
                Log.d(TAG, "<<<onOrderNowClick>>>: " + "Delete>>>: data is exist" + cartItem.toString());
                //Delete the CartItem from database
                AddToCartManager.getInstance().deleteItem(CartItem.class, DB_KEY_ID, cartItem.getId());

                //Reset counter view into toolbar
                ((CategoryActivity) getContext()).resetCartCounter();
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
                AppUtil.makeFlyAnimation(((CategoryActivity) getContext()), shoppingView, shoppingView.getAddIcon(), ((CategoryActivity) getContext()).getCartIcon(), 1000, new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ((CategoryActivity) getContext()).resetCartCounter();
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

    /***************************
     * Methods for flow layout *
     ***************************/
    public void initUnit(List<Unit> units) {
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
                flowLayoutManagerUnit = new FlowLayoutManager.FlowViewBuilder(getContext(), flowLayoutUnit, keys, new FlowLayoutManager.onFlowViewClick() {
                    @Override
                    public void flowViewClick(TextView updatedTextView) {
                        List<TextView> selectedSizeKeys = flowLayoutManagerUnit.getSelectedFlowViews();
                        String tempSelectedSize = (selectedSizeKeys.size() > 0) ? selectedSizeKeys.get(0).getText().toString() : "";
                        Log.d(TAG, "tempSelectedSize: " + tempSelectedSize);

                        mSelectedUnit = DataUtil.getUnit(tempSelectedSize, units);
                        updateUnitSelection(mSelectedUnit);

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
                flowLayoutManagerUnit.clickFlowView(keys.get(0));
            }
        }
    }

    private void updateUnitSelection(Unit unit) {
        if (unit != null) {
            tvProductSize.setText(unit.getName());
            tvProductOriginalPrice.setText(unit.getOriginalPrice() + " TK");
            tvProductOfferPrice.setText(unit.getOfferPrice() + " TK");
//            if (!isFirstTime) {
//                svAddToCart.setTextNum(0);
//            }
        }
    }
}