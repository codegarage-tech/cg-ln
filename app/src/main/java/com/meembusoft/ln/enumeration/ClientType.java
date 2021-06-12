package com.meembusoft.ln.enumeration;

import com.meembusoft.ln.BuildConfig;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public enum ClientType {

    BD("bd"), RASHED_SHOP("rashedshop");

    private final String clientType;

    ClientType(String value) {
        clientType = value;
    }

    public boolean equalsName(String otherName) {
        return clientType.equals(otherName);
    }

    public String toString() {
        return this.clientType;
    }

    public static ClientType getClientType() {
        for (ClientType clientType : ClientType.values()) {
            if (clientType.toString().equalsIgnoreCase(BuildConfig.FLAVOR_clientType)) {
                return clientType;
            }
        }
        return null;
    }
}