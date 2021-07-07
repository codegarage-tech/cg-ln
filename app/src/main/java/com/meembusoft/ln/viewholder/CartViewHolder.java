package com.meembusoft.ln.viewholder;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.meembusoft.addtocart.AddToCartManager;
import com.meembusoft.addtocart.model.CartItem;
import com.meembusoft.ln.R;
import com.meembusoft.recyclerview.viewholder.BaseViewHolder;
import com.squareup.picasso.Picasso;

import me.wangyuwei.shoppoing.ShoppingView;

import static com.meembusoft.addtocart.util.Constant.DB_KEY_ID;

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
        updateProductSumPrice(data);

        initShoppingView(data);
    }

    private void updateProductSumPrice(CartItem cartItem) {
        tvProductSumPrice.setText("TK " + (cartItem.getPrice() * cartItem.getQuantity()));
    }

    private void initShoppingView(CartItem cartItem) {
        svAddToCart.setOnShoppingClickListener(new ShoppingView.ShoppingClickListener() {
            @Override
            public void onAddClick(int num) {
                Log.d(TAG, "@=> " + "add.......num=> " + num);
                // Prepare cart item
                updateCartItemView(cartItem, num);
            }

            @Override
            public void onMinusClick(int num) {
                Log.d(TAG, "@=> " + "minus.......num=> " + num);
                // Prepare cart item
                updateCartItemView(cartItem, num);
            }
        });
    }

    private void updateCartItemView(CartItem cartItem, int quantity) {
        //Update data into database
        if (AddToCartManager.getInstance().isCartItemExist(CartItem.class, DB_KEY_ID, cartItem.getId())) {
            Log.d(TAG, "<<<onOrderNowClick>>>: " + "Update>>>: data is exist" + cartItem.toString());
            cartItem.setQuantity(quantity);
            AddToCartManager.getInstance().addOrUpdateCart(cartItem);
        }

        // Update sum price
        updateProductSumPrice(cartItem);
    }
}