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
import com.meembusoft.recyclerview.MRecyclerView;
import com.meembusoft.recyclerview.adapter.RecyclerArrayAdapter;
import com.meembusoft.recyclerview.effect.FanEffect;
import com.meembusoft.recyclerview.listener.MRecyclerViewScrollListener;

public class ProductListFragment extends BaseFragment {

    private Category mCategory;
    private RecyclerView rvSubCategory;
    private MRecyclerView rvProduct;
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
        // Setup subcategory recyclerview
        rvSubCategory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvSubCategory.setHasFixedSize(true);

        // Setup subcategory adapter
        mSubCategoryListAdapter = new SubCategoryListAdapter(getActivity());
        mSubCategoryListAdapter.addAll(category.getSubcategory());
        mSubCategoryListAdapter.setSelection(0);
        mSubCategoryListAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                mSubCategoryListAdapter.setSelection(i);
            }
        });

        // Load subcategory adapter
        rvSubCategory.setAdapter(mSubCategoryListAdapter);
        rvSubCategory.smoothScrollToPosition(0);

        initProduct(category);
    }

    private void initProduct(Category category) {
        // Setup product recyclerview
        rvProduct.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Setup product adapter
        mProductListAdapter = new ProductListAdapter(getActivity());
        mProductListAdapter.addAll(category.getSubcategory());

        // Load subcategory adapter
        rvProduct.setAdapter(mProductListAdapter);
        rvProduct.addOnScrollListener(new MRecyclerViewScrollListener(new FanEffect()));
    }
}