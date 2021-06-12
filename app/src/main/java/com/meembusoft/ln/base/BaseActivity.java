package com.meembusoft.ln.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.meembusoft.ln.R;
import com.meembusoft.ln.util.AppUtil;
import com.meembusoft.ln.util.Logger;
import com.meembusoft.localemanager.LocaleManager;
import com.simmorsal.recolor_project.ReColor;

import java.util.List;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public abstract class BaseActivity extends AppCompatActivity {

    private BaseActivity mActivity;
    public Bundle mSavedInstanceState;
    public String TAG;
    public ProgressDialog mProgressDialog;
    private View mToolbarView, mScreenView;
    private ViewGroup mParentView;

    //Abstract declaration
    public abstract int initToolbarLayout();

    public abstract int initScreenLayout();

    public abstract void initIntentData(Bundle savedInstanceState, Intent intent);

    public abstract void initViews();

    public abstract void initViewsData(Bundle savedInstanceState);

    public abstract void initActions(Bundle savedInstanceState);

    public abstract void initActivityOnResult(int requestCode, int resultCode, Intent data);

    public abstract void initBackPress();

    public abstract void initDestroyTasks();

    public BaseActivity() {
        this.mActivity = this;
        this.TAG = getClass().getSimpleName();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Locale manager
        LocaleManager.initialize(getActivity());

        // Inflate views
        mToolbarView = LayoutInflater.from(getActivity()).inflate(initToolbarLayout(), null);
        mScreenView = LayoutInflater.from(getActivity()).inflate(initScreenLayout(), null);

        // Set status bar and navigation bar color
        new ReColor(getActivity()).setStatusBarColor(AppUtil.getColor(getActivity(), R.color.screenBgColor), AppUtil.getColor(getActivity(), R.color.screenBgColor), 1000);
        new ReColor(getActivity()).setNavigationBarColor(AppUtil.getColor(getActivity(), R.color.screenBgColor), AppUtil.getColor(getActivity(), R.color.screenBgColor), 1000);

        mSavedInstanceState = savedInstanceState;
        initIntentData(mSavedInstanceState, getIntent());
        setContentView(R.layout.activity_base);

        // Add toolbar and screen views
        mParentView = findViewById(R.id.ll_parent);
        ViewGroup mToolbar = findViewById(R.id.ll_toolbar);
        mToolbar.addView(mToolbarView);
        ViewGroup mScreen = findViewById(R.id.ll_container);
        mScreen.addView(mScreenView);

        initViews();
        initViewsData(mSavedInstanceState);
        initActions(mSavedInstanceState);
    }

    public Activity getActivity() {
        return mActivity;
    }

    public ViewGroup getParentView() {
        return mParentView;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        try {
            super.onRestoreInstanceState(savedInstanceState);

            mSavedInstanceState = savedInstanceState;
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.d(TAG, "onRestoreInstanceState: " + ex.getMessage());
        }
    }

    /********************
     * Fragment methods *
     ********************/
    protected void initFragment(int containerViewId, BaseFragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment, tag).commitAllowingStateLoss();
    }

    protected void addFragment(int containerViewId, BaseFragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment).commitAllowingStateLoss();
    }

    protected <F extends BaseFragment> void addFragment(int containerViewId, Class<F> fragmentClazz) {
        F frg = createFragment(fragmentClazz, getIntent().getExtras());
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, frg).commitAllowingStateLoss();
    }

    public static <T extends BaseFragment> T createFragment(Class<T> fragmentClazz, Bundle args) {
        T fragment = null;
        try {
            fragment = fragmentClazz.newInstance();
            fragment.setArguments(args);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        initActivityOnResult(requestCode, resultCode, data);

        //send on activity result event to the fragment
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        if (fragmentList != null) {
            for (Fragment fragment : fragmentList) {
                if (fragment instanceof BaseFragment) {
                    ((BaseFragment) fragment).onFragmentResult(requestCode, resultCode, data);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        initBackPress();

        //send back press event to the fragment
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        if (fragmentList != null) {
            for (Fragment fragment : fragmentList) {
                if (fragment instanceof BaseFragment) {
                    ((BaseFragment) fragment).onFragmentBackPressed();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        initDestroyTasks();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /***************************
     * Progress dialog methods *
     ***************************/
    public ProgressDialog showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage(getResources().getString(R.string.progress_dialog_loading));
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface arg0) {
                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                        mProgressDialog = null;
                    }
                }
            });
        }

        if (mProgressDialog != null && !mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }

        return mProgressDialog;
    }

    public void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
}