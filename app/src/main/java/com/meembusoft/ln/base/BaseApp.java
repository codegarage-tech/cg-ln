package com.meembusoft.ln.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;

import com.meembusoft.addtocart.AddToCartManager;
import com.meembusoft.ln.BuildConfig;
import com.meembusoft.ln.base.flavor.BuildType;
import com.meembusoft.ln.util.Logger;
import com.meembusoft.localemanager.LocaleManager;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public class BaseApp extends Application {

    private static Application mContext;
    private static Activity mActivity;
    private static final String CANARO_EXTRA_BOLD_PATH = "fonts/canaro_extra_bold.otf";
    public static Typeface canaroExtraBold;

    @Override
    public void onCreate() {
        super.onCreate();

        if (mContext == null) {
            mContext = this;
        }

        // Init Locale
        LocaleManager.initialize(mContext);

        //Initialize logger
        new Logger.Builder()
                .isLoggable(BuildType.getBuildType() == BuildType.DEBUG)
                .build();

        //For using vector drawable
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        //Initialize font
        initTypeface();

        //Multidex initialization
        MultiDex.install(mContext);

        // Init add to cart db
        AddToCartManager.initialize(mContext);
    }

    private void initTypeface() {
        canaroExtraBold = Typeface.createFromAsset(getAssets(), CANARO_EXTRA_BOLD_PATH);
    }

    public static Context getGlobalContext() {
        return mContext.getApplicationContext();
    }

    public static Activity getCurrentActivity() {
        return mActivity;
    }

    public static void setCurrentActivity(Activity activity) {
        BaseApp.mActivity = activity;
    }
}