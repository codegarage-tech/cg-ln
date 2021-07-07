package com.meembusoft.ln.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.meembusoft.addtocart.model.CartItem;
import com.meembusoft.ln.viewholder.CartViewHolder;
import com.meembusoft.recyclerview.adapter.RecyclerArrayAdapter;
import com.meembusoft.recyclerview.viewholder.BaseViewHolder;

import java.security.InvalidParameterException;

public class CartListAdapter extends RecyclerArrayAdapter<CartItem> {

    private static final int VIEW_TYPE_REGULAR = 1;

    public CartListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getViewType(int position) {
        return VIEW_TYPE_REGULAR;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_REGULAR:
                return new CartViewHolder(parent);
            default:
                throw new InvalidParameterException();
        }
    }
}