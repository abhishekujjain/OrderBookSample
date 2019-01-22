package com.ujjain.trade.api.model;


public class ExecuteOrderRequest {
    private Integer quantity;
    private Double price;
    private Integer financeInstrumentId;


    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getFinanceInstrumentId() {
        return financeInstrumentId;
    }

    public void setFinanceInstrumentId(Integer financeInstrumentId) {
        this.financeInstrumentId = financeInstrumentId;
    }
}
