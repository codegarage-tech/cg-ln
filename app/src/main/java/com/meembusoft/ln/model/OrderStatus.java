package com.meembusoft.ln.model;

import com.meembusoft.ln.enumeration.OrderStatusType;
import com.meembusoft.ln.enumeration.OrderStepperStatus;

import org.parceler.Parcel;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
@Parcel
public class OrderStatus {

    private int status = OrderStatusType.ORDER_PLACED.getOrderStatusType();
    private String timestamp = "";
    private int isFinished = OrderStepperStatus.INACTIVE.getOrderStepperStatus();

    public OrderStatus() {
    }

    public OrderStatus(int status, String timestamp, int isFinished) {
        this.status = status;
        this.timestamp = timestamp;
        this.isFinished = isFinished;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(int isFinished) {
        this.isFinished = isFinished;
    }

    @Override
    public String toString() {
        return "{" +
                "status=" + status +
                ", dateTime='" + timestamp + '\'' +
                ", isFinished=" + isFinished +
                '}';
    }
}