package com.ujjain.trade.dependencies.db.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ExecutedOrder")
public class ExecutedOrder extends BaseEntity {

    int quantity;
    double price;
    Long orderId;
    boolean isValid;
    int financeInstrument;

    public ExecutedOrder() {
    }

    public ExecutedOrder(int quantity, double price, Long orderId, boolean isValid, int financeInstrument) {
        this.quantity = quantity;
        this.price = price;
        this.orderId = orderId;
        this.isValid = isValid;
        this.financeInstrument = financeInstrument;
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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ExecutedOrder{");
        sb.append("quantity=").append(quantity);
        sb.append(", price=").append(price);
        sb.append(", orderId=").append(orderId);
        sb.append(", isValid=").append(isValid);
        sb.append(", id=").append(id);
         sb.append(", createdOn=").append(createdOn);
        sb.append(", updatedOn=").append(updatedOn);
        sb.append('}');
        return sb.toString();
    }
}
