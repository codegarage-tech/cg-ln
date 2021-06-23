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

    private RecyclerView rvProduct, rvSubCategory;
    private ProductListAdapter mProductListAdapter;
    private SubCategoryListAdapter mSubCategoryListAdapter;
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
        rvProduct = parentView.findViewById(R.id.rv_product);
        rvSubCategory = parentView.findViewById(R.id.rv_sub_category);
    }

    @Override
    public void initFragmentViewsData() {
        initializeRecyclerView();
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

    private void initializeRecyclerView() {
        rvProduct.setLayoutManager(new LinearLayoutManager(getActivity()));
        mProductListAdapter = new ProductListAdapter(mCategory);
        rvProduct.setAdapter(mProductListAdapter);
        rvProduct.addOnScrollListener(new MRecyclerViewScrollListener(new FanEffect()));

        rvSubCategory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvSubCategory.setHasFixedSize(true);
        mSubCategoryListAdapter = new SubCategoryListAdapter(mCategory);
        rvSubCategory.setAdapter(mSubCategoryListAdapter);
        mSubCategoryListAdapter.setSelection(0);
    }
}