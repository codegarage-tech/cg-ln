package com.meembusoft.ln.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.meembusoft.ln.model.OrderStatus;
import com.meembusoft.ln.viewholder.OrderDetailStatusViewHolder;
import com.meembusoft.recyclerview.adapter.RecyclerArrayAdapter;
import com.meembusoft.recyclerview.viewholder.BaseViewHolder;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public class OrderDetailStatusAdapter extends RecyclerArrayAdapter<OrderStatus> {

    private static final int VIEW_TYPE_REGULAR = 1;

    public OrderDetailStatusAdapter(Context context) {
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
                return new OrderDetailStatusViewHolder(parent);
            default:
                throw new InvalidParameterException();
        }
    }

//    @Override
//    public void addAll(Collection<? extends OrderStatus> collection) {
//        removeAll();
//        super.addAll(getFurnishedData(collection));
//        notifyDataSetChanged();
//    }

    /**
     * Prepare original data pushing a false data one after another for making bar.
     *
     * @param collection The original status data
     * @return Original data with the false data
     */
    private List<OrderStatus> getFurnishedData(Collection<? extends OrderStatus> collection) {
        List<OrderStatus> mData = new ArrayList<>();
        List<OrderStatus> mAcceptedData = new ArrayList<>();
        List<OrderStatus> tempData = (List<OrderStatus>) collection;

        //Pick only accepted data
        for (OrderStatus orderStatus : tempData) {
            if (orderStatus.getIsFinished() == 1) {
                mAcceptedData.add(orderStatus);
            }
        }

        //Push false data
        for (int i = 0; i < mAcceptedData.size(); i++) {
            mData.add(mAcceptedData.get(i));
            if (i != (mAcceptedData.size() - 1)) {
                mData.add(new OrderStatus());
            }
        }
        return mData;
    }

    public void updateItem(OrderStatus orderStatus) {
        int position = getItemPosition(orderStatus);

        List<OrderStatus> orderStatuses = new ArrayList<>();
        orderStatuses.addAll(getAllData());
        orderStatuses.remove(position);
        orderStatuses.add(position, orderStatus);

        removeAll();
        addAll(orderStatuses);
        notifyDataSetChanged();
    }

    private int getItemPosition(OrderStatus orderStatus) {
        List<OrderStatus> orderStatuses = getAllData();
        for (int i = 0; i < orderStatuses.size(); i++) {
            if (orderStatuses.get(i).getStatus() == orderStatus.getStatus()) {
                return i;
            }
        }
        return -1;
    }
}