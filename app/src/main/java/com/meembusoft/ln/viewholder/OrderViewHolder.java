package com.meembusoft.ln.viewholder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meembusoft.ln.R;
import com.meembusoft.ln.activity.CategoryActivity;
import com.meembusoft.ln.activity.OrdersActivity;
import com.meembusoft.ln.adapter.OrderListAdapter;
import com.meembusoft.ln.adapter.ProductListAdapter;
import com.meembusoft.ln.enumeration.OrderStatusType;
import com.meembusoft.ln.model.Order;
import com.meembusoft.recyclerview.adapter.RecyclerArrayAdapter;
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
        tvTotalPrice.setText(data.getTotal_price() + " " + getContext().getString(R.string.txt_tk));
        tvCartQuantity.setText(data.getCart_quantity() + "");
        tvOrderStatus.setText(OrderStatusType.getOrderStatusTypeDescription(data.getOrder_status()));

        OrderStatusType orderStatusType = OrderStatusType.getOrderStatusType(data.getOrder_status());
        if (orderStatusType == OrderStatusType.ORDER_COMPLETED) {
            llOrderTick.setVisibility(View.VISIBLE);
        } else {
            llOrderTick.setVisibility(View.GONE);
        }

        // Open order detail
        ((OrderListAdapter) getOwnerAdapter()).setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ((OrdersActivity) getContext()).getOrderDetailController().openDetails(null, view.findViewById(R.id.item_view), data);
            }
        });
    }
}