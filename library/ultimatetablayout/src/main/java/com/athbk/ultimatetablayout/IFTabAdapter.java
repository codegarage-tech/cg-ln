package com.athbk.ultimatetablayout;

/**
 * Created by athbk on 8/25/17.
 */

public interface IFTabAdapter<T> {

    String getTitle(int position);

    T getIcon(int position);

    boolean isEnableBadge(int position);
}
