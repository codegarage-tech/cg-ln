package com.meembusoft.ln.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.meembusoft.ln.R;
import com.meembusoft.ln.model.colormatchtab.Category;
import com.meembusoft.ln.model.colormatchtab.Subcategory;
import com.squareup.picasso.Picasso;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    private Category mCategory;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvProductName;
        public TextView tvProductDescription;
        public ImageView ivProductImage;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;

            tvProductName = layout.findViewById(R.id.tv_product_name);
            tvProductDescription = layout.findViewById(R.id.tv_product_description);
            ivProductImage = layout.findViewById(R.id.iv_product_image);
        }
    }

    public ProductListAdapter(Category category) {
        mCategory = category;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.row_item_product, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Subcategory subcategory = mCategory.getSubcategory().get(position);
        holder.tvProductName.setText(subcategory.getName());
        Picasso.get().load(subcategory.getImage()).into(holder.ivProductImage);
    }

    @Override
    public int getItemCount() {
        return mCategory.getSubcategory().size();
    }
}