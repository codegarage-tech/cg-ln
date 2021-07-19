package com.meembusoft.ln.enumeration;

import com.meembusoft.ln.R;
import com.meembusoft.ln.base.BaseApp;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public enum OrderStatusType {

    NONE(0),
    ORDER_PLACED(1),
    ORDER_ACCEPTED(2),
    ORDER_CANCELED(3),
    ORDER_PROCESSING_STARTED(4),
    ORDER_PROCESSING_FINISHED(5),
    SHIPPING_STARTED(6),
    SHIPPING_FINISHED(7),
    PAYMENT_RECEIVED(8),
    ORDER_COMPLETED(9);

    private final int orderStatusType;

    private OrderStatusType(int value) {
        orderStatusType = value;
    }

    public int getOrderStatusType() {
        return this.orderStatusType;
    }

    public static OrderStatusType getOrderStatusType(int orderStatusTypeValue) {
        for (OrderStatusType orderStatusType : values()) {
            if (orderStatusType.getOrderStatusType() == orderStatusTypeValue) {
                return orderStatusType;
            }
        }
        return null;
    }

    public static String getOrderStatusTypeDescription(int orderStatusType) {
        String orderStatusDescription = "";
        if (orderStatusType == NONE.getOrderStatusType()) {
            orderStatusDescription = "";
        } else if (orderStatusType == ORDER_PLACED.getOrderStatusType()) {
            orderStatusDescription = BaseApp.getCurrentActivity().getString(R.string.txt_order_has_been_placed_and_under_review);
        } else if (orderStatusType == ORDER_ACCEPTED.getOrderStatusType()) {
            orderStatusDescription = BaseApp.getCurrentActivity().getString(R.string.txt_order_order_has_been_accepted);
        } else if (orderStatusType == ORDER_CANCELED.getOrderStatusType()) {
            orderStatusDescription = BaseApp.getCurrentActivity().getString(R.string.txt_order_has_been_canceled);
        } else if (orderStatusType == ORDER_PROCESSING_STARTED.getOrderStatusType()) {
            orderStatusDescription = BaseApp.getCurrentActivity().getString(R.string.txt_order_processing_has_been_started);
        } else if (orderStatusType == ORDER_PROCESSING_FINISHED.getOrderStatusType()) {
            orderStatusDescription = BaseApp.getCurrentActivity().getString(R.string.txt_order_processing_has_been_finished);
        } else if (orderStatusType == SHIPPING_STARTED.getOrderStatusType()) {
            orderStatusDescription = BaseApp.getCurrentActivity().getString(R.string.txt_delivery_man_has_started_moving);
        } else if (orderStatusType == SHIPPING_FINISHED.getOrderStatusType()) {
            orderStatusDescription = BaseApp.getCurrentActivity().getString(R.string.txt_delivery_man_has_reached);
        } else if (orderStatusType == PAYMENT_RECEIVED.getOrderStatusType()) {
            orderStatusDescription = BaseApp.getCurrentActivity().getString(R.string.txt_payment_has_been_received_by_delivery_man);
        } else if (orderStatusType == ORDER_COMPLETED.getOrderStatusType()) {
            orderStatusDescription = BaseApp.getCurrentActivity().getString(R.string.txt_order_is_completed);
        }
        return orderStatusDescription;
    }
}