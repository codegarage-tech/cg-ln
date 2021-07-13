package com.meembusoft.ln.viewholder;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.meembusoft.ln.R;
import com.meembusoft.ln.model.Category;
import com.meembusoft.ln.model.Product;
import com.meembusoft.ln.model.Subcategory;
import com.meembusoft.ln.model.Suggestion;
import com.meembusoft.recyclerview.viewholder.BaseViewHolder;
import com.squareup.picasso.Picasso;

public class SuggestionViewHolder extends BaseViewHolder<Suggestion> {

    private TextView tvSuggestionName;
    private ImageView ivSuggestionImage;
    private String TAG = "SuggestionViewHolder";

    public SuggestionViewHolder(ViewGroup parent) {
        super(parent, R.layout.row_item_category_circle);

        tvSuggestionName = $(R.id.tv_item_name);
        ivSuggestionImage = $(R.id.iv_item_image);
    }

    @Override
    public void setData(final Suggestion data) {
        if (data instanceof Product) {
            Product product = (Product) data;
            tvSuggestionName.setText(product.getName());
            Picasso.get().load(product.getImages().get(0).getUrl()).into(ivSuggestionImage);
        } else if (data instanceof Category) {
            Category category = (Category) data;
            tvSuggestionName.setText(category.getName());
            Picasso.get().load(category.getImage()).into(ivSuggestionImage);
        } else if (data instanceof Subcategory) {
            Subcategory subcategory = (Subcategory) data;
            tvSuggestionName.setText(subcategory.getName());
            Picasso.get().load(subcategory.getImage()).into(ivSuggestionImage);
        }
    }
}