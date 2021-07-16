package com.meembusoft.ln.base.flavor;

import com.meembusoft.ln.BuildConfig;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public enum AppType {

    USER("user"), ADMIN("admin"), DRIVER("driver");

    private final String appType;

    AppType(String value) {
        appType = value;
    }

    public boolean equalsName(String otherName) {
        return appType.equals(otherName);
    }

    public String toString() {
        return this.appType;
    }

    public static AppType getAppType() {
        for (AppType appType : AppType.values()) {
            if (appType.toString().equalsIgnoreCase(BuildConfig.FLAVOR_appType)) {
                return appType;
            }
        }
        return null;
    }
}