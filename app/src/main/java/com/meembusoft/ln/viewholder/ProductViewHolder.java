package com.meembusoft.ln.viewholder;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.meembusoft.ln.R;
import com.meembusoft.ln.model.colormatchtab.Product;
import com.meembusoft.recyclerview.viewholder.BaseViewHolder;
import com.squareup.picasso.Picasso;

public class ProductViewHolder extends BaseViewHolder<Product> {

    private TextView tvProductName, tvProductAvailabilityTime, tvProductOriginalPrice, tvProductOfferPrice;
    private ImageView ivProductImage, ivProductFavorite;

    public ProductViewHolder(ViewGroup parent) {
        super(parent, R.layout.row_item_product_new);

        ivProductImage = $(R.id.iv_product_image);
        ivProductFavorite = $(R.id.iv_product_favorite);
        tvProductName = $(R.id.tv_product_name);
        tvProductAvailabilityTime = $(R.id.tv_product_availability_time);
        tvProductOriginalPrice = $(R.id.tv_product_original_price);
        tvProductOfferPrice = $(R.id.tv_product_offer_price);
    }

    @Override
    public void setData(final Product data) {
        tvProductName.setText(data.getName());
        Picasso.get().load(data.getImage()).into(ivProductImage);
    }
}