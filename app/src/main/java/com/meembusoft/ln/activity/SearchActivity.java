package com.meembusoft.ln.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.claudiodegio.msv.BaseMaterialSearchView;
import com.claudiodegio.msv.OnSearchViewListener;
import com.claudiodegio.msv.SuggestionMaterialSearchView;
import com.meembusoft.ln.R;
import com.meembusoft.ln.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity {

    // Toolbar
    private TextView tvTitle;
    private LinearLayout llClose;

    private RelativeLayout rlSearchView;
    RecyclerView mRvItem;
    BaseMaterialSearchView mMaterialSearchView;

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

        init();

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
    protected void init() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);

        mRvItem.setLayoutManager(gridLayoutManager);
        mRvItem.setHasFixedSize(true);

        List<String> list = new ArrayList<>();

        list.add("Have");
        list.add("Sodium");
        list.add("Routine");
        list.add("Systematic");
        list.add("Departure");
        list.add("Transparent");
        list.add("Amputate");
        list.add("Dialogue");
        list.add("Uncle");
        list.add("Credit card");
        list.add("Greet");
        list.add("Dollar");

        mRvItem.setAdapter(new SimpleRVAdapter(list));
    }

    public class SimpleRVAdapter extends RecyclerView.Adapter<SimpleRVAdapter.SimpleViewHolder> {
        private List<String> dataSource;

        public SimpleRVAdapter(List<String> dataArgs) {
            dataSource = dataArgs;
        }

        @Override
        public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View view = LayoutInflater.from(SearchActivity.this).inflate(R.layout.row_item_category_circle, parent, false);
            SimpleViewHolder viewHolder = new SimpleViewHolder(view);
            return viewHolder;
        }

        public class SimpleViewHolder extends RecyclerView.ViewHolder {
            public TextView textView;

            public SimpleViewHolder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.tv_item_name);
            }
        }

        @Override
        public void onBindViewHolder(SimpleViewHolder holder, int position) {
            holder.textView.setText(dataSource.get(position));
        }

        @Override
        public int getItemCount() {
            return dataSource.size();
        }
    }
}
