package com.meembusoft.ln.viewholder;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.meembusoft.ln.R;
import com.meembusoft.ln.model.colormatchtab.Product;
import com.meembusoft.recyclerview.viewholder.BaseViewHolder;
import com.squareup.picasso.Picasso;

public class NewProductViewHolder extends BaseViewHolder<Product> {

    private TextView tvProductName, tvProductDescription;
    private ImageView ivProductImage;

    public NewProductViewHolder(ViewGroup parent) {
        super(parent, R.layout.row_item_new_product);

        tvProductName = $(R.id.tv_product_name);
        tvProductDescription = $(R.id.tv_product_description);
        ivProductImage = $(R.id.iv_product_image);
    }

    @Override
    public void setData(final Product data) {
        tvProductName.setText(data.getName());
        Picasso.get().load(data.getImage()).into(ivProductImage);
    }
}