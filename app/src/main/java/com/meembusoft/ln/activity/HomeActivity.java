package com.meembusoft.ln.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meembusoft.ln.R;
import com.meembusoft.ln.adapter.CategoryListAdapter;
import com.meembusoft.ln.adapter.NewProductListAdapter;
import com.meembusoft.ln.adapter.PopularProductListAdapter;
import com.meembusoft.ln.base.BaseActivity;
import com.meembusoft.ln.controller.SettingsController;
import com.meembusoft.ln.interfaces.OnCartResetListener;
import com.meembusoft.ln.util.AppUtil;
import com.meembusoft.ln.util.DataUtil;
import com.meembusoft.recyclerview.adapter.RecyclerArrayAdapter;

import static com.meembusoft.ln.util.Constants.INTENT_KEY_CATEGORY;

public class HomeActivity extends BaseActivity {

    // Toolbar
    private LinearLayout llSettings;
    private TextView tvCart;
    private RelativeLayout rlCart;
    private ImageView ivCart;

    // Screen items
    private RelativeLayout rlSearch;

    // Settings
    private SettingsController mSettingsController;

    // Category
    private RecyclerView rvCategory, rvPopularProduct, rvNewProduct;
    private CategoryListAdapter mCategoryListAdapter;
    private PopularProductListAdapter mPopularProductListAdapter;
    private NewProductListAdapter mNewProductListAdapter;

    @Override
    public int initToolbarLayout() {
        return R.layout.toolbar_home;
    }

    @Override
    public int initScreenLayout() {
        return R.layout.activity_home;
    }

    @Override
    public void initIntentData(Bundle savedInstanceState, Intent intent) {

    }

    @Override
    public void initViews() {
        // Toolbar
        llSettings = findViewById(R.id.ll_settings);
        tvCart = findViewById(R.id.tv_cart);
        rlCart = findViewById(R.id.rl_cart);
        ivCart = findViewById(R.id.iv_cart);

        rlSearch = findViewById(R.id.rl_search);
        rvCategory = findViewById(R.id.rv_category);
        rvPopularProduct = findViewById(R.id.rv_product_popular);
        rvNewProduct = findViewById(R.id.rv_product_new);
    }

    @Override
    public void initViewsData(Bundle savedInstanceState) {
        // Initialize settings
        mSettingsController = new SettingsController(getActivity(), getParentView());

        // Initialize category
        rvCategory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mCategoryListAdapter = new CategoryListAdapter(getActivity());
        mCategoryListAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intentCategory = new Intent(getActivity(), CategoryActivity.class);
                intentCategory.putExtra(INTENT_KEY_CATEGORY, position);
                startActivity(intentCategory);
            }
        });
        rvCategory.setAdapter(mCategoryListAdapter);
        mCategoryListAdapter.addAll(DataUtil.getAllCategoriesWithSubcategories(getActivity()));

        // Initialize popular product
        rvPopularProduct.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mPopularProductListAdapter = new PopularProductListAdapter(getActivity());
        mPopularProductListAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
        rvPopularProduct.setAdapter(mPopularProductListAdapter);
        mPopularProductListAdapter.addAll(DataUtil.getAllProducts(getActivity(), "Popular"));

        // Initialize new product
        rvNewProduct.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mNewProductListAdapter = new NewProductListAdapter(getActivity());
        mNewProductListAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
        rvNewProduct.setAdapter(mNewProductListAdapter);
        mNewProductListAdapter.addAll(DataUtil.getAllProducts(getActivity(), "New"));
    }

    @Override
    public void initActions(Bundle savedInstanceState) {
        llSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSettingsController.openSettings();
            }
        });

        rlCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtil.navigateToCartScreen(getActivity());
            }
        });

        rlSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSearch = new Intent(getActivity(), SearchActivity.class);
                startActivity(intentSearch);
            }
        });
    }

    @Override
    public void initActivityOnResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void initBackPress() {
        if (mSettingsController.closeSettings()) {
            return;
        }
        finish();
    }

    @Override
    public void initDestroyTasks() {

    }

    @Override
    public void onResume() {
        super.onResume();
        //Reset counter view into toolbar
        DataUtil.resetCartCounterView(tvCart, new OnCartResetListener() {
            @Override
            public void onOrderCompleted(boolean isOrderCompleted) {

            }
        });
    }

    @Override
    public void onAllPermissionsAccepted() {

    }
}