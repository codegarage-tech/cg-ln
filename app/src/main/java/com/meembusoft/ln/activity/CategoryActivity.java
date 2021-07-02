package com.meembusoft.ln.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.allattentionhere.fabulousfilter.AAH_FabulousFragment;
import com.athbk.ultimatetablayout.UltimateTabLayout;
import com.meembusoft.ln.R;
import com.meembusoft.ln.adapter.CategoryViewPagerAdapter;
import com.meembusoft.ln.base.BaseActivity;
import com.meembusoft.ln.fragment.CategoryFragment;
import com.meembusoft.ln.fragment.SubCategoryFragment;
import com.meembusoft.ln.model.colormatchtab.Category;
import com.meembusoft.ln.model.colormatchtab.Subcategory;
import com.meembusoft.ln.util.DataUtil;
import com.meembusoft.ln.util.FragmentUtilsManager;

import java.util.List;

public class CategoryActivity extends BaseActivity implements AAH_FabulousFragment.Callbacks, AAH_FabulousFragment.AnimationListener {

    // Toolbar
    private TextView tvTitle;
    private LinearLayout llClose;

    private ViewPager viewPagerCategory;
    private UltimateTabLayout ultimateTabLayout;

    // Filter
    private Subcategory mSelectedSubCategory;
    private Category mSelectedCategory;

    @Override
    public int initToolbarLayout() {
        return R.layout.toolbar_base;
    }

    @Override
    public int initScreenLayout() {
        return R.layout.activity_category;
    }

    @Override
    public void initIntentData(Bundle savedInstanceState, Intent intent) {

    }

    @Override
    public void initViews() {
        // Toolbar
        tvTitle = findViewById(R.id.tv_title);
        llClose = findViewById(R.id.ll_close);

        viewPagerCategory = (ViewPager) findViewById(R.id.view_pager_category);
        ultimateTabLayout = (UltimateTabLayout) findViewById(R.id.ultimate_tab_layout);
    }

    @Override
    public void initViewsData(Bundle savedInstanceState) {
        // Toolbar
        tvTitle.setText(R.string.txt_product);

        initTab();
    }

    @Override
    public void initActions(Bundle savedInstanceState) {
        llClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initBackPress();
            }
        });
    }

    @Override
    public void initActivityOnResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void initBackPress() {
        finish();
    }

    @Override
    public void initDestroyTasks() {

    }

    private void initTab() {
        List<Category> categories = DataUtil.getAllCategoriesWithSubcategories(getActivity());

        CategoryViewPagerAdapter pagerAdapter = new CategoryViewPagerAdapter(getSupportFragmentManager(), categories);

        viewPagerCategory.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                List<Fragment> fragments = getSupportFragmentManager().getFragments();
                if (fragments != null && fragments.size() > 0) {
                    Log.d(TAG, "onPageSelected>>fragments>>size: " + fragments.size() + " position: "
                            + position + " current position: " + viewPagerCategory.getCurrentItem());
                    for (Fragment mFragment : fragments) {
                        if (mFragment instanceof CategoryFragment) {
                            Log.d(TAG, "onPageSelected>>fragment>>name: " + ((CategoryFragment) mFragment).getCategory().getName());
                        }
                    }

                    Fragment visibleViewPagerFragment = FragmentUtilsManager.getVisibleViewPagerFragment(getSupportFragmentManager(), viewPagerCategory);
                    setSelectedCategory(((CategoryFragment) visibleViewPagerFragment).getCategory());
                    Log.d(TAG, "onPageSelected>>visibleViewPagerFragment>>name: " + mSelectedCategory.getName());
                    List<Fragment> subFragments = visibleViewPagerFragment.getChildFragmentManager().getFragments();
                    if (subFragments != null && subFragments.size() > 0) {
                        Log.d(TAG, "onPageSelected>>subFragments>>size: " + subFragments.size());

                        for (Fragment subFragment : subFragments) {
                            if (subFragment instanceof SubCategoryFragment) {
                                Log.d(TAG, "onPageSelected>>subFragment>>name: " + ((SubCategoryFragment) subFragment).getSubCategory().getName());
                            }
                        }

                        Fragment visibleViewPagerSubFragment = FragmentUtilsManager.getVisibleViewPagerFragment(visibleViewPagerFragment.getChildFragmentManager(), ((CategoryFragment) visibleViewPagerFragment).getViewPagerSubCategory());
                        Log.d(TAG, "onPageSelected>>visibleViewPagerSubFragment>>name: " + ((SubCategoryFragment) visibleViewPagerSubFragment).getSubCategory().getName());
                        setSelectedSubCategory(((SubCategoryFragment) visibleViewPagerSubFragment).getSubCategory());
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPagerCategory.setAdapter(pagerAdapter);
        ultimateTabLayout.setViewPager(viewPagerCategory, pagerAdapter);

        // For the very first time selected item
        viewPagerCategory.post(new Runnable() {
            @Override
            public void run() {
                List<Fragment> fragments = getSupportFragmentManager().getFragments();
                if (fragments != null && fragments.size() > 0) {
                    Log.d(TAG, "onPageSelected>>post>>fragments>>size: " + fragments.size() + " current position: " + viewPagerCategory.getCurrentItem());
                    for (Fragment mFragment : fragments) {
                        if (mFragment instanceof CategoryFragment) {
                            Log.d(TAG, "onPageSelected>>post>>fragment>>name: " + ((CategoryFragment) mFragment).getCategory().getName());
                        }
                    }

                    Fragment visibleViewPagerFragment = FragmentUtilsManager.getVisibleViewPagerFragment(getSupportFragmentManager(), viewPagerCategory);
                    setSelectedCategory(((CategoryFragment) visibleViewPagerFragment).getCategory());
                    Log.d(TAG, "onPageSelected>>post>>visibleViewPagerFragment>>name: " + mSelectedCategory.getName());
                    List<Fragment> subFragments = visibleViewPagerFragment.getChildFragmentManager().getFragments();
                    if (subFragments != null && subFragments.size() > 0) {
                        Log.d(TAG, "onPageSelected>>post>>subFragments>>size: " + subFragments.size());

                        for (Fragment subFragment : subFragments) {
                            if (subFragment instanceof SubCategoryFragment) {
                                Log.d(TAG, "onPageSelected>>post>>subFragment>>name: " + ((SubCategoryFragment) subFragment).getSubCategory().getName());
                            }
                        }

                        Fragment visibleViewPagerSubFragment = FragmentUtilsManager.getVisibleViewPagerFragment(visibleViewPagerFragment.getChildFragmentManager(), ((CategoryFragment) visibleViewPagerFragment).getViewPagerSubCategory());
                        Log.d(TAG, "onPageSelected>>post>>visibleViewPagerSubFragment>>name: " + ((SubCategoryFragment) visibleViewPagerSubFragment).getSubCategory().getName());
                        setSelectedSubCategory(((SubCategoryFragment) visibleViewPagerSubFragment).getSubCategory());
                    }
                }
            }
        });
    }


    /***************************
     * Fabulous Filter methods *
     ***************************/
    @Override
    public void onResult(Object result) {
        Log.d(TAG, "onResult: " + result.toString());
    }

    @Override
    public void onOpenAnimationStart() {
        Log.d("aah_animation", "onOpenAnimationStart: ");
    }

    @Override
    public void onOpenAnimationEnd() {
        Log.d("aah_animation", "onOpenAnimationEnd: ");
    }

    @Override
    public void onCloseAnimationStart() {
        Log.d("aah_animation", "onCloseAnimationStart: ");
    }

    @Override
    public void onCloseAnimationEnd() {
        Log.d("aah_animation", "onCloseAnimationEnd: ");
    }

    public void setSelectedCategory(Category selectedCategory) {
        this.mSelectedCategory = selectedCategory;
        Log.d(TAG, "onPageSelected>>mSelectedCategory: " + mSelectedCategory.getName());
    }

    public void setSelectedSubCategory(Subcategory selectedSubCategory) {
        this.mSelectedSubCategory = selectedSubCategory;
        Log.d(TAG, "onPageSelected>>mSelectedSubCategory: " + mSelectedSubCategory.getName());
    }

    public Subcategory getSelectedSubCategory() {
        return mSelectedSubCategory;
    }

    public Category getSelectedCategory() {
        return mSelectedCategory;
    }
}
