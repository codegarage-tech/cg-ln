package com.meembusoft.ln.util;

import android.content.Context;
import android.text.TextUtils;

import com.meembusoft.ln.model.colormatchtab.User;
import com.meembusoft.retrofitmanager.APIResponse;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public class SessionUtil {

    // Session key
    private static final String SESSION_KEY_USER = "SESSION_KEY_USER";

    public static User getUser(Context context) {
        User mUser = null;
        String appUser = SessionManager.getStringSetting(context, SESSION_KEY_USER, "");
        if (!TextUtils.isEmpty(appUser)) {
            mUser = APIResponse.getObjectFromJSONString(appUser, User.class);
        }
        return mUser;
    }

    public static void setUser(Context context, String jsonValue) {
        SessionManager.setStringSetting(context, SESSION_KEY_USER, jsonValue);
    }
}