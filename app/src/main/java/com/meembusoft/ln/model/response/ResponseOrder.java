package com.meembusoft.ln.model.response;

import com.meembusoft.ln.model.Order;

import java.util.ArrayList;
import java.util.List;

public class ResponseOrder {

    private int status = 0;
    private String message = "";
    private List<Order> data = new ArrayList<>();

    public ResponseOrder() {
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

    public List<Order> getData() {
        return data;
    }

    public void setData(List<Order> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseOrder{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}