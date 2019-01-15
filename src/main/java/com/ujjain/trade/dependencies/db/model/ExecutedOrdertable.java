package com.ujjain.trade.dependencies.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class ExecutedOrdertable extends BaseEntity {

    @Column(unique = true)
    double price;
    int quantity;

    public ExecutedOrdertable() {
    }

    public ExecutedOrdertable(double price, int quantity) {
        this.price = price;
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}
