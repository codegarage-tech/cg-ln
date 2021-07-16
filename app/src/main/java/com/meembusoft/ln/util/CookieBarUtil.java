package com.meembusoft.ln.util;

import android.app.Activity;

import com.meembusoft.ln.R;

import org.aviran.cookiebar2.CookieBar;
import org.aviran.cookiebar2.OnActionClickListener;

public class CookieBarUtil {

    public static void showCookieBarWarning(Activity activity, String title, String message) {
        CookieBar.build(activity)
                .setTitle(title)
                .setMessage(message)
                .setIcon(R.drawable.vector_cookie_warning)
                .setIconAnimation(R.animator.iconspin)
                .setTitleColor(R.color.titleTextColor)
                .setActionColor(R.color.blueLight)
                .setMessageColor(R.color.subtitleTextColor)
                .setBackgroundColor(R.color.screenBgColor)
                .setEnableAutoDismiss(false)
                .setSwipeToDismiss(true)
                .setCookiePosition(CookieBar.BOTTOM)
                .setAction(activity.getString(R.string.txt_ok).toUpperCase(), new OnActionClickListener() {
                    @Override
                    public void onClick() {
                    }
                })
                .show();
    }

    public static void showCookieBarError(Activity activity, String title, String message) {
        CookieBar.build(activity)
                .setTitle(title)
                .setMessage(message)
                .setIcon(R.drawable.vector_cookie_warning)
                .setIconAnimation(R.animator.iconspin)
                .setTitleColor(R.color.titleTextColor)
                .setActionColor(R.color.blueLight)
                .setMessageColor(R.color.subtitleTextColor)
                .setBackgroundColor(R.color.screenBgColor)
                .setEnableAutoDismiss(false)
                .setSwipeToDismiss(true)
                .setCookiePosition(CookieBar.BOTTOM)
                .setAction(activity.getString(R.string.txt_ok).toUpperCase(), new OnActionClickListener() {
                    @Override
                    public void onClick() {
                    }
                })
                .show();
    }
}