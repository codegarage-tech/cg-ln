package com.meembusoft.ln.fragment.filter;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.allattentionhere.fabulousfilter.AAH_FabulousFragment;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.tabs.TabLayout;
import com.meembusoft.ln.R;
import com.meembusoft.ln.enumeration.FilterType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Md. Rashadul Alam
 */
public class ProductFilterFragment extends AAH_FabulousFragment {

    private static ArrayMap<String, List<String>> filterKeys = new ArrayMap<>();
    private ArrayMap<String, List<String>> appliedFilter = new ArrayMap<>();
    private  List<TextView> textViews = new ArrayList<>();
    private TabLayout tabFilterType;
    private ImageButton imgBtnClose, imgBtnRefresh, imgBtnApply;
    private SectionsPagerAdapter mAdapter;
    private static String TAG = ProductFilterFragment.class.getSimpleName();

    public static ProductFilterFragment newInstance(ArrayMap<String, List<String>> data) {
        filterKeys = data;

        Log.d(TAG, "=====================Opening======================");
        for (Map.Entry<String, List<String>> entry : filterKeys.entrySet()) {
            Log.d(TAG, "saved filter key: " + entry.getKey());
            for (String s : entry.getValue()) {
                Log.d(TAG, "saved item: " + s);
            }
        }
        Log.d(TAG, "======================Opening=====================");

        ProductFilterFragment mff = new ProductFilterFragment();

        return mff;
    }

    public void setAppliedFilterKeys(ArrayMap<String, List<String>> selectedFilterKeys){
        appliedFilter = selectedFilterKeys;
    }

    private List<String> getVendorCategoryKey() {
        return filterKeys.get(FilterType.VENDOR.name());
    }

    private List<String> getPriceCategoryKey() {
        return filterKeys.get(FilterType.PRICE.name());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

//    @Override
//    public void onCancel(DialogInterface dialog) {
//        super.onCancel(dialog);
//        clearAllSelectedData();
//    }

    @Override

    public void setupDialog(Dialog dialog, int style) {
        View contentView = View.inflate(getContext(), R.layout.filter_view, null);

        RelativeLayout parentView = (RelativeLayout) contentView.findViewById(R.id.rl_content);
        LinearLayout llBottomBar = (LinearLayout) contentView.findViewById(R.id.ll_buttons);
        imgBtnClose = (ImageButton) contentView.findViewById(R.id.imgbtn_close);
        imgBtnRefresh = (ImageButton) contentView.findViewById(R.id.imgbtn_refresh);
        imgBtnApply = (ImageButton) contentView.findViewById(R.id.imgbtn_apply);
        ViewPager vpFilterType = (ViewPager) contentView.findViewById(R.id.vp_types);
        tabFilterType = (TabLayout) contentView.findViewById(R.id.tabs_types);

        imgBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFilter("closed");
            }
        });

        imgBtnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllSelectedData();
            }
        });

        imgBtnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayMap<String, List<String>> selectedItem = getSelectedData();

                Log.d(TAG, "=====================Saving======================");
                for (Map.Entry<String, List<String>> entry : selectedItem.entrySet()) {
                    Log.d(TAG, "saved filter key: " + entry.getKey());
                    for (String s : entry.getValue()) {
                        Log.d(TAG, "saved item: " + s);
                    }
                }
                Log.d(TAG, "======================Saving=====================");

                closeFilter(selectedItem);
            }
        });

        mAdapter = new SectionsPagerAdapter();
        vpFilterType.setOffscreenPageLimit(4);
        vpFilterType.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        tabFilterType.setupWithViewPager(vpFilterType);

        //params to set
        setAnimationDuration(600); //optional; default 500ms
        setPeekHeight(300); // optional; default 400dp
        setCallbacks((Callbacks) getActivity()); //optional; to get back result
        setAnimationListener((AnimationListener) getActivity()); //optional; to get animation callbacks
        setViewgroupStatic(llBottomBar); // optional; layout to stick at bottom on slide
        setViewPager(vpFilterType); //optional; if you use viewpager that has scrollview
        setViewMain(parentView); //necessary; main bottomsheet view
        setMainContentView(contentView); // necessary; call at end before super
        super.setupDialog(dialog, style); //call super at last
    }

    public class SectionsPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.view_filters_sorters, collection, false);
            FlexboxLayout fbl = (FlexboxLayout) layout.findViewById(R.id.fbl);
//            LinearLayout ll_scroll = (LinearLayout) layout.findViewById(R.id.ll_scroll);
//            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (metrics.heightPixels-(104*metrics.density)));
//            ll_scroll.setLayoutParams(lp);
            switch (FilterType.values()[position]) {
                case VENDOR:
                    inflateLayoutWithFilters(FilterType.VENDOR, fbl);
                    break;
//                case PRICE:
//                    inflateLayoutWithFilters(FilterType.PRICE, fbl);
//                    break;
            }
            collection.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public int getCount() {
            return FilterType.values().length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "    " + FilterType.values()[position].getValue() + "    ";
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    private void inflateLayoutWithFilters(FilterType filterType, FlexboxLayout fbl) {
        List<String> keys = new ArrayList<>();
        switch (filterType) {
            case VENDOR:
                keys = getVendorCategoryKey();
                break;
//            case PRICE:
//                keys = getPriceCategoryKey();
//                break;
        }

        for (int i = 0; i < keys.size(); i++) {
            View subchild = getActivity().getLayoutInflater().inflate(R.layout.single_chip, null);
            final TextView tv = ((TextView) subchild.findViewById(R.id.txt_title));
            tv.setText(keys.get(i));
            final int finalI = i;
            final List<String> finalKeys = keys;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //For multiple selection
                    multiChoiceSelectedMap(tv);

                    //For single selection
//                    singleChoiceSelectedMap(filter_category, tv);
                }
            });
            try {
                Log.d(TAG, "key: " + filterType.name() + " |val:" + keys.get(finalI));
                Log.d(TAG, "applied_filters != null: " + (appliedFilter != null));
                Log.d(TAG, "applied_filters.get(key) != null: " + (appliedFilter.get(filterType.name()) != null));
                Log.d(TAG, "applied_filters.get(key).contains(keys.get(finalI)): " + (appliedFilter.get(filterType.name()).contains(keys.get(finalI))));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (appliedFilter != null && appliedFilter.get(filterType.name()) != null && appliedFilter.get(filterType.name()).contains(keys.get(finalI))) {
                tv.setTag("selected");
                tv.setBackgroundResource(R.drawable.chip_selected);
                tv.setTextColor(ContextCompat.getColor(getContext(), R.color.subtitleTextColor));
            } else {
                tv.setTag("unselected");
                tv.setBackgroundResource(R.drawable.chip_unselected);
                tv.setTextColor(ContextCompat.getColor(getContext(), R.color.paragraphTextColor));
            }
            textViews.add(tv);

            fbl.addView(subchild);
        }
    }

    private ArrayMap<String, List<String>> getSelectedData() {
        ArrayMap<String, List<String>> tempSelectedData = new ArrayMap<>();

        for (int i = 0; i < FilterType.values().length; i++) {
            List<String> keys = new ArrayList<>();
            switch (FilterType.values()[i]) {
                case VENDOR:
                    keys = getVendorCategoryKey();
                    break;
//                case PRICE:
//                    keys = getPriceCategoryKey();
//                    break;
            }

            for (TextView textView : textViews) {
                for (int j = 0; j < keys.size(); j++) {
                    if (textView.getText().toString().equalsIgnoreCase(keys.get(j))) {
                        if (textView.getTag().equals("selected")) {
                            String currentFilterKey = FilterType.values()[i].name();
                            String currentKey = keys.get(j);
                            if (tempSelectedData.get(currentFilterKey) != null && !tempSelectedData.get(currentFilterKey).contains(currentKey)) {
                                tempSelectedData.get(currentFilterKey).add(currentKey);
                            } else {
                                List<String> temp = new ArrayList<>();
                                temp.add(currentKey);
                                tempSelectedData.put(currentFilterKey, temp);
                            }
                        }
                    }
                }
            }
        }

        return tempSelectedData;
    }

    private void multiChoiceSelectedMap(TextView textView) {
        if (textView.getTag().equals("selected")) {
            unSelectTextView(textView);
        } else {
            selectTextView(textView);
        }
    }

    private void singleChoiceSelectedMap(FilterType filterType, TextView textView) {
        if (filterType == FilterType.VENDOR || filterType == FilterType.PRICE) {
            clearAllSelectedData(filterType);
        } else {
            clearAllSelectedDataExceptCurrent(filterType, textView);
        }

        if (textView.getTag().equals("selected")) {
            unSelectTextView(textView);
        } else {
            selectTextView(textView);
        }
    }

    private void selectTextView(TextView textView) {
        textView.setTag("selected");
        textView.setBackgroundResource(R.drawable.chip_selected);
        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.subtitleTextColor));

        updateTextView(textView);
    }

    private void unSelectTextView(TextView textView) {
        textView.setTag("unselected");
        textView.setBackgroundResource(R.drawable.chip_unselected);
        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.paragraphTextColor));

        updateTextView(textView);
    }

    private TextView getTextView(String value) {
        for (TextView tv : textViews) {
            if (tv.getText().toString().equalsIgnoreCase(value)) {
                return tv;
            }
        }
        return null;
    }

    private int getTextViewPosition(String value) {
        for (int i = 0; i < textViews.size(); i++) {
            if (textViews.get(i).getText().toString().equalsIgnoreCase(value)) {
                return i;
            }
        }
        return -1;
    }

    private TextView updateTextView(TextView textView) {
        int position = getTextViewPosition(textView.getText().toString());
        textViews.remove(position);
        textViews.add(position, textView);
        return textViews.get(position);
    }

    private void clearAllSelectedData() {
        for (TextView tv : textViews) {
            tv.setTag("unselected");
            tv.setBackgroundResource(R.drawable.chip_unselected);
            tv.setTextColor(ContextCompat.getColor(getContext(), R.color.paragraphTextColor));
        }
        appliedFilter.clear();
    }

    private void clearAllSelectedDataExceptCurrent(FilterType filterType, TextView textView) {
        List<String> keys = null;
        switch (filterType) {
            case VENDOR:
                keys = getVendorCategoryKey();
                break;
//            case PRICE:
//                keys = getPriceCategoryKey();
//                break;
        }

        List<TextView> tempTextViews = new ArrayList<>(textViews);

        for (TextView mTextView : tempTextViews) {
            for (int i = 0; i < keys.size(); i++) {
                if (mTextView.getText().toString().equalsIgnoreCase(keys.get(i))) {

                    if (!mTextView.getText().toString().equalsIgnoreCase(textView.getText().toString())) {

                        if (mTextView.getTag().equals("selected")) {

                            mTextView.setTag("unselected");
                            mTextView.setBackgroundResource(R.drawable.chip_unselected);
                            mTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.paragraphTextColor));

                            updateTextView(mTextView);
                        }
                    }
                }
            }
        }
    }

    private void clearAllSelectedData(FilterType filterType) {
        List<String> keys = null;
        switch (filterType) {
            case VENDOR:
                keys = getVendorCategoryKey();
                break;
//            case PRICE:
//                keys = getPriceCategoryKey();
//                break;
        }

        List<TextView> tempTextViews = new ArrayList<>(textViews);

        for (TextView textView : tempTextViews) {
            for (int i = 0; i < keys.size(); i++) {
                if (textView.getText().toString().equalsIgnoreCase(keys.get(i))) {
                    if (textView.getTag().equals("selected")) {

                        textView.setTag("unselected");
                        textView.setBackgroundResource(R.drawable.chip_unselected);
                        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.paragraphTextColor));

                        updateTextView(textView);
                    }
                }
            }
        }
    }
}