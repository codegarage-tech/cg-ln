package com.meembusoft.ln.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.collection.ArrayMap;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.meembusoft.ln.R;
import com.meembusoft.ln.adapter.ProductListAdapter;
import com.meembusoft.ln.base.BaseFragment;
import com.meembusoft.ln.enumeration.FilterType;
import com.meembusoft.ln.interfaces.OnProductFilter;
import com.meembusoft.ln.model.colormatchtab.Product;
import com.meembusoft.ln.model.colormatchtab.Subcategory;
import com.meembusoft.ln.util.DataUtil;
import com.meembusoft.recyclerview.MRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SubCategoryFragment extends BaseFragment implements OnProductFilter {

    private Subcategory mSubCategory;
    private MRecyclerView rvProduct;
    private ProductListAdapter mProductListAdapter;
    private String TAG = SubCategoryFragment.class.getSimpleName();
    private ArrayMap<String, List<String>> mFilterKeys = new ArrayMap<>();
    private ArrayMap<String, List<String>> mSelectedFilterKeys = new ArrayMap<>();

    public SubCategoryFragment(Subcategory subcategory) {
        mSubCategory = subcategory;
    }

    @Override
    public int initFragmentLayout() {
        return R.layout.fragment_sub_category;
    }

    @Override
    public void initFragmentBundleData(Bundle bundle) {

    }

    @Override
    public void initFragmentViews(View parentView) {
        rvProduct = parentView.findViewById(R.id.rv_product);
    }

    @Override
    public void initFragmentViewsData() {
        // Setup product recyclerview
        rvProduct.setLayoutManager(new LinearLayoutManager(getActivity()));
        mProductListAdapter = new ProductListAdapter(getActivity());
        // Load subcategory adapter
        rvProduct.setAdapter(mProductListAdapter);
        // Load products into adapter
        List<Product> products = new ArrayList<>();
        products.addAll(DataUtil.getAllProducts(getActivity(), mSubCategory));
        mProductListAdapter.addAll(products);

        // Initialize filter keys
        findFilterKeys(products);
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

    public Subcategory getSubCategory() {
        return mSubCategory;
    }

    @Override
    public void findFilterKeys(List<Product> productList) {
        if(productList != null && productList.size()>0){
            mFilterKeys.put(FilterType.VENDOR.name(), DataUtil.getUniqueVendorKeys(productList));
            mFilterKeys.put(FilterType.PRICE.name(), new ArrayList<>());
        }
    }

    @Override
    public ArrayMap<String, List<String>> getFilterKeys() {
        return mFilterKeys;
    }

    @Override
    public void setSelectedFilterKeys(ArrayMap<String, List<String>> selectedFilterKeys) {
        mSelectedFilterKeys = selectedFilterKeys;
    }

    @Override
    public ArrayMap<String, List<String>> getSelectedFilterKeys() {
        return getSelectedFilterKeys();
    }
}