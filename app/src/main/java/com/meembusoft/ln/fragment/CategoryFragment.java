package com.meembusoft.ln.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.meembusoft.ln.R;
import com.meembusoft.ln.activity.CategoryActivity;
import com.meembusoft.ln.adapter.SubCategoryViewPagerAdapter;
import com.meembusoft.ln.base.BaseFragment;
import com.meembusoft.ln.model.colormatchtab.Category;
import com.meembusoft.ln.util.FragmentUtilsManager;

import java.util.List;

public class CategoryFragment extends BaseFragment {

    private Category mCategory;
    //    private Subcategory mLastSelectedSubCategory;
//    private RecyclerView rvSubCategory;
//    private MRecyclerView rvProduct;
//    private SubCategoryListAdapter mSubCategoryListAdapter;
//    private ProductListAdapter mProductListAdapter;
//    // Filter
//    private ArrayMap<String, List<String>> appliedFilters = new ArrayMap<>();
//    private FloatingActionButton fabFilter;
//    private ProductFilterFragment productFilterFragment;
    private TabLayout tabLayoutSubCategory;
    private ViewPager viewPagerSubCategory;
    private SubCategoryViewPagerAdapter subCategoryViewPagerAdapter;
    private String TAG = CategoryFragment.class.getSimpleName();

    public CategoryFragment(Category category) {
        mCategory = category;
    }

    @Override
    public int initFragmentLayout() {
        return R.layout.fragment_category;
    }

    @Override
    public void initFragmentBundleData(Bundle bundle) {

    }

    @Override
    public void initFragmentViews(View parentView) {
//        rvSubCategory = parentView.findViewById(R.id.rv_sub_category);
//        rvProduct = parentView.findViewById(R.id.rv_product);
//        fabFilter = parentView.findViewById(R.id.fab_filter);

        tabLayoutSubCategory = parentView.findViewById(R.id.tabLayout_sub_category);
        viewPagerSubCategory = parentView.findViewById(R.id.viewpager_sub_category);
    }

    @Override
    public void initFragmentViewsData() {
        subCategoryViewPagerAdapter = new SubCategoryViewPagerAdapter(getChildFragmentManager(), mCategory.getSubcategory());
        viewPagerSubCategory.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                List<Fragment> subFragments = getChildFragmentManager().getFragments();
                if (subFragments != null && subFragments.size() > 0) {
                    Log.d(TAG, "onPageSelected>>subFragments>>size: " + subFragments.size());

                    for (Fragment subFragment : subFragments) {
                        if (subFragment instanceof SubCategoryFragment) {
                            Log.d(TAG, "onPageSelected>>subFragment>>name: " + ((SubCategoryFragment) subFragment).getSubCategory().getName());
                        }
                    }

                    Fragment visibleViewPagerSubFragment = FragmentUtilsManager.getVisibleViewPagerFragment(getChildFragmentManager(), viewPagerSubCategory);
                    Log.d(TAG, "onPageSelected>>visibleViewPagerSubFragment>>name: " + ((SubCategoryFragment) visibleViewPagerSubFragment).getSubCategory().getName());
                    ((CategoryActivity) getActivity()).setSelectedSubCategory(((SubCategoryFragment) visibleViewPagerSubFragment).getSubCategory());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPagerSubCategory.setAdapter(subCategoryViewPagerAdapter);
        tabLayoutSubCategory.setupWithViewPager(viewPagerSubCategory);

        viewPagerSubCategory.post(new Runnable() {
            @Override
            public void run() {
                // For avoiding multiple view pager instance
                if (((CategoryActivity) getActivity()).getSelectedCategory().getName().equalsIgnoreCase(mCategory.getName())) {
                    List<Fragment> subFragments = getChildFragmentManager().getFragments();
                    if (subFragments != null && subFragments.size() > 0) {
                        Log.d(TAG, "onPageSelected>>post>>subFragments>>size: " + subFragments.size());

                        for (Fragment subFragment : subFragments) {
                            if (subFragment instanceof SubCategoryFragment) {
                                Log.d(TAG, "onPageSelected>>post>>subFragment>>name: " + ((SubCategoryFragment) subFragment).getSubCategory().getName());
                            }
                        }

                        Fragment visibleViewPagerSubFragment = FragmentUtilsManager.getVisibleViewPagerFragment(getChildFragmentManager(), viewPagerSubCategory);
                        Log.d(TAG, "onPageSelected>>post>>visibleViewPagerSubFragment>>name: " + ((SubCategoryFragment) visibleViewPagerSubFragment).getSubCategory().getName());
                        ((CategoryActivity) getActivity()).setSelectedSubCategory(((SubCategoryFragment) visibleViewPagerSubFragment).getSubCategory());
                    }
                }
            }
        });

//        initUI();
//        initSubCategory(mCategory);
//
//        fabFilter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                productFilterFragment = ProductFilterFragment.newInstance(appliedFilters);
//                productFilterFragment.setParentFab(fabFilter);
//                productFilterFragment.setCallbacks((AAH_FabulousFragment.Callbacks) getActivity());
//                productFilterFragment.setAnimationListener((AAH_FabulousFragment.AnimationListener) getActivity());
//
//                productFilterFragment.show(getActivity().getSupportFragmentManager(), productFilterFragment.getTag());
//            }
//        });
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

//    private void initUI() {
//        // Setup subcategory recyclerview
//        rvSubCategory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
//        rvSubCategory.setHasFixedSize(true);
//        // Setup subcategory adapter
//        mSubCategoryListAdapter = new SubCategoryListAdapter(getActivity());
//        mSubCategoryListAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int i) {
//                mSubCategoryListAdapter.setSelection(i);
//                initProduct(mSubCategoryListAdapter.getSelectedSubCategory());
//            }
//        });
//        // Load subcategory adapter
//        rvSubCategory.setAdapter(mSubCategoryListAdapter);
//
//        // Setup product recyclerview
//        rvProduct.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mProductListAdapter = new ProductListAdapter(getActivity());
//        // Load subcategory adapter
//        rvProduct.setAdapter(mProductListAdapter);
//    }
//
//    private void initSubCategory(Category category) {
//        mSubCategoryListAdapter.addAll(category.getSubcategory());
//        mSubCategoryListAdapter.setSelection(0);
//        rvSubCategory.smoothScrollToPosition(0);
//
//        initProduct(mSubCategoryListAdapter.getSelectedSubCategory());
//    }
//
//    private void initProduct(Subcategory subcategory) {
//        mLastSelectedSubCategory = subcategory;
//        // Setup product adapter
//        List<Product> products = new ArrayList<>();
//        if (subcategory.getName().equalsIgnoreCase("Rice")) {
//            products.addAll(DataUtil.getAllProducts(getActivity(), subcategory));
////            products.addAll(DataUtil.getAllProducts(getActivity(), subcategory));
//        }
//        mProductListAdapter.clear();
//        mProductListAdapter.addAll(products);
//    }
//
//    /***************************
//     * Fabulous Filter methods *
//     ***************************/
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        if (productFilterFragment.isAdded()) {
//            productFilterFragment.dismiss();
//            productFilterFragment.show(getActivity().getSupportFragmentManager(), productFilterFragment.getTag());
//        }
//    }
//
//    public void onResult(Object result) {
////        Log.d(TAG, "onResult: " + result.toString());
//
////        if (result != null) {
////
////            if (result.toString().equalsIgnoreCase("swiped_down")) {
////                //do something or nothing
////            } else {
////
////                appliedFilters = (ArrayMap<String, List<String>>) result;
////                ArrayMap<String, List<String>> appliedFilters = (ArrayMap<String, List<String>>) result;
////                if (appliedFilters.size() != 0) {
////
////                    if (appliedFilters.get("food_category") != null) {
////                        if (appliedFilters.get("food_category").size() == 1) {
////                            selectedFoodCategory = appliedFilters.get("food_category").get(0);
////                        } else {
////                            selectedFoodCategory = "";
////                        }
////                    } else {
////                        selectedFoodCategory = "";
////                    }
////
////                    if (appliedFilters.get("restaurant_category") != null) {
////                        if (appliedFilters.get("restaurant_category").size() == 1) {
////                            selectedRestaurantCategory = appliedFilters.get("restaurant_category").get(0);
////                        } else {
////                            selectedRestaurantCategory = "";
////                        }
////                    } else {
////                        selectedRestaurantCategory = "";
////                    }
////                } else {
////                    selectedFoodCategory = "";
////                    selectedRestaurantCategory = "";
////                }
////
////                searchRestaurant(mLocation, getSelectedFoodCategory(selectedFoodCategory).getId(), getSelectedRestaurantCategory(selectedRestaurantCategory).getId());
////            }
////        }
//    }


    public Category getCategory() {
        return mCategory;
    }

    public ViewPager getViewPagerSubCategory() {
        return viewPagerSubCategory;
    }
}