package com.meembusoft.ln.model;

import org.parceler.Parcel;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
@Parcel
public class OrderInformation {

    private String receiver_name = "";
    private String receiver_mobile_number = "";
    private String receiver_address = "";
    private String payment_system = "";
    private int subtotal = 0;
    private int shipping_charge = 0;

    public OrderInformation() {
    }

    public OrderInformation(String receiver_name, String receiver_mobile_number, String receiver_address, String payment_system, int subtotal, int shipping_charge) {
        this.receiver_name = receiver_name;
        this.receiver_mobile_number = receiver_mobile_number;
        this.receiver_address = receiver_address;
        this.payment_system = payment_system;
        this.subtotal = subtotal;
        this.shipping_charge = shipping_charge;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getReceiver_mobile_number() {
        return receiver_mobile_number;
    }

    public void setReceiver_mobile_number(String receiver_mobile_number) {
        this.receiver_mobile_number = receiver_mobile_number;
    }

    public String getReceiver_address() {
        return receiver_address;
    }

    public void setReceiver_address(String receiver_address) {
        this.receiver_address = receiver_address;
    }

    public String getPayment_system() {
        return payment_system;
    }

    public void setPayment_system(String payment_system) {
        this.payment_system = payment_system;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }

    public int getShipping_charge() {
        return shipping_charge;
    }

    public void setShipping_charge(int shipping_charge) {
        this.shipping_charge = shipping_charge;
    }

    @Override
    public String toString() {
        return "OrderInformation{" +
                "receiver_name='" + receiver_name + '\'' +
                ", receiver_mobile_number='" + receiver_mobile_number + '\'' +
                ", receiver_address='" + receiver_address + '\'' +
                ", payment_system='" + payment_system + '\'' +
                ", subtotal=" + subtotal +
                ", shipping_charge=" + shipping_charge +
                '}';
    }
}