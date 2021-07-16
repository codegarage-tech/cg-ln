package com.meembusoft.ln.enumeration;

import com.meembusoft.ln.R;
import com.meembusoft.ln.base.BaseApp;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public enum GenderType {

    NONE(0), MALE(1), FEMALE(2), THIRD_GENDER(3);

    private final int genderType;

    GenderType(int value) {
        genderType = value;
    }

    public int getGenderType() {
        return genderType;
    }

    public static String getGenderName(int genderType) {
        String genderName = "";
        if (genderType == NONE.getGenderType()) {
            genderName = BaseApp.getCurrentActivity().getString(R.string.txt_gender);
        } else if (genderType == MALE.getGenderType()) {
            genderName = BaseApp.getCurrentActivity().getString(R.string.txt_male);
        } else if (genderType == FEMALE.getGenderType()) {
            genderName = BaseApp.getCurrentActivity().getString(R.string.txt_female);
        } else if (genderType == THIRD_GENDER.getGenderType()) {
            genderName = BaseApp.getCurrentActivity().getString(R.string.txt_third_gender);
        }
        return genderName;
    }
}