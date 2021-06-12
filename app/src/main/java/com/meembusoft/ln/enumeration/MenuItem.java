package com.meembusoft.ln.enumeration;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public enum MenuItem {

    HOME(1), FAVORITE(2), CART(3), NOTIFICATION(4), SETTINGS(5);

    private final int value;

    MenuItem(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static MenuItem getMenuItem(int value) {
        for (MenuItem menuItem : MenuItem.values()) {
            if (menuItem.getValue() == value) {
                return menuItem;
            }
        }
        return null;
    }
}