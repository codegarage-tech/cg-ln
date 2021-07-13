package com.meembusoft.ln.viewholder;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.meembusoft.ln.R;
import com.meembusoft.ln.model.Category;
import com.meembusoft.recyclerview.viewholder.BaseViewHolder;
import com.squareup.picasso.Picasso;

public class CategoryViewHolder extends BaseViewHolder<Category> {

    private TextView tvCategoryName;
    private ImageView ivCategoryImage;

    public CategoryViewHolder(ViewGroup parent) {
        super(parent, R.layout.row_item_category_capsule);

        tvCategoryName = $(R.id.tv_category_name);
        ivCategoryImage = $(R.id.iv_category_image);
    }

    @Override
    public void setData(final Category data) {
        tvCategoryName.setText(data.getName());
        Picasso.get().load(data.getImage()).into(ivCategoryImage);
    }
}