package com.meembusoft.ln.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.claudiodegio.msv.BaseMaterialSearchView;
import com.claudiodegio.msv.OnSearchViewListener;
import com.claudiodegio.msv.SuggestionMaterialSearchView;
import com.meembusoft.ln.R;
import com.meembusoft.ln.adapter.SuggestionListAdapter;
import com.meembusoft.ln.base.BaseActivity;
import com.meembusoft.ln.util.DataUtil;

public class SearchActivity extends BaseActivity {

    // Toolbar
    private TextView tvTitle;
    private LinearLayout llClose;

    private RelativeLayout rlSearchView;
    private RecyclerView mRvItem;
    private BaseMaterialSearchView mMaterialSearchView;

    // Suggestion
    private SuggestionListAdapter mSuggestionListAdapter;

    @Override
    public int initToolbarLayout() {
        return R.layout.toolbar_base;
    }

    @Override
    public int initScreenLayout() {
        return R.layout.activity_search;
    }

    @Override
    public void initIntentData(Bundle savedInstanceState, Intent intent) {

    }

    @Override
    public void initViews() {
        // Toolbar
        tvTitle = findViewById(R.id.tv_title);
        llClose = findViewById(R.id.ll_close);

        rlSearchView = findViewById(R.id.rl_search_item);
        mRvItem = findViewById(R.id.rv_item);
        mMaterialSearchView = findViewById(R.id.sv);
    }

    @Override
    public void initViewsData(Bundle savedInstanceState) {
        // Toolbar
        tvTitle.setText(R.string.txt_search);

        initSuggestion();

        SuggestionMaterialSearchView cast = (SuggestionMaterialSearchView) mMaterialSearchView;
        String[] arrays = getResources().getStringArray(R.array.query_suggestions);
        cast.setSuggestion(arrays, true);
        rlSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMaterialSearchView.showSearch(true);
            }
        });

        mMaterialSearchView.setOnSearchViewListener(new OnSearchViewListener() {
            @Override
            public void onSearchViewShown() {
                rlSearchView.setVisibility(View.GONE);
            }

            @Override
            public void onSearchViewClosed() {
                rlSearchView.setVisibility(View.VISIBLE);
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public void onQueryTextChange(String newText) {

            }
        });
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

    @Override
    public void onAllPermissionsAccepted() {

    }

    /*****************************
     * screen item
     * */
    protected void initSuggestion() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        mRvItem.setLayoutManager(gridLayoutManager);
        mRvItem.setHasFixedSize(true);
        // Initialize adapter
        mSuggestionListAdapter = new SuggestionListAdapter(getActivity());
        mRvItem.setAdapter(mSuggestionListAdapter);
        // Load data
        mSuggestionListAdapter.removeAll();
        mSuggestionListAdapter.addAll(DataUtil.getAllSuggestions(getActivity()));
    }
}