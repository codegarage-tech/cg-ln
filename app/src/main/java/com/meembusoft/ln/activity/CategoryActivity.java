package com.meembusoft.ln.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.collection.ArrayMap;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alexvasilkov.foldablelayout.UnfoldableView;
import com.alexvasilkov.foldablelayout.shading.GlanceFoldShading;
import com.allattentionhere.fabulousfilter.AAH_FabulousFragment;
import com.athbk.ultimatetablayout.UltimateTabLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.meembusoft.ln.R;
import com.meembusoft.ln.adapter.CategoryViewPagerAdapter;
import com.meembusoft.ln.base.BaseActivity;
import com.meembusoft.ln.fragment.CategoryFragment;
import com.meembusoft.ln.fragment.SubCategoryFragment;
import com.meembusoft.ln.fragment.filter.ProductFilterFragment;
import com.meembusoft.ln.interfaces.OnCartResetListener;
import com.meembusoft.ln.model.Category;
import com.meembusoft.ln.model.Product;
import com.meembusoft.ln.model.Unit;
import com.meembusoft.ln.util.AppUtil;
import com.meembusoft.ln.util.DataUtil;
import com.meembusoft.ln.util.FragmentUtilsManager;

import java.util.List;

import static com.meembusoft.ln.util.Constants.INTENT_KEY_CATEGORY;

public class CategoryActivity extends BaseActivity implements AAH_FabulousFragment.Callbacks, AAH_FabulousFragment.AnimationListener {

    // Toolbar
    private TextView tvTitle, tvCart;
    private LinearLayout llClose;
    private RelativeLayout rlCart;
    private ImageView ivCart;

    private ViewPager viewPagerCategory;
    private UltimateTabLayout ultimateTabLayout;
    private int mSelectedCategoryPosition = 0;

    // Filter
    private SubCategoryFragment mSubCategoryFragment;
    private CategoryFragment mCategoryFragment;
    private ArrayMap<String, List<String>> appliedFilters = new ArrayMap<>();
    private FloatingActionButton fabFilter;
    private ProductFilterFragment productFilterFragment;

    // Foldablelayout
    private View listTouchInterceptor;
    private LinearLayout detailsLayout;
    private UnfoldableView unfoldableView;

    @Override
    public int initToolbarLayout() {
        return R.layout.toolbar_category;
    }

    @Override
    public int initScreenLayout() {
        return R.layout.activity_category;
    }

    @Override
    public void initIntentData(Bundle savedInstanceState, Intent intent) {
        // Get intent data
        if (intent != null) {
            mSelectedCategoryPosition = intent.getIntExtra(INTENT_KEY_CATEGORY, 0);
            Log.d(TAG, "mSelectedCategoryPosition: " + mSelectedCategoryPosition);
        }
    }

    @Override
    public void initViews() {
        // Toolbar
        tvTitle = findViewById(R.id.tv_title);
        tvCart = findViewById(R.id.tv_cart);
        llClose = findViewById(R.id.ll_close);
        rlCart = findViewById(R.id.rl_cart);
        ivCart = findViewById(R.id.iv_cart);

        viewPagerCategory = (ViewPager) findViewById(R.id.view_pager_category);
        ultimateTabLayout = (UltimateTabLayout) findViewById(R.id.ultimate_tab_layout);
        fabFilter = findViewById(R.id.fab_filter);
    }

    @Override
    public void initViewsData(Bundle savedInstanceState) {
        // Toolbar
        tvTitle.setText(R.string.txt_product);

        initFoldableLayout();

        initCategories();
    }

    @Override
    public void initActions(Bundle savedInstanceState) {
        fabFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSubCategoryFragment != null && mSubCategoryFragment.getFilterKeys().size() > 0) {
                    productFilterFragment = ProductFilterFragment.newInstance(mSubCategoryFragment.getFilterKeys());
                    productFilterFragment.setAppliedFilterKeys(mSubCategoryFragment.getAppliedFilterKeys());
                    productFilterFragment.setParentFab(fabFilter);
                    productFilterFragment.setCallbacks((AAH_FabulousFragment.Callbacks) getActivity());
                    productFilterFragment.setAnimationListener((AAH_FabulousFragment.AnimationListener) getActivity());

                    productFilterFragment.show(getSupportFragmentManager(), productFilterFragment.getTag());
                }
            }
        });

        llClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initBackPress();
            }
        });

        rlCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtil.navigateToCartScreen(getActivity());
            }
        });
    }

    @Override
    public void initActivityOnResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void initBackPress() {
        if (unfoldableView.isUnfolded() || unfoldableView.isUnfolding()) {
            unfoldableView.foldBack();
            return;
        }
        finish();
    }

    @Override
    public void initDestroyTasks() {

    }

    @Override
    public void onAllPermissionsAccepted() {

    }

    @Override
    public void onResume() {
        super.onResume();
        //Reset counter view into toolbar
        DataUtil.resetCartCounterView(tvCart, new OnCartResetListener() {
            @Override
            public void onOrderCompleted(boolean isOrderCompleted) {
                if (isOrderCompleted) {
                    List<Fragment> fragments = getSupportFragmentManager().getFragments();
                    if (fragments != null && fragments.size() > 0) {
                        Log.d(TAG, "onOrderCompleted>>fragments>>size: " + fragments.size());
                        for (Fragment mFragment : fragments) {

                            if (mFragment instanceof CategoryFragment) {
                                Log.d(TAG, "onOrderCompleted>>post>>fragment>>name: " + ((CategoryFragment) mFragment).getCategory().getName());
                                CategoryFragment categoryFragment = (CategoryFragment) mFragment;
                                List<Fragment> subFragments = categoryFragment.getChildFragmentManager().getFragments();
                                if (subFragments != null && subFragments.size() > 0) {
                                    Log.d(TAG, "onPageSelected>>subFragments>>size: " + subFragments.size());
                                    for (Fragment subFragment : subFragments) {

                                        if (subFragment instanceof SubCategoryFragment) {
                                            Log.d(TAG, "onPageSelected>>post>>subFragment>>name: " + ((SubCategoryFragment) subFragment).getSubCategory().getName());
                                            SubCategoryFragment subCategoryFragment = (SubCategoryFragment) subFragment;
                                            List<Product> allProducts = subCategoryFragment.getProductListAdapter().getAllData();
                                            if (allProducts != null && allProducts.size() > 0) {
                                                for (Product product : allProducts) {
                                                    Log.d(TAG, "onOrderCompleted>>productName: " + product.getName());
                                                    for (Unit unit : product.getUnits()) {
                                                        Log.d(TAG, "onOrderCompleted>>unitName: " + unit.getName());
                                                        product.setSelectedQuantity(unit.getName(), 0);
                                                    }
                                                }
                                                subCategoryFragment.getProductListAdapter().notifyDataSetChanged();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    private void initCategories() {
        List<Category> categories = DataUtil.getAllCategoriesWithSubcategories(getActivity());
        CategoryViewPagerAdapter categoryPagerAdapter = new CategoryViewPagerAdapter(getSupportFragmentManager(), categories);
        viewPagerCategory.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                List<Fragment> fragments = getSupportFragmentManager().getFragments();
                if (fragments != null && fragments.size() > 0) {
                    Log.d(TAG, "onPageSelected>>fragments>>size: " + fragments.size());
//                    for (Fragment mFragment : fragments) {
//                        if (mFragment instanceof CategoryFragment) {
//                            Log.d(TAG, "onPageSelected>>post>>fragment>>name: " + ((CategoryFragment) mFragment).getCategory().getName());
//                        }
//                    }

                    Fragment visibleViewPagerFragment = FragmentUtilsManager.getVisibleViewPagerFragment(getSupportFragmentManager(), viewPagerCategory);
                    if (visibleViewPagerFragment != null) {
                        CategoryFragment categoryFragment = (CategoryFragment) visibleViewPagerFragment;
                        setCategoryFragment(categoryFragment);

                        List<Fragment> subFragments = categoryFragment.getChildFragmentManager().getFragments();
                        if (subFragments != null && subFragments.size() > 0) {
                            Log.d(TAG, "onPageSelected>>subFragments>>size: " + subFragments.size());

//                            for (Fragment subFragment : subFragments) {
//                                if (subFragment instanceof SubCategoryFragment) {
//                                    Log.d(TAG, "onPageSelected>>post>>subFragment>>name: " + ((SubCategoryFragment) subFragment).getSubCategory().getName());
//                                }
//                            }

                            Fragment visibleViewPagerSubFragment = FragmentUtilsManager.getVisibleViewPagerFragment(categoryFragment.getChildFragmentManager(), categoryFragment.getViewPagerSubCategory());
                            if (visibleViewPagerSubFragment != null) {
                                SubCategoryFragment subCategoryFragment = (SubCategoryFragment) visibleViewPagerSubFragment;
                                setSubCategoryFragment(subCategoryFragment);
                            }
                        }
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPagerCategory.setAdapter(categoryPagerAdapter);
        ultimateTabLayout.setViewPager(viewPagerCategory, categoryPagerAdapter);

        // For the very first time selected item
        viewPagerCategory.post(new Runnable() {
            @Override
            public void run() {
                // Open selected category from home screen
                viewPagerCategory.setCurrentItem(mSelectedCategoryPosition);

                List<Fragment> fragments = getSupportFragmentManager().getFragments();
                if (fragments != null && fragments.size() > 0) {
                    Log.d(TAG, "onPageSelected>>fragments>>size: " + fragments.size());
//                    for (Fragment mFragment : fragments) {
//                        if (mFragment instanceof CategoryFragment) {
//                            Log.d(TAG, "onPageSelected>>post>>fragment>>name: " + ((CategoryFragment) mFragment).getCategory().getName());
//                        }
//                    }

                    Fragment visibleViewPagerFragment = FragmentUtilsManager.getVisibleViewPagerFragment(getSupportFragmentManager(), viewPagerCategory);
                    if (visibleViewPagerFragment != null) {
                        CategoryFragment categoryFragment = (CategoryFragment) visibleViewPagerFragment;
                        setCategoryFragment(categoryFragment);

                        List<Fragment> subFragments = categoryFragment.getChildFragmentManager().getFragments();
                        if (subFragments != null && subFragments.size() > 0) {
                            Log.d(TAG, "onPageSelected>>subFragments>>size: " + subFragments.size());

//                            for (Fragment subFragment : subFragments) {
//                                if (subFragment instanceof SubCategoryFragment) {
//                                    Log.d(TAG, "onPageSelected>>post>>subFragment>>name: " + ((SubCategoryFragment) subFragment).getSubCategory().getName());
//                                }
//                            }

                            Fragment visibleViewPagerSubFragment = FragmentUtilsManager.getVisibleViewPagerFragment(categoryFragment.getChildFragmentManager(), categoryFragment.getViewPagerSubCategory());
                            if (visibleViewPagerSubFragment != null) {
                                SubCategoryFragment subCategoryFragment = (SubCategoryFragment) visibleViewPagerSubFragment;
                                setSubCategoryFragment(subCategoryFragment);
                            }
                        }
                    }
                }
            }
        });
    }

    /***************************
     * Fabulous Filter methods *
     ***************************/
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (productFilterFragment.isAdded()) {
            productFilterFragment.dismiss();
            productFilterFragment.show(getSupportFragmentManager(), productFilterFragment.getTag());
        }
    }

    @Override
    public void onResult(Object result) {
        Log.d(TAG, "filter>>onResult: " + result.toString());
        if (result != null) {
            if (result.toString().equalsIgnoreCase("swiped_down")) {
                //do something or nothing
            } else if (result.toString().equalsIgnoreCase("closed")) {
                // do nothing
            } else {
                appliedFilters = (ArrayMap<String, List<String>>) result;
                if (mSubCategoryFragment != null) {
                    mSubCategoryFragment.setAppliedFilterKeys(appliedFilters);
                }
            }
        }
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

    public SubCategoryFragment getSubCategoryFragment() {
        return mSubCategoryFragment;
    }

    public void setSubCategoryFragment(SubCategoryFragment subCategoryFragment) {
        this.mSubCategoryFragment = subCategoryFragment;
        Log.d(TAG, "onPageSelected>>mSubCategoryFragment: " + mSubCategoryFragment.getSubCategory().getName());

        // Gone filter view if there is no filter keys
        if (mSubCategoryFragment.getFilterKeys().size() == 0) {
            fabFilter.setVisibility(View.GONE);
        } else {
            fabFilter.setVisibility(View.VISIBLE);
        }
    }

    public CategoryFragment getCategoryFragment() {
        return mCategoryFragment;
    }

    public void setCategoryFragment(CategoryFragment categoryFragment) {
        this.mCategoryFragment = categoryFragment;
        Log.d(TAG, "onPageSelected>>mCategoryFragment: " + mCategoryFragment.getCategory().getName());
    }

    public ImageView getCartIcon() {
        return ivCart;
    }

    public TextView getCartCounter() {
        return tvCart;
    }

    /******************
     * FoldableLayout *
     ******************/
//    private void setBackButtonAction(final UnfoldableView unfoldableView) {
//        ivContentBack.setOnClickListener(new OnSingleClickListener() {
//            @Override
//            public void onSingleClick(View view) {
//                if (unfoldableView.isUnfolded() || unfoldableView.isUnfolding()) {
//                    unfoldableView.foldBack();
//                }
//            }
//        });
//    }
    private void initFoldableLayout() {

        listTouchInterceptor = (View) findViewById(R.id.touch_interceptor_view);
        listTouchInterceptor.setClickable(false);

        detailsLayout = (LinearLayout) findViewById(R.id.details_layout);
        detailsLayout.setVisibility(View.INVISIBLE);

        unfoldableView = (UnfoldableView) findViewById(R.id.unfoldable_view);

        Bitmap glance = BitmapFactory.decodeResource(getResources(), R.drawable.unfold_glance);
        unfoldableView.setFoldShading(new GlanceFoldShading(glance));

        unfoldableView.setOnFoldingListener(new UnfoldableView.SimpleFoldingListener() {

            @Override
            public void onUnfolding(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(true);
                detailsLayout.setVisibility(View.VISIBLE);

//                // change layout view while unfolding
//                mFilter.setVisibility(View.INVISIBLE);
//                ivListToGrid.setVisibility(View.INVISIBLE);
//                tvTitle.setText(getString(R.string.title_activity_file_detail));
//                ivContentHamburger.setVisibility(View.GONE);
//                ivContentBack.setVisibility(View.VISIBLE);
//
//                // after folding view it always lost action when it is unfolded again
//                setBackButtonAction(unfoldableView);

            }

            @Override
            public void onUnfolded(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(false);

                fabFilter.setVisibility(View.INVISIBLE);
                rlCart.setVisibility(View.INVISIBLE);
                tvTitle.setText(getString(R.string.txt_product_detail));
            }

            @Override
            public void onFoldingBack(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(true);
            }

            @Override
            public void onFoldedBack(UnfoldableView unfoldableView) {
                listTouchInterceptor.setClickable(false);
                detailsLayout.setVisibility(View.INVISIBLE);

//                // change layout view while unfolding
//                mFilter.setVisibility(View.VISIBLE);
//                ivListToGrid.setVisibility(View.VISIBLE);
//                tvTitle.setText(getString(R.string.title_activity_home));
//                ivContentBack.setVisibility(View.GONE);
//                ivContentHamburger.setVisibility(View.VISIBLE);

                fabFilter.setVisibility(View.VISIBLE);
                rlCart.setVisibility(View.VISIBLE);
                tvTitle.setText(getString(R.string.txt_product));
            }
        });
    }

    public void openDetails(View detailLayout, View rowItemView, Product deletedFile) {

//        LinearLayout llImageBackground = (LinearLayout) detailsLayout.findViewById(R.id.ll_image_bg);
//        TextView tvDetailFileName = (TextView) detailsLayout.findViewById(R.id.tv_detail_file_name);
//        TextView tvDetailFileLocation = (TextView) detailsLayout.findViewById(R.id.tv_detail_file_location);
//
//        if (deletedFile.getTags().size() > 0) {
//            Tag firstTag = deletedFile.getTags().get(0);
//            GradientDrawable drawable = new GradientDrawable();
//            drawable.setColor(firstTag.getColor());
//            llImageBackground.setBackgroundDrawable(drawable);
//        }
//
//        tvDetailFileName.setText(deletedFile.getOriginFileName());
//        tvDetailFileLocation.setText(deletedFile.getOriginFilePath());

        unfoldableView.unfold(rowItemView, detailsLayout);
    }
}