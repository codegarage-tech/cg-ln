package com.meembusoft.ln.viewholder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meembusoft.ln.R;
import com.meembusoft.ln.model.Order;
import com.meembusoft.recyclerview.viewholder.BaseViewHolder;

public class OrderViewHolder extends BaseViewHolder<Order> {

    private String TAG = "OrderViewHolder";
    private LinearLayout llOrderTick;
    private TextView tvOrderIdName, tvOrderTimestamp, tvReceiverName, tvShippingAddress, tvTotalPrice, tvCartQuantity, tvOrderStatus;

    public OrderViewHolder(ViewGroup parent) {
        super(parent, R.layout.row_item_order);

        tvOrderIdName = $(R.id.tv_order_id_name);
        tvOrderTimestamp = $(R.id.tv_order_timestamp);
        tvReceiverName = $(R.id.tv_receiver_name);
        tvShippingAddress = $(R.id.tv_shipping_address);
        tvTotalPrice = $(R.id.tv_total_price);
        tvCartQuantity = $(R.id.tv_cart_count);
        tvOrderStatus = $(R.id.tv_order_status);
        llOrderTick = $(R.id.ll_order_tick);
    }

    @Override
    public void setData(final Order data) {
        tvOrderIdName.setText(data.getOrder_id_name());
        tvOrderTimestamp.setText(data.getOrder_timestamp());
        tvReceiverName.setText(data.getReceiver_name());
        tvShippingAddress.setText(data.getShipping_address());
        tvTotalPrice.setText(data.getTotal_price() + "");
        tvCartQuantity.setText(data.getCart_quantity() + "");

        if (data.getOrder_status() == 5) {
            tvOrderStatus.setText("Completed");
            llOrderTick.setVisibility(View.VISIBLE);
        } else {
            tvOrderStatus.setText("Delivery on the way");
            llOrderTick.setVisibility(View.GONE);
        }
    }
}