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
import com.meembusoft.ln.model.Category;
import com.meembusoft.ln.util.FragmentUtilsManager;

import java.util.List;

public class CategoryFragment extends BaseFragment {

    private Category mCategory;
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
                    Log.d(TAG, "onPageSelected>>post>>subFragments>>size: " + subFragments.size());

//                        for (Fragment subFragment : subFragments) {
//                            if (subFragment instanceof SubCategoryFragment) {
//                                Log.d(TAG, "onPageSelected>>post>>subFragment>>name: " + ((SubCategoryFragment) subFragment).getSubCategory().getName());
//                            }
//                        }

                    Fragment visibleViewPagerSubFragment = FragmentUtilsManager.getVisibleViewPagerFragment(getChildFragmentManager(), viewPagerSubCategory);
                    if (visibleViewPagerSubFragment != null) {
                        SubCategoryFragment subCategoryFragment = (SubCategoryFragment) visibleViewPagerSubFragment;
                        ((CategoryActivity) getActivity()).setSubCategoryFragment(subCategoryFragment);
                    }
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
                CategoryFragment categoryFragment = ((CategoryActivity) getActivity()).getCategoryFragment();
                if (categoryFragment != null && categoryFragment.getCategory().getName().equalsIgnoreCase(mCategory.getName())) {
                    List<Fragment> subFragments = getChildFragmentManager().getFragments();
                    if (subFragments != null && subFragments.size() > 0) {
                        Log.d(TAG, "onPageSelected>>post>>subFragments>>size: " + subFragments.size());

//                        for (Fragment subFragment : subFragments) {
//                            if (subFragment instanceof SubCategoryFragment) {
//                                Log.d(TAG, "onPageSelected>>post>>subFragment>>name: " + ((SubCategoryFragment) subFragment).getSubCategory().getName());
//                            }
//                        }

                        Fragment visibleViewPagerSubFragment = FragmentUtilsManager.getVisibleViewPagerFragment(getChildFragmentManager(), viewPagerSubCategory);
                        if (visibleViewPagerSubFragment != null) {
                            SubCategoryFragment subCategoryFragment = (SubCategoryFragment) visibleViewPagerSubFragment;
                            ((CategoryActivity) getActivity()).setSubCategoryFragment(subCategoryFragment);
                        }
                    }
                }
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

    public Category getCategory() {
        return mCategory;
    }

    public ViewPager getViewPagerSubCategory() {
        return viewPagerSubCategory;
    }
}