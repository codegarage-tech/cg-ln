package com.meembusoft.ln.base.flavor;

import com.meembusoft.ln.BuildConfig;

public enum BuildType {

    RELEASE("release"),
    DEBUG("debug");

    private final String buildType;

    private BuildType(String value) {
        buildType = value;
    }

    public boolean equalsName(String otherName) {
        return buildType.equals(otherName);
    }

    public String getBuildName() {
        return this.buildType;
    }

    public static BuildType getBuildType() {
        for (BuildType buildType : BuildType.values()) {
            if (buildType.getBuildName().equalsIgnoreCase(BuildConfig.BUILD_TYPE)) {
                return buildType;
            }
        }
        return null;
    }
}