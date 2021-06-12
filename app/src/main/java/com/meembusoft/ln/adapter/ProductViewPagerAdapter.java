package com.meembusoft.ln.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.athbk.ultimatetablayout.IFTabAdapter;
import com.meembusoft.ln.fragment.ProductListFragment;
import com.meembusoft.ln.model.colormatchtab.Category;

import java.util.ArrayList;
import java.util.List;

public class ProductViewPagerAdapter extends FragmentPagerAdapter implements IFTabAdapter<String> {

    private List<Category> mCategories = new ArrayList<>();

    public ProductViewPagerAdapter(@NonNull FragmentManager fragmentManager, List<Category> categories) {
        super(fragmentManager);
        mCategories = categories;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return new ProductListFragment(mCategories.get(position));
    }

    @Override
    public int getCount() {
        return mCategories.size();
    }

    @Override
    public String getTitle(int position) {
        return mCategories.get(position).getName();
    }

    @Override
    public String getIcon(int position) {
        return mCategories.get(position).getImage();
    }

    @Override
    public boolean isEnableBadge(int position) {
        return false;
    }
}