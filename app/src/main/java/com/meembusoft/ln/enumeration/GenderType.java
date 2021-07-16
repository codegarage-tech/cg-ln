package com.meembusoft.ln.enumeration;

import com.meembusoft.ln.R;
import com.meembusoft.ln.base.BaseApp;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public enum GenderType {

    MALE(BaseApp.getGlobalContext().getString(R.string.txt_male)), FEMALE(BaseApp.getGlobalContext().getString(R.string.txt_female)), THIRD_GENDER(BaseApp.getGlobalContext().getString(R.string.txt_third_gender));

    private final String genderType;

    GenderType(String value) {
        genderType = value;
    }

    public boolean equalsName(String otherName) {
        return genderType.equals(otherName);
    }

    public String toString() {
        return this.genderType;
    }

    public static GenderType getGenderType(String value) {
        for (GenderType genderType : GenderType.values()) {
            if (genderType.toString().equalsIgnoreCase(value)) {
                return genderType;
            }
        }
        return null;
    }
}