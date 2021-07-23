package com.meembusoft.ln.model;

import java.util.ArrayList;
import java.util.List;

public class OrderDetail {

    private String id = "";
    private String order_id_name = "";
    private int current_status = 0;
    private List<OrderStatus> order_status = new ArrayList<>();
    private OrderInformation order_information;
    private List<OrderItem> order_items = new ArrayList<>();

    public OrderDetail() {
    }

    public OrderDetail(String id, String order_id_name, int current_status, List<OrderStatus> order_status, OrderInformation order_information, List<OrderItem> order_items) {
        this.id = id;
        this.order_id_name = order_id_name;
        this.current_status = current_status;
        this.order_status = order_status;
        this.order_information = order_information;
        this.order_items = order_items;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_id_name() {
        return order_id_name;
    }

    public void setOrder_id_name(String order_id_name) {
        this.order_id_name = order_id_name;
    }

    public int getCurrent_status() {
        return current_status;
    }

    public void setCurrent_status(int current_status) {
        this.current_status = current_status;
    }

    public List<OrderStatus> getOrder_status() {
        return order_status;
    }

    public void setOrder_status(List<OrderStatus> order_status) {
        this.order_status = order_status;
    }

    public OrderInformation getOrder_information() {
        return order_information;
    }

    public void setOrder_information(OrderInformation order_information) {
        this.order_information = order_information;
    }

    public List<OrderItem> getOrder_items() {
        return order_items;
    }

    public void setOrder_items(List<OrderItem> order_items) {
        this.order_items = order_items;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id='" + id + '\'' +
                ", order_id_name='" + order_id_name + '\'' +
                ", current_status=" + current_status +
                ", order_status=" + order_status +
                ", order_information=" + order_information +
                ", order_items=" + order_items +
                '}';
    }
}