package com.meembusoft.ln.viewholder;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.meembusoft.addtocart.model.CartItem;
import com.meembusoft.ln.R;
import com.meembusoft.recyclerview.viewholder.BaseViewHolder;
import com.squareup.picasso.Picasso;

import me.wangyuwei.shoppoing.ShoppingView;

public class CartViewHolder extends BaseViewHolder<CartItem> {

    private TextView tvProductName, tvProductUnitName, tvProductUnitPrice, tvProductSumPrice;
    private ImageView ivProductImage;
    private ShoppingView svAddToCart;
    private String TAG = "CartViewHolder";

    public CartViewHolder(ViewGroup parent) {
        super(parent, R.layout.row_item_cart);

        ivProductImage = $(R.id.iv_product_image);
        tvProductName = $(R.id.tv_product_name);
        tvProductUnitName = $(R.id.tv_product_unit_name);
        tvProductUnitPrice = $(R.id.tv_product_unit_price);
        tvProductSumPrice = $(R.id.tv_product_sum_price);
        svAddToCart = $(R.id.sv_add_to_cart);
    }

    @Override
    public void setData(final CartItem data) {
        tvProductName.setText(data.getName());
        Picasso.get().load(data.getImage()).into(ivProductImage);
        tvProductUnitName.setText(data.getSize());
        tvProductUnitPrice.setText("TK " + data.getPrice());
        svAddToCart.setTextNum(data.getQuantity());
        tvProductSumPrice.setText("TK " + (data.getPrice() * data.getQuantity()));

        //        initUnit(data);
//
//        initShoppingView(data);
    }
//
//    private void initShoppingView(Product data) {
//        svAddToCart.setOnShoppingClickListener(new ShoppingView.ShoppingClickListener() {
//            @Override
//            public void onAddClick(int num) {
//                Log.d(TAG, "@=> " + "add.......num=> " + num);
//                // Prepare cart item
//                CartItem cartItem = DataUtil.prepareCartItem(data, data.getSelectedUnit(), num);
//
//                // Add to cart
//                addToCart(data, cartItem);
//            }
//
//            @Override
//            public void onMinusClick(int num) {
//                Log.d(TAG, "@=> " + "minus.......num=> " + num);
//                // Prepare cart item
//                CartItem cartItem = DataUtil.prepareCartItem(data, data.getSelectedUnit(), num);
//
//                // Add to cart
//                addToCart(data, cartItem);
//            }
//        });
//    }
//
//    private void addToCart(Product product, CartItem cartItem) {
//        Log.d(TAG, "<<<onOrderNowClick>>>: " + "count: " + cartItem.getQuantity());
//
//        // Store and update cart information
//        product.setSelectedQuantity(product.getSelectedUnit().getName(), cartItem.getQuantity());
//
//        if (cartItem.getQuantity() == 0) {
//            if (AddToCartManager.getInstance().isCartItemExist(CartItem.class, DB_KEY_ID, cartItem.getId())) {
//                Log.d(TAG, "<<<onOrderNowClick>>>: " + "Delete>>>: data is exist" + cartItem.toString());
//                //Delete the CartItem from database
//                AddToCartManager.getInstance().deleteItem(CartItem.class, DB_KEY_ID, cartItem.getId());
//
//                //Reset counter view into toolbar
//                DataUtil.resetCartCounterView(((CategoryActivity) getContext()).getCartCounter());
//            }
//        } else if (cartItem.getQuantity() == 1) {
//            if (AddToCartManager.getInstance().isCartItemExist(CartItem.class, DB_KEY_ID, cartItem.getId())) {
//                Log.d(TAG, "<<<onOrderNowClick>>>: " + "Update>>>: " + cartItem.toString());
//                //Update data into database
//                AddToCartManager.getInstance().addOrUpdateCart(cartItem);
//            } else {
//                //Add item into database
//                Log.d(TAG, "<<<onOrderNowClick>>>: " + "<<Add>>>: " + cartItem.getQuantity());
//                AddToCartManager.getInstance().addOrUpdateCart(cartItem);
//
//                //make fly animation for adding item
//                AppUtil.makeFlyAnimation(((CategoryActivity) getContext()), svAddToCart, svAddToCart.getAddIcon(), ((CategoryActivity) getContext()).getCartIcon(), 1000, new Animator.AnimatorListener() {
//                    @Override
//                    public void onAnimationStart(Animator animation) {
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        DataUtil.resetCartCounterView(((CategoryActivity) getContext()).getCartCounter());
//                    }
//
//                    @Override
//                    public void onAnimationCancel(Animator animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animator animation) {
//
//                    }
//                });
//            }
//        } else {
//            //Update data into database
//            Log.d(TAG, "<<<onOrderNowClick>>>: " + "Update>>>: " + cartItem.getQuantity());
//            Log.d(TAG, "<<<onOrderNowClick>>>: " + "Update>>>: " + cartItem.toString());
//
//            AddToCartManager.getInstance().addOrUpdateCart(cartItem);
//        }
//    }
//
//    /***************************
//     * Methods for flow layout *
//     ***************************/
//    public void initUnit(Product product) {
//        List<Unit> units = product.getUnits();
//        if (units != null && !units.isEmpty()) {
//            List<String> keys = new ArrayList<>();
//            for (int i = 0; i < units.size(); i++) {
//                keys.add(units.get(i).getName());
//            }
//            if (!keys.isEmpty()) {
//                // Remove all previous keys
//                if (flowLayoutManagerUnit != null) {
//                    flowLayoutManagerUnit.removeAllKeys();
//                }
//
//                //Set flow layout with connection key
//                flowLayoutManagerUnit = new FlowLayoutManager.FlowViewBuilder(getContext(), flowLayoutUnit, keys, new FlowLayoutManager.onFlowViewClick() {
//                    @Override
//                    public void flowViewClick(TextView updatedTextView) {
//                        List<TextView> selectedSizeKeys = flowLayoutManagerUnit.getSelectedFlowViews();
//                        String tempSelectedSize = (selectedSizeKeys.size() > 0) ? selectedSizeKeys.get(0).getText().toString() : "";
//                        Log.d(TAG, "tempSelectedSize: " + tempSelectedSize);
//
//                        // Store and update selected unit
//                        product.setSelectedUnit(DataUtil.getUnit(tempSelectedSize, units));
//                        updateUnitSelection(product);
//
//                        // Close expansion layout
//                        expansionLayout.collapse(true);
//
//                        //Save temp selected size
//                    }
//                })
//                        .setSingleChoice(true)
//                        .build();
//
//                //Set last temp selected size key
//                if (product.getSelectedUnit() != null) {
//                    flowLayoutManagerUnit.clickFlowView(product.getSelectedUnit().getName());
//                } else {
//                    flowLayoutManagerUnit.clickFlowView(keys.get(0));
//                }
//            }
//        }
//    }
//
//    private void updateUnitSelection(Product product) {
//        if (product != null) {
//            Unit unit = product.getSelectedUnit();
//            if (unit != null) {
//                tvProductSize.setText(unit.getName());
//                tvProductOriginalPrice.setText(unit.getOriginalPrice() + " TK");
//                tvProductOfferPrice.setText(unit.getOfferPrice() + " TK");
//
//                // Show previous selected quantity
//                int quantity = product.getSelectedQuantity(unit.getName());
//                if (svAddToCart.getText() > 0 || quantity > 0) {
//                    svAddToCart.setTextNum(quantity);
//                }
//            }
//        }
//    }
}