package com.meembusoft.ln.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.meembusoft.ln.R;

import static android.content.Context.WINDOW_SERVICE;

public class GlobalProgressBar {

    private static String TAG = GlobalProgressBar.class.getSimpleName();
    private Activity mContext;
    private static GlobalProgressBar mGlobalProgressBar;
    private WindowManager mWindowManager;
    private LayoutInflater mLayoutInflater;
    private View mProgressBar;
    private WindowManager.LayoutParams mLayoutParams;

    private GlobalProgressBar(Activity context) {
        mContext = context;
    }

    public static GlobalProgressBar getInstance(Activity activity) {
        if (mGlobalProgressBar == null) {
            mGlobalProgressBar = new GlobalProgressBar(activity);
        }
        return mGlobalProgressBar;
    }

    public void showProgressBar() {
        try {
            mContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mWindowManager == null) {
                        mWindowManager = (WindowManager) mContext.getSystemService(WINDOW_SERVICE);
                    }
                    if (mLayoutInflater == null) {
                        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    }
                    if (mProgressBar == null) {
                        mProgressBar = mLayoutInflater.inflate(R.layout.layout_global_progress, null);
                        mProgressBar.setOnTouchListener(null);
                        mProgressBar.setClickable(false);
                    }
                    if (mLayoutParams == null) {
                        mLayoutParams = prepareLayoutParam();
                    }
                    mWindowManager.addView(mProgressBar, mLayoutParams);
                }
            });
        } catch (Exception ex) {
            Log.d(TAG, "Exception: " + ex.getMessage());
        }
    }

    private WindowManager.LayoutParams prepareLayoutParam() {
        WindowManager.LayoutParams windowLayoutParams = new WindowManager.LayoutParams();
        windowLayoutParams.gravity = Gravity.CENTER;
        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }
        windowLayoutParams.type = LAYOUT_FLAG;
        windowLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        windowLayoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        windowLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        windowLayoutParams.format = PixelFormat.TRANSPARENT;
        return windowLayoutParams;
    }

    public void dismissProgressBar() {
        try {
            mContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mWindowManager != null && mProgressBar != null) {
                        mWindowManager.removeView(mProgressBar);
                        mWindowManager = null;
                        mProgressBar = null;
                        mLayoutInflater = null;
                        mLayoutParams = null;
                    }
                }
            });
        } catch (Exception ex) {
            Log.d(TAG, "Exception: " + ex.getMessage());
        }
    }
}