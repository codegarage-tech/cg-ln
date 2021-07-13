package com.meembusoft.ln.viewholder;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.meembusoft.ln.R;
import com.meembusoft.ln.model.Product;
import com.meembusoft.ln.util.RandomManager;
import com.meembusoft.recyclerview.viewholder.BaseViewHolder;
import com.squareup.picasso.Picasso;

public class PopularProductViewHolder extends BaseViewHolder<Product> {

    private TextView tvProductName, tvProductDescription;
    private ImageView ivProductImage;

    public PopularProductViewHolder(ViewGroup parent) {
        super(parent, R.layout.row_item_popular_product);

        tvProductName = $(R.id.tv_product_name);
        tvProductDescription = $(R.id.tv_product_description);
        ivProductImage = $(R.id.iv_product_image);
    }

    @Override
    public void setData(final Product data) {
        tvProductName.setText(data.getName());
        if (data.getImages() != null && !data.getImages().isEmpty()) {
            Picasso.get().load(data.getImages().get(RandomManager.getRandom(0, data.getImages().size())).getUrl()).into(ivProductImage);
        }
    }
}