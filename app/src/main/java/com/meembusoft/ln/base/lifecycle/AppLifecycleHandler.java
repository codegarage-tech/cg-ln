package com.meembusoft.ln.base.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import android.os.Bundle;

public class AppLifecycleHandler implements Application.ActivityLifecycleCallbacks, ComponentCallbacks2 {

    private static String TAG = AppLifecycleHandler.class.getSimpleName();

    private boolean appInForeground = false;
    private AppLifecycleListener mAppLifecycleListener;

    public static AppLifecycleHandler getInstance(AppLifecycleListener appLifecycleListener) {
        return new AppLifecycleHandler(appLifecycleListener);
    }

    public AppLifecycleHandler(AppLifecycleListener appLifecycleListener) {
        this.mAppLifecycleListener = appLifecycleListener;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (!appInForeground) {
            appInForeground = true;
            mAppLifecycleListener.onAppForegrounded();
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    @Override
    public void onTrimMemory(int level) {
        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            appInForeground = false;
            mAppLifecycleListener.onAppBackgrounded();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {

    }

    @Override
    public void onLowMemory() {

    }
}
