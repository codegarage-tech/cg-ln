package com.meembusoft.ln.util;

import android.app.Activity;

import com.meembusoft.ln.R;

import org.aviran.cookiebar2.CookieBar;
import org.aviran.cookiebar2.OnActionClickListener;

public class CookieBarUtil {

    public static void showCookieBarWarning(Activity activity, String message) {
        CookieBar.build(activity)
                .setTitle(activity.getString(R.string.txt_warning))
                .setMessage(message)
                .setIcon(R.drawable.vector_cookie_warning)
                .setIconAnimation(R.animator.iconspin)
                .setTitleColor(R.color.titleTextColor)
                .setActionColor(R.color.blueLight)
                .setMessageColor(R.color.subtitleTextColor)
                .setBackgroundColor(R.color.screenBgColor)
                .setTitleTextSize(18)
                .setMessageTextSize(16)
                .setActionTextSize(20)
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

    public static void showCookieBarOffer(Activity activity, String message) {
        CookieBar.build(activity)
                .setTitle(activity.getString(R.string.txt_offer))
                .setMessage(message)
                .setIcon(R.drawable.vector_cookie_offer)
                .setIconAnimation(R.animator.iconspin)
                .setTitleColor(R.color.titleTextColor)
                .setActionColor(R.color.blueLight)
                .setMessageColor(R.color.subtitleTextColor)
                .setBackgroundColor(R.color.screenBgColor)
                .setTitleTextSize(18)
                .setMessageTextSize(16)
                .setActionTextSize(20)
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