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
import com.meembusoft.ln.model.User;
import com.meembusoft.ln.util.AppUtil;
import com.meembusoft.ln.util.DataUtil;
import com.meembusoft.ln.util.GlideManager;
import com.meembusoft.ln.util.OnSingleClickListener;
import com.meembusoft.ln.util.SessionUtil;
import com.meembusoft.recyclerview.adapter.RecyclerArrayAdapter;

import static com.meembusoft.ln.util.Constants.INTENT_KEY_CATEGORY;

public class HomeActivity extends BaseActivity {

    // Toolbar
    private LinearLayout llSettings;
    private TextView tvCart;
    private RelativeLayout rlCart;
    private ImageView ivCart, ivUser;
//    private PowerMenu mPowerMenuUser;

    // Screen items
    private RelativeLayout rlSearch;

    // Settings
    private SettingsController mSettingsController;

    // Category
    private RecyclerView rvCategory, rvPopularProduct, rvNewProduct;
    private CategoryListAdapter mCategoryListAdapter;
    private PopularProductListAdapter mPopularProductListAdapter;
    private NewProductListAdapter mNewProductListAdapter;
//    private ActivityResultLauncher<Intent> activityResultLauncherLogin = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if (result.getResultCode() == Activity.RESULT_OK) {
//                        mSettingsController.refreshAccountView();
//                        refreshUserView();
//                    }
//                }
//            });
//    private ActivityResultLauncher<Intent> activityResultLauncherAboutProfile = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if (result.getResultCode() == Activity.RESULT_OK) {
//                        doSignOut();
//                    }
//                }
//            });

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
        ivUser = findViewById(R.id.iv_user);

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

        ivUser.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View var1) {
                User user = SessionUtil.getUser(getActivity());
                if (user != null) {
                    navigateToAboutProfile();
                } else {
                    navigateToLogin();
                }
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
        // Sync user account from settings and user icon of home
        syncUserAccount();
    }

    @Override
    public void onAllPermissionsAccepted() {

    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        switch (requestCode) {
//            case INTENT_KEY_REQUEST_CODE_LOGIN:
//                if (data != null) {
//                    Parcelable parcelable = data.getParcelableExtra("loginResult");
//
//                    if (parcelable != null) {
//                        Logger.d(TAG, TAG_LOGIN + "onActivityResult>>loginResult: " + parcelable);
//                        AlertDialog.Builder(this)
//                                .setMessage(result.toString())
//                                .setTitle("Login result")
//                                .setPositiveButton(android.R.string.ok, null)
//                                .apply {
//                            if (result?.isSuccess == true) {
//                                setNeutralButton("Logout") { _, _ ->
//                                        SocialLogin.logout(
//                                                this@MainActivity,
//                                    SocialLogin.LoginType.GOOGLE
//                                    )
//                                }
//                            }
//                        }
//                        .show()
//                    }
//                }
//                break;
//        }
//    }

    private void refreshUserView() {
        User user = SessionUtil.getUser(getActivity());
        if (user != null) {
            AppUtil.removeViewTint(ivUser);
//            Picasso.get().load(user.getUser_image().getUrl()).into(ivUser);
            GlideManager.setImage(getActivity(), ivUser, user.getUser_image().getUrl(), true);
        } else {
//            ivUser.setImageResource(R.drawable.vector_user);
            GlideManager.setImage(getActivity(), ivUser, R.drawable.vector_user, true);
            AppUtil.applyViewTint(ivUser, R.color.imageTintColor);
        }
    }

    public void navigateToLogin() {
        Intent intentLogin = new Intent(getActivity(), SignInActivity.class);
//        activityResultLauncherLogin.launch(intentLogin);
        startActivity(intentLogin);
    }

    private void navigateToAboutProfile() {
        Intent intentAboutProfile = new Intent(HomeActivity.this, AboutProfileActivity.class);
//        activityResultLauncherAboutProfile.launch(intentAboutProfile);
        startActivity(intentAboutProfile);
    }

//    private void showUserMenu() {
//        OnMenuItemClickListener<PowerMenuItem> onMenuItemClickListener = new OnMenuItemClickListener<PowerMenuItem>() {
//            @Override
//            public void onItemClick(int position, PowerMenuItem item) {
//                if (item.getTitle().toString().equalsIgnoreCase(getString(R.string.txt_about_profile))) {
//                    Intent intentAboutProfile = new Intent(HomeActivity.this, AboutProfileActivity.class);
//                    startActivity(intentAboutProfile);
//                } else if (item.getTitle().toString().equalsIgnoreCase(getString(R.string.txt_sign_out))) {
//                    doSignOut();
//                }
//
//                mPowerMenuUser.setSelectedPosition(position);
//                mPowerMenuUser.dismiss();
//            }
//        };
//        mPowerMenuUser = new PowerMenu.Builder(getActivity())
//                .addItem(new PowerMenuItem(getString(R.string.txt_about_profile), false))
//                .addItem(new PowerMenuItem(getString(R.string.txt_sign_out), false))
//                .setWidth(400)
//                .setHeight(300)
//                .setAnimation(MenuAnimation.DROP_DOWN) // Animation start point (TOP | LEFT).
//                .setMenuRadius(10f) // sets the corner radius.
//                .setMenuShadow(10f) // sets the shadow.
//                .setTextColorResource(R.color.paragraphTextColor)
//                .setTextSize(16)
//                .setTextGravity(Gravity.CENTER)
//                .setTextTypeface(Typeface.create("sans-serif-medium", Typeface.BOLD))
//                .setSelectedTextColorResource(R.color.titleTextColor)
//                .setMenuColorResource(R.color.colorPrimaryDark)
//                .setSelectedMenuColorResource(R.color.colorPrimary)
//                .setOnMenuItemClickListener(onMenuItemClickListener)
//                .build();
//        mPowerMenuUser.showAsDropDown(ivUser);
//    }

    public void doSignOut() {
        SessionUtil.setUser(getActivity(), "");
        refreshUserView();
        mSettingsController.refreshAccountView();
    }

    private void syncUserAccount() {
        mSettingsController.refreshAccountView();
        refreshUserView();
    }
}