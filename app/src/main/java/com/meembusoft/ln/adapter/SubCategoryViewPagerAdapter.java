package com.meembusoft.ln.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.meembusoft.ln.fragment.SubCategoryFragment;
import com.meembusoft.ln.model.colormatchtab.Subcategory;

import java.util.ArrayList;
import java.util.List;

public class SubCategoryViewPagerAdapter extends FragmentPagerAdapter {

    private List<Subcategory> mSubcategories = new ArrayList<>();

    public SubCategoryViewPagerAdapter(@NonNull FragmentManager fragmentManager, List<Subcategory> subcategories) {
        super(fragmentManager);
        mSubcategories = subcategories;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return new SubCategoryFragment(mSubcategories.get(position));
    }

    @Override
    public int getCount() {
        return mSubcategories.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mSubcategories.get(position).getName();
    }
}