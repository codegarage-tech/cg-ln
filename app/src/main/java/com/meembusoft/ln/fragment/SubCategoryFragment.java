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
    private List<Product> mProducts = new ArrayList<>();
    private String TAG = SubCategoryFragment.class.getSimpleName();
    private ArrayMap<String, List<String>> mFilterKeys = new ArrayMap<>();
    private ArrayMap<String, List<String>> mAppliedFilterKeys = new ArrayMap<>();

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
        mFilterKeys.clear();
        mAppliedFilterKeys.clear();
        mProducts.clear();
        mProducts.addAll(DataUtil.getAllProducts(getActivity(), mSubCategory));
        mProductListAdapter.clear();
        mProductListAdapter.addAll(mProducts);

        // Initialize filter keys
        findFilterKeys(mProducts);
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
        if (productList != null && productList.size() > 0) {
            List<String> vendors = DataUtil.getUniqueVendorKeys(productList);
            if (vendors != null && !vendors.isEmpty()) {
                mFilterKeys.put(FilterType.VENDOR.name(), vendors);
            }
//            mFilterKeys.put(FilterType.PRICE.name(), new ArrayList<>());
        }
    }

    @Override
    public ArrayMap<String, List<String>> getFilterKeys() {
        return mFilterKeys;
    }

    @Override
    public void setAppliedFilterKeys(ArrayMap<String, List<String>> appliedFilterKeys) {
        mAppliedFilterKeys = appliedFilterKeys;
        executeFilter(mProducts, mAppliedFilterKeys);
    }

    @Override
    public ArrayMap<String, List<String>> getAppliedFilterKeys() {
        return mAppliedFilterKeys;
    }

    @Override
    public void executeFilter(List<Product> productList, ArrayMap<String, List<String>> appliedFilterKeys) {
        List<Product> filteredProducts = new ArrayList<>();
        List<Product> allProducts = new ArrayList<>(productList);

        for (int i = 0; i < FilterType.values().length; i++) {
            List<String> keys = appliedFilterKeys.get(FilterType.values()[i].name());
            if (keys != null && !keys.isEmpty()) {
                for (int k = 0; k < keys.size(); k++) {
                    for (int j = 0; j < allProducts.size(); j++) {
                        if (allProducts.get(j).getVendor().equalsIgnoreCase(keys.get(k))) {
                            filteredProducts.add(allProducts.get(j));
                        }
                    }
                }
            }
        }

        mProductListAdapter.clear();
        mProductListAdapter.addAll(filteredProducts);
    }
}