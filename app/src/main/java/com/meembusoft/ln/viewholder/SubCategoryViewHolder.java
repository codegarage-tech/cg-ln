//package com.meembusoft.ln.viewholder;
//
//import android.graphics.Color;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.meembusoft.ln.R;
//import com.meembusoft.ln.model.colormatchtab.Subcategory;
//import com.meembusoft.recyclerview.viewholder.BaseViewHolder;
//
//public class SubCategoryViewHolder extends BaseViewHolder<Subcategory> {
//
//    private TextView tvSubCategory;
//
//    public SubCategoryViewHolder(ViewGroup parent) {
//        super(parent, R.layout.row_item_sub_category);
//
//        tvSubCategory = $(R.id.tv_sub_category);
//    }
//
//    @Override
//    public void setData(final Subcategory data) {
//        tvSubCategory.setText(data.getName());
//        if (data.isSelected()) {
//            tvSubCategory.setBackgroundResource(R.drawable.chip_selected);
//            tvSubCategory.setTextColor(Color.parseColor("#ff4651"));
//        } else {
//            tvSubCategory.setBackgroundResource(R.drawable.chip_unselected);
//            tvSubCategory.setTextColor(Color.parseColor("#3F51B5"));
//        }
//    }
//}