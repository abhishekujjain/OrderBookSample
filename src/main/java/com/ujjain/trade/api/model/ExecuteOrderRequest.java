package com.ujjain.trade.api.model;


public class ExecuteOrderRequest {
    private int quantity;
    private double price;
    private int financeInstrumentId;


    public int getFinanceInstrumentId() {
        return financeInstrumentId;
    }

    public void setFinanceInstrumentId(int financeInstrumentId) {
        this.financeInstrumentId = financeInstrumentId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
