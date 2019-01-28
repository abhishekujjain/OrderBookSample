package com.ujjain.trade.dependencies.db.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Orderbook")

public class OrderBook extends BaseEntity {

    private Integer quantity;
    private Double price;
    private Integer financeInstrumentId;
    private Boolean isExecuted;

    public Boolean getExecuted() {
        return isExecuted;
    }

    public void setExecuted(Boolean executed) {
        isExecuted = executed;
    }

    public OrderBook() {
    }


    public OrderBook(Integer quantity, Double price, Integer financeInstrumentId, Boolean isExecuted) {
        this.quantity = quantity;
        this.price = price;
        this.financeInstrumentId = financeInstrumentId;
        this.isExecuted = isExecuted;
    }

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
