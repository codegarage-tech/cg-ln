package com.meembusoft.ln.viewholder;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatCheckBox;

import com.meembusoft.addtocart.AddToCartManager;
import com.meembusoft.addtocart.model.CartItem;
import com.meembusoft.ln.R;
import com.meembusoft.ln.activity.CartActivity;
import com.meembusoft.ln.adapter.CartListAdapter;
import com.meembusoft.recyclerview.viewholder.BaseViewHolder;
import com.squareup.picasso.Picasso;

import me.wangyuwei.shoppoing.ShoppingView;

import static com.meembusoft.addtocart.util.Constant.DB_KEY_ID;

public class CartViewHolder extends BaseViewHolder<CartItem> {

    private TextView tvProductName, tvProductUnitName, tvProductUnitPrice, tvProductSumPrice;
    private ImageView ivProductImage;
    private ShoppingView svAddToCart;
    private AppCompatCheckBox accbSelect;
    private LinearLayout llDelete, llFavorite;
    private String TAG = "CartViewHolder";

    public CartViewHolder(ViewGroup parent) {
        super(parent, R.layout.row_item_cart);

        ivProductImage = $(R.id.iv_product_image);
        tvProductName = $(R.id.tv_product_name);
        tvProductUnitName = $(R.id.tv_product_unit_name);
        tvProductUnitPrice = $(R.id.tv_product_unit_price);
        tvProductSumPrice = $(R.id.tv_product_sum_price);
        svAddToCart = $(R.id.sv_add_to_cart);
        accbSelect = $(R.id.cb_select);
        llDelete = $(R.id.ll_delete);
        llFavorite = $(R.id.ll_favorite);
    }

    @Override
    public void setData(final CartItem data) {
        tvProductName.setText(data.getName());
        Picasso.get().load(data.getImage()).into(ivProductImage);
        tvProductUnitName.setText(data.getSize());
        tvProductUnitPrice.setText("TK " + data.getSinglePrice());
        svAddToCart.setTextNum(data.getQuantity());
        accbSelect.setChecked(data.isSelected());
        updateProductSumPrice(data);

        initShoppingView(data);

        accbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (data.getQuantity() > 0) {
                        // Update cart item
                        data.setSelected(isChecked);
                        updateCartItemView(data);
                    } else {
                        accbSelect.setChecked(false);
                        Toast.makeText(getContext(), getContext().getString(R.string.txt_item_quantity_should_more_than_zero), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Update cart item
                    data.setSelected(isChecked);
                    updateCartItemView(data);
                }

                // Finally update select all status
                getOwnerRecyclerView().post(new Runnable() {
                    @Override
                    public void run() {
                        ((CartActivity) getContext()).updateAllCartItemsSelection();
                    }
                });
            }
        });

        llDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AddToCartManager.getInstance().isCartItemExist(CartItem.class, DB_KEY_ID, data.getId())) {
                    AddToCartManager.getInstance().deleteItem(CartItem.class, DB_KEY_ID, data.getId());
                }
                ((CartListAdapter) getOwnerAdapter()).remove(data);

                // Finish activity if there is no items
                if (((CartListAdapter) getOwnerAdapter()).getCount() == 0) {
                    ((CartActivity) getContext()).finish();
                }
            }
        });
    }

    private void initShoppingView(CartItem cartItem) {
        svAddToCart.setOnShoppingClickListener(new ShoppingView.ShoppingClickListener() {
            @Override
            public void onAddClick(int num) {
                Log.d(TAG, "@=> " + "add.......num=> " + num);
                // Update cart item
                cartItem.setQuantity(num);
                updateCartItemView(cartItem);
            }

            @Override
            public void onMinusClick(int num) {
                Log.d(TAG, "@=> " + "minus.......num=> " + num);
                // Update cart item
                cartItem.setQuantity(num);
                updateCartItemView(cartItem);
            }
        });
    }

    private void updateCartItemView(CartItem cartItem) {
        //Update data into database
        if (AddToCartManager.getInstance().isCartItemExist(CartItem.class, DB_KEY_ID, cartItem.getId())) {
            Log.d(TAG, "<<<onOrderNowClick>>>: " + "Update>>>: data is exist" + cartItem.toString());
            AddToCartManager.getInstance().addOrUpdateCart(cartItem);
        }

        // Update sum price
        updateProductSumPrice(cartItem);

        // if quantity is zero, then deselect the product
        if (cartItem.getQuantity() == 0 && cartItem.isSelected()) {
            accbSelect.setChecked(false);
        }
    }

    private void updateProductSumPrice(CartItem cartItem) {
        tvProductSumPrice.setText("TK " + (cartItem.getTotalPrice()));
    }
}