package com.meembusoft.ln.model.response;

import com.meembusoft.ln.model.OrderDetail;

public class ResponseOrderDetail {

    private int status = 0;
    private String message = "";
    private OrderDetail data;

    public ResponseOrderDetail() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OrderDetail getData() {
        return data;
    }

    public void setData(OrderDetail data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseOrderDetail{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}