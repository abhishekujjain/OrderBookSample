package com.ujjain.trade.dependencies.db.model;

import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "OrderRecord")
@Immutable
public class OrderModel extends BaseEntity {

    private int quantity;
    private double price;
    private boolean isMarketOrder;
    private boolean isExecuted;
    private boolean isInvalid;
    private int financeIntrumentId;
    private boolean isSell;

    public OrderModel(int quantity, double price, boolean isMarketOrder, int financeIntrumentId, boolean sell) {
        this.quantity = quantity;
        this.price = price;
        this.isMarketOrder = isMarketOrder;
        this.financeIntrumentId = financeIntrumentId;
        this.isSell = sell;
    }

    public OrderModel() {
    }



    public boolean isSell() {
        return isSell;
    }

    public void setSell(boolean sell) {
        isSell = sell;
    }

    public int getFinanceIntrumentId() {
        return financeIntrumentId;
    }

    public void setFinanceIntrumentId(int financeIntrumentId) {
        this.financeIntrumentId = financeIntrumentId;
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

    public boolean isMarketOrder() {
        return isMarketOrder;
    }

    public void setMarketOrder(boolean marketOrder) {
        isMarketOrder = marketOrder;
    }

    public boolean isExecuted() {
        return isExecuted;
    }

    public void setExecuted(boolean executed) {
        isExecuted = executed;
    }

    public boolean isInvalid() {
        return isInvalid;
    }

    public void setInvalid(boolean invalid) {
        isInvalid = invalid;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderModel{");
        sb.append("quantity=").append(quantity);
        sb.append(", price=").append(price);
        sb.append(", isMarketOrder=").append(isMarketOrder);
        sb.append(", isExecuted=").append(isExecuted);
        sb.append(", isInvalid=").append(isInvalid);
        sb.append(", financeIntrumentId=").append(financeIntrumentId);
        sb.append(", isSell=").append(isSell);
        sb.append('}');
        return sb.toString();
    }
}
