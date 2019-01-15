package com.ujjain.trade.dependencies.db.model;

import javax.persistence.Entity;
import javax.persistence.Table;

//@Entity
//@Table
public class OrderBookStatus {
    private boolean isOrderOpen;

    public OrderBookStatus() {
    }

    public OrderBookStatus(boolean isOrderOpen) {
        this.isOrderOpen = isOrderOpen;
    }

    public boolean isOrderOpen() {
        return isOrderOpen;
    }

    public void setOrderOpen(boolean orderOpen) {
        isOrderOpen = orderOpen;
    }
}
