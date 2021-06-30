package com.meembusoft.ln.fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.collection.ArrayMap;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.allattentionhere.fabulousfilter.AAH_FabulousFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.meembusoft.ln.R;
import com.meembusoft.ln.adapter.ProductListAdapter;
import com.meembusoft.ln.base.BaseFragment;
import com.meembusoft.ln.fragment.filter.ProductFilterFragment;
import com.meembusoft.ln.model.colormatchtab.Product;
import com.meembusoft.ln.model.colormatchtab.Subcategory;
import com.meembusoft.ln.util.DataUtil;
import com.meembusoft.recyclerview.MRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SubCategoryFragment extends BaseFragment {

    private Subcategory mSubCategory;
    private MRecyclerView rvProduct;
    private ProductListAdapter mProductListAdapter;
    // Filter
    private ArrayMap<String, List<String>> appliedFilters = new ArrayMap<>();
    private FloatingActionButton fabFilter;
    private ProductFilterFragment productFilterFragment;
    private String TAG = SubCategoryFragment.class.getSimpleName();

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
        fabFilter = parentView.findViewById(R.id.fab_filter);
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

        fabFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productFilterFragment = ProductFilterFragment.newInstance(appliedFilters);
                productFilterFragment.setParentFab(fabFilter);
                productFilterFragment.setCallbacks((AAH_FabulousFragment.Callbacks) getActivity());
                productFilterFragment.setAnimationListener((AAH_FabulousFragment.AnimationListener) getActivity());

                productFilterFragment.show(getActivity().getSupportFragmentManager(), productFilterFragment.getTag());
            }
        });
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

    /***************************
     * Fabulous Filter methods *
     ***************************/
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (productFilterFragment.isAdded()) {
            productFilterFragment.dismiss();
            productFilterFragment.show(getActivity().getSupportFragmentManager(), productFilterFragment.getTag());
        }
    }

    public void onResult(Object result) {
//        Log.d(TAG, "onResult: " + result.toString());

//        if (result != null) {
//
//            if (result.toString().equalsIgnoreCase("swiped_down")) {
//                //do something or nothing
//            } else {
//
//                appliedFilters = (ArrayMap<String, List<String>>) result;
//                ArrayMap<String, List<String>> appliedFilters = (ArrayMap<String, List<String>>) result;
//                if (appliedFilters.size() != 0) {
//
//                    if (appliedFilters.get("food_category") != null) {
//                        if (appliedFilters.get("food_category").size() == 1) {
//                            selectedFoodCategory = appliedFilters.get("food_category").get(0);
//                        } else {
//                            selectedFoodCategory = "";
//                        }
//                    } else {
//                        selectedFoodCategory = "";
//                    }
//
//                    if (appliedFilters.get("restaurant_category") != null) {
//                        if (appliedFilters.get("restaurant_category").size() == 1) {
//                            selectedRestaurantCategory = appliedFilters.get("restaurant_category").get(0);
//                        } else {
//                            selectedRestaurantCategory = "";
//                        }
//                    } else {
//                        selectedRestaurantCategory = "";
//                    }
//                } else {
//                    selectedFoodCategory = "";
//                    selectedRestaurantCategory = "";
//                }
//
//                searchRestaurant(mLocation, getSelectedFoodCategory(selectedFoodCategory).getId(), getSelectedRestaurantCategory(selectedRestaurantCategory).getId());
//            }
//        }
    }
}