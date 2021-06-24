package com.meembusoft.ln.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meembusoft.ln.R;
import com.meembusoft.ln.adapter.ProductListAdapter;
import com.meembusoft.ln.adapter.SubCategoryListAdapter;
import com.meembusoft.ln.base.BaseFragment;
import com.meembusoft.ln.model.colormatchtab.Category;
import com.meembusoft.recyclerview.effect.FanEffect;
import com.meembusoft.recyclerview.listener.MRecyclerViewScrollListener;

public class ProductListFragment extends BaseFragment {

    private Category mCategory;
    private RecyclerView rvSubCategory, rvProduct;
    private SubCategoryListAdapter mSubCategoryListAdapter;
    private ProductListAdapter mProductListAdapter;
    private String TAG = ProductListFragment.class.getSimpleName();

    public ProductListFragment(Category category) {
        mCategory = category;
    }

    @Override
    public int initFragmentLayout() {
        return R.layout.fragment_product_list;
    }

    @Override
    public void initFragmentBundleData(Bundle bundle) {

    }

    @Override
    public void initFragmentViews(View parentView) {
        rvSubCategory = parentView.findViewById(R.id.rv_sub_category);
        rvProduct = parentView.findViewById(R.id.rv_product);
    }

    @Override
    public void initFragmentViewsData() {
        initSubCategory(mCategory);
    }

    @Override
    public void initFragmentActions() {

    }

    @Override
    public void initFragmentBackPress() {

    }

    @Override
    public void initFragmentUpdate(Object object) {

    }

    @Override
    public void initFragmentOnResult(int requestCode, int resultCode, Intent data) {

    }

    private void initSubCategory(Category category) {
        rvSubCategory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvSubCategory.setHasFixedSize(true);
        mSubCategoryListAdapter = new SubCategoryListAdapter(category);
        rvSubCategory.setAdapter(mSubCategoryListAdapter);
        mSubCategoryListAdapter.setSelection(0);
        rvSubCategory.smoothScrollToPosition(0);

        initProduct(category);
    }

    private void initProduct(Category category) {
        rvProduct.setLayoutManager(new LinearLayoutManager(getActivity()));
        mProductListAdapter = new ProductListAdapter(category);
        rvProduct.setAdapter(mProductListAdapter);
        rvProduct.addOnScrollListener(new MRecyclerViewScrollListener(new FanEffect()));
    }
}