package com.meembusoft.ln.model.colormatchtab.response;

import com.meembusoft.ln.model.colormatchtab.Category;

import java.util.ArrayList;
import java.util.List;

public class ResponseCategory {

    private int status = 0;
    private String message = "";
    private List<Category> data = new ArrayList<>();

    public ResponseCategory() {
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

    public List<Category> getData() {
        return data;
    }

    public void setData(List<Category> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}