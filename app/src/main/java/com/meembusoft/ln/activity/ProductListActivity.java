package com.meembusoft.ln.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.athbk.ultimatetablayout.OnClickTabListener;
import com.athbk.ultimatetablayout.UltimateTabLayout;
import com.meembusoft.ln.R;
import com.meembusoft.ln.adapter.ProductViewPagerAdapter;
import com.meembusoft.ln.base.BaseActivity;
import com.meembusoft.ln.model.colormatchtab.Category;
import com.meembusoft.ln.util.DataUtil;

import java.util.List;

public class ProductListActivity extends BaseActivity {

    // Toolbar
    private TextView tvTitle;
    private LinearLayout llClose;

    private ViewPager viewPager;
    private UltimateTabLayout ultimateTabLayout;

    @Override
    public int initToolbarLayout() {
        return R.layout.toolbar_base;
    }

    @Override
    public int initScreenLayout() {
        return R.layout.activity_product_list;
    }

    @Override
    public void initIntentData(Bundle savedInstanceState, Intent intent) {

    }

    @Override
    public void initViews() {
        // Toolbar
        tvTitle = findViewById(R.id.tv_title);
        llClose = findViewById(R.id.ll_close);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
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

        ProductViewPagerAdapter pagerAdapter = new ProductViewPagerAdapter(getSupportFragmentManager(), categories);
        viewPager.setAdapter(pagerAdapter);

        ultimateTabLayout.setOnClickTabListener(new OnClickTabListener() {
            @Override
            public void onClickTab(int currentPos) {
                Log.e("LOG", "OnClickTab " + currentPos);
                viewPager.setCurrentItem(currentPos);
            }
        });
        ultimateTabLayout.setViewPager(viewPager, pagerAdapter);


////        TabLayout tabLayout = findViewById(R.id.tabLayout);
//        FlexibleTabLayout ftl = findViewById(R.id.flexibleTabLayout);
//
//        ftl.setInflateTabsDelegate(new Function1<LinearLayout, List<? extends View>>() {
//            @Override
//            public List<? extends View> invoke(LinearLayout linearLayout) {
//
//                List<View> tabs = new ArrayList<>();
//                for(Category category : categories){
//                tabs.add(AppUtil.getTabView(getActivity(), category));}
//
//                return tabs;
//            }
//        });
//
//        ftl.setHasIndicator(false);


//        ViewPager viewPager = findViewById(R.id.pager);
//        viewPager.setAdapter(new ProductViewPagerAdapter(getSupportFragmentManager(), categories));
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                ftl.onPageScrolled(position, positionOffset);
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                ftl.onPageSelected(position);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//            }
//        });
//        ftl.inflateTabs();
//        ftl.inflateIndicator();

//        viewPager.setAdapter(new ProductViewPagerAdapter(getSupportFragmentManager(), categories));
////        viewPager.addOnPageChangeListener(ColorTabLayoutOnPageChangeListener(colorMatchTabLayout))
////        viewPager.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGreen));
////        viewPager.getBackground().setAlpha(128);
//
//        tabLayout.setupWithViewPager(viewPager);
//
//
//        for (int index = 0; index < categories.size(); index++) {
//            Category category = categories.get(index);
////            String tabName = category.getName();
////            int selectedColor = new RandomColor().randomColor();
////            String icon = category.getImage();
//
//            TabLayout.Tab tab = tabLayout.getTabAt(index);
//            tab.setCustomView(AppUtil.getTabView(getActivity(), category));
//        }
    }
}
