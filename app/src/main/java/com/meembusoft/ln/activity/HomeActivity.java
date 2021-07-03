package com.meembusoft.ln.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meembusoft.ln.R;
import com.meembusoft.ln.adapter.CategoryListAdapter;
import com.meembusoft.ln.base.BaseActivity;
import com.meembusoft.ln.enumeration.Language;
import com.meembusoft.ln.model.colormatchtab.Category;
import com.meembusoft.ln.util.DataUtil;
import com.meembusoft.localemanager.LocaleManager;
import com.meembusoft.localemanager.languagesupport.LanguagesSupport;
import com.meembusoft.recyclerview.adapter.RecyclerArrayAdapter;
import com.skydoves.flourish.Flourish;
import com.skydoves.flourish.FlourishAnimation;
import com.skydoves.flourish.FlourishOrientation;
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;

import org.parceler.Parcels;

import static com.meembusoft.ln.util.Constants.INTENT_KEY_CATEGORY;

public class HomeActivity extends BaseActivity {

    // Toolbar
    private LinearLayout llSettings;

    // Screen items
    private RelativeLayout rlSearch;

    // Menu
    private Flourish mFlourish;
    private PowerMenu mPowerMenu;
    private String mSelectedAppLanguageCode = "";
    private TextView tvAppLanguage;

    // Category
    private RecyclerView rvCategory;
    private CategoryListAdapter mCategoryListAdapter;

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

        rlSearch = findViewById(R.id.rl_search);
        rvCategory = findViewById(R.id.rv_category);
    }

    @Override
    public void initViewsData(Bundle savedInstanceState) {
        // Initialize menu
        initMenu();

        // Initialize category
        rvCategory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mCategoryListAdapter = new CategoryListAdapter(getActivity());
        mCategoryListAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Category category = mCategoryListAdapter.getItem(position);
//                Log.d(TAG, "category: " + category.toString());
                Intent intentCategory = new Intent(getActivity(), CategoryActivity.class);
                intentCategory.putExtra(INTENT_KEY_CATEGORY, position);
                startActivity(intentCategory);
            }
        });
        rvCategory.setAdapter(mCategoryListAdapter);
        mCategoryListAdapter.addAll(DataUtil.getAllCategoriesWithSubcategories(getActivity()));
    }

    @Override
    public void initActions(Bundle savedInstanceState) {
        llSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFlourish.show();
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
        if (mPowerMenu != null && mPowerMenu.isShowing()) {
            mPowerMenu.dismiss();
            return;
        }
        if (mFlourish != null && mFlourish.isShowing()) {
            mFlourish.dismiss();
            return;
        }
        finish();
    }

    @Override
    public void initDestroyTasks() {

    }

    /****************
     * Menu methods *
     ****************/
    private void initMenu() {
        // Set menu view
        mFlourish = new Flourish.Builder(getParentView())
                .setFlourishLayout(R.layout.layout_settings)
                .setFlourishAnimation(FlourishAnimation.BOUNCE)
                .setFlourishOrientation(FlourishOrientation.TOP_RIGHT)
                //.setIsShowedOnStart(false)
                .setDuration(800L)
                .build();

        // Set title
        ((TextView) mFlourish.flourishView.findViewById(R.id.tv_title)).setText(R.string.txt_settings);

        mFlourish.flourishView.findViewById(R.id.ll_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFlourish.dismiss();
            }
        });

        mFlourish.flourishView.findViewById(R.id.rl_app_language).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAppLanguages();
            }
        });

        // Set app selected language for the very first time when the menu is initialized
        mSelectedAppLanguageCode = LocaleManager.getSelectedLanguageId(getActivity());
        tvAppLanguage = mFlourish.flourishView.findViewById(R.id.tv_app_language);
        tvAppLanguage.setText(Language.getLanguage(mSelectedAppLanguageCode).getLabel(getActivity()));
    }

    private void showAppLanguages() {
        OnMenuItemClickListener<PowerMenuItem> onMenuItemClickListener = new OnMenuItemClickListener<PowerMenuItem>() {
            @Override
            public void onItemClick(int position, PowerMenuItem item) {
                for (Language language : Language.values()) {
                    if (language.getLabel(getActivity()).equalsIgnoreCase(item.getTitle().toString())) {
                        if (!mSelectedAppLanguageCode.equalsIgnoreCase(language.getLanguageCode())) {
                            LocaleManager.setLocale(getActivity(), language.getLanguageCode());
                            mSelectedAppLanguageCode = language.getLanguageCode();
                            tvAppLanguage.setText(language.getLabel(getActivity()));
                        }
                    }
                }

                mPowerMenu.setSelectedPosition(position);
                mPowerMenu.dismiss();
            }
        };
        mPowerMenu = new PowerMenu.Builder(getActivity())
                .addItem(new PowerMenuItem(Language.ENGLISH.getLabel(getActivity()), mSelectedAppLanguageCode.equalsIgnoreCase(LanguagesSupport.Language.ENGLISH)))
                .addItem(new PowerMenuItem(Language.BENGALI.getLabel(getActivity()), mSelectedAppLanguageCode.equalsIgnoreCase(LanguagesSupport.Language.BENGALI)))
                .setWidth(450)
                .setHeight(400)
                .setAnimation(MenuAnimation.SHOWUP_TOP_LEFT) // Animation start point (TOP | LEFT).
                .setMenuRadius(10f) // sets the corner radius.
                .setMenuShadow(10f) // sets the shadow.
                .setTextColorResource(R.color.paragraphTextColor)
                .setTextSize(16)
                .setTextGravity(Gravity.CENTER)
                .setTextTypeface(Typeface.create("sans-serif-medium", Typeface.BOLD))
                .setSelectedTextColorResource(R.color.titleTextColor)
                .setMenuColorResource(R.color.colorPrimary)
                .setSelectedMenuColorResource(R.color.yellow90)
                .setOnMenuItemClickListener(onMenuItemClickListener)
                .build();
        mPowerMenu.showAtCenter(getParentView());
    }
}
