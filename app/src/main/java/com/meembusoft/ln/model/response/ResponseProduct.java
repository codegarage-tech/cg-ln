package com.meembusoft.ln.model.response;

import com.meembusoft.ln.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ResponseProduct {

    private int status = 0;
    private String message = "";
    private List<Product> data = new ArrayList<>();

    public ResponseProduct() {
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

    public List<Product> getData() {
        return data;
    }

    public void setData(List<Product> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseProduct{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}