package com.meembusoft.ln.base;

import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/*
 Extension of FragmentStatePagerAdapter which intelligently caches
 all active fragments and manages the fragment lifecycles.
 Usage involves extending from SmartFragmentStatePagerAdapter as you would any other PagerAdapter.
 */
public abstract class BaseFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    // Sparse array to keep track of registered fragments in memory
    private SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

    public BaseFragmentStatePagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    // Register the fragment when the item is instantiated
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);

        return fragment;
    }

    // Unregister when the item is inactive
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    // Returns the fragment for the position (if instantiated)
    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }

    //Returns all registered fragments
    public List<Fragment> getAllRegisteredFragments() {
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < registeredFragments.size(); i++) {
            fragments.add(registeredFragments.valueAt(i));
        }
        return fragments;
    }
}