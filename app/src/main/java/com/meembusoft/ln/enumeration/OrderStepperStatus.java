package com.meembusoft.ln.enumeration;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public enum OrderStepperStatus {

    INACTIVE(0),
    COMPLETED(1);

    private final int orderStepperStatus;

    private OrderStepperStatus(int value) {
        orderStepperStatus = value;
    }

    public int getOrderStepperStatus() {
        return this.orderStepperStatus;
    }

    public static OrderStepperStatus getOrderStepperStatus(int value) {
        for (OrderStepperStatus stepperStatus : OrderStepperStatus.values()) {
            if (stepperStatus.getOrderStepperStatus() == value) {
                return stepperStatus;
            }
        }
        return null;
    }
}