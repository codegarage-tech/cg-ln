package com.meembusoft.ln.enumeration;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public enum GenderType {

    MALE("Male"), FEMALE("Female"), THIRD_GENDER("Third Gender");

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