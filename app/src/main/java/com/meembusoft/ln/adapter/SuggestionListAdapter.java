package com.meembusoft.ln.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.meembusoft.ln.model.colormatchtab.Suggestion;
import com.meembusoft.ln.viewholder.SuggestionViewHolder;
import com.meembusoft.recyclerview.adapter.RecyclerArrayAdapter;
import com.meembusoft.recyclerview.viewholder.BaseViewHolder;

import java.security.InvalidParameterException;

public class SuggestionListAdapter extends RecyclerArrayAdapter<Suggestion> {

    private static final int VIEW_TYPE_REGULAR = 1;

    public SuggestionListAdapter(Context context) {
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
                return new SuggestionViewHolder(parent);
            default:
                throw new InvalidParameterException();
        }
    }
}