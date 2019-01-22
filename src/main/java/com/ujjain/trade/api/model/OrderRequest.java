package com.ujjain.trade.api.model;

public class OrderRequest {

    private int quantity;
    private double price;
    private int financeInstrumentId;
    private boolean isMarket;

    public OrderRequest(int quantity, double price, int financeInstrumentId, boolean isMarket) {
        this.quantity = quantity;
        this.price = price;
        this.financeInstrumentId = financeInstrumentId;
        this.isMarket = isMarket;
    }

    public OrderRequest() {
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getFinanceInstrumentId() {
        return financeInstrumentId;
    }

    public void setFinanceInstrumentId(int financeInstrumentId) {
        this.financeInstrumentId = financeInstrumentId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isMarket() {
        return isMarket;
    }

    public void setMarket(boolean market) {
        isMarket = market;
    }
}
