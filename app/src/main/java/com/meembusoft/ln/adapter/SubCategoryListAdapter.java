package com.meembusoft.ln.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.meembusoft.ln.R;
import com.meembusoft.ln.model.colormatchtab.Category;
import com.meembusoft.ln.model.colormatchtab.Subcategory;

import java.util.List;

public class SubCategoryListAdapter extends RecyclerView.Adapter<SubCategoryListAdapter.ViewHolder> {

    private Category mCategory;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvSubCategory;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;

            tvSubCategory = layout.findViewById(R.id.tv_sub_category);
        }
    }

    public SubCategoryListAdapter(Category category) {
        mCategory = category;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.row_item_sub_category, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Subcategory subcategory = mCategory.getSubcategory().get(position);
        holder.tvSubCategory.setText(subcategory.getName());
        if (subcategory.isSelected()) {
            holder.tvSubCategory.setBackgroundResource(R.drawable.chip_selected);
            holder.tvSubCategory.setTextColor(Color.parseColor("#FFFFFF"));
        } else {
            holder.tvSubCategory.setBackgroundResource(R.drawable.chip_unselected);
            holder.tvSubCategory.setTextColor(Color.parseColor("#3F51B5"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelection(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCategory.getSubcategory().size();
    }

    public void setSelection(int position) {
        List<Subcategory> data = mCategory.getSubcategory();
        for (int i = 0; i < data.size(); i++) {
            if (i == position) {
                data.get(i).setSelected(true);
            } else {
                data.get(i).setSelected(false);
            }
        }
        notifyDataSetChanged();
    }
}