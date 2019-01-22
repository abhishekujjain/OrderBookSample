package com.ujjain.trade.dependencies.db.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class OrderRecieved extends BaseEntity {

    Double price;
    Integer quantity;
    Integer financialId;

    public OrderRecieved() {
    }

    public OrderRecieved(Double price, Integer quantity, Integer financialId) {
        this.price = price;
        this.quantity = quantity;
        this.financialId = financialId;
    }

    public Integer getFinancialId() {
        return financialId;
    }

    public void setFinancialId(Integer financialId) {
        this.financialId = financialId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
