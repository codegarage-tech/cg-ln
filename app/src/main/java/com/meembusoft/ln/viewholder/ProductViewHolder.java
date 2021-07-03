package com.meembusoft.ln.viewholder;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.meembusoft.ln.R;
import com.meembusoft.ln.model.colormatchtab.Product;
import com.meembusoft.recyclerview.viewholder.BaseViewHolder;
import com.squareup.picasso.Picasso;

public class ProductViewHolder extends BaseViewHolder<Product> {

    private TextView tvProductName, tvProductVendor, tvProductAvailabilityTime, tvProductOriginalPrice, tvProductOfferPrice;
    private ImageView ivProductImage, ivProductFavorite, ivProductCart;

    public ProductViewHolder(ViewGroup parent) {
        super(parent, R.layout.row_item_product);

        ivProductImage = $(R.id.iv_product_image);
        tvProductName = $(R.id.tv_product_name);
        tvProductVendor = $(R.id.tv_product_vendor);
        tvProductAvailabilityTime = $(R.id.tv_product_availability);
        tvProductOriginalPrice = $(R.id.tv_product_original_price);
        tvProductOfferPrice = $(R.id.tv_product_offer_price);
        ivProductFavorite = $(R.id.iv_product_favorite);
        ivProductCart = $(R.id.iv_product_cart);
    }

    @Override
    public void setData(final Product data) {
        tvProductName.setText(data.getName());
        tvProductVendor.setText(data.getVendor());
        Picasso.get().load(data.getImage()).into(ivProductImage);
    }
}