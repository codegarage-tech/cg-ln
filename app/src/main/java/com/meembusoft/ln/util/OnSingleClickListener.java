package com.meembusoft.ln.util;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public abstract class OnSingleClickListener implements OnClickListener {

    private static final String TAG = OnSingleClickListener.class.getSimpleName();
    private static final long MIN_DELAY_MS = 1500L;
    private long mLastClickTime;

    public OnSingleClickListener() {
    }

    public final void onClick(View v) {
        long lastClickTime = this.mLastClickTime;
        long now = System.currentTimeMillis();
        this.mLastClickTime = now;
        if (now - lastClickTime < 1500L) {
            Log.d(TAG, "onClick Clicked too quickly: ignored");
        } else {
            this.onSingleClick(v);
        }
    }

    public abstract void onSingleClick(View var1);

    public static OnClickListener wrap(final OnClickListener onClickListener) {
        return new OnSingleClickListener() {
            public void onSingleClick(View v) {
                onClickListener.onClick(v);
            }
        };
    }
}