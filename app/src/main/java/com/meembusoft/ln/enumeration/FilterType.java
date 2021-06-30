package com.meembusoft.ln.enumeration;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public enum FilterType {

    VENDOR("Vendor"), PRICE("Price");

    private final String value;

    FilterType(String value) {
        this.value = value;
    }

    public boolean equalsName(String otherName) {
        return value.equals(otherName);
    }

    public String getValue() {
        return this.value;
    }

    public static FilterType getFilterType(String value) {
        for (FilterType filterType : FilterType.values()) {
            if (filterType.getValue().equalsIgnoreCase(value)) {
                return filterType;
            }
        }
        return null;
    }
}