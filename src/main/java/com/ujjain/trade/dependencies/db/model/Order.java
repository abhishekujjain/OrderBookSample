package com.ujjain.trade.dependencies.db.model;

import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "OrderRecord")
@Immutable
public class Order extends BaseEntity {

    private Integer orderedQuantity;
    private Double price;
    private Boolean isMarketOrder;
    private Integer financeIntrumentId;


    public Order() {
    }

    public Order(Integer orderedQuantity, Double price, Boolean isMarketOrder, Integer financeIntrumentId) {
        this.orderedQuantity = orderedQuantity;
        this.price = price;
        this.isMarketOrder = isMarketOrder;
        this.financeIntrumentId = financeIntrumentId;
    }

    public Integer getOrderedQuantity() {
        return orderedQuantity;
    }

    public Double getPrice() {
        return price;
    }

    public Boolean getMarketOrder() {
        return isMarketOrder;
    }

    public Integer getFinanceIntrumentId() {
        return financeIntrumentId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("orderedQuantity=").append(orderedQuantity);
        sb.append(", price=").append(price);
        sb.append(", isMarketOrder=").append(isMarketOrder);
        sb.append(", financeIntrumentId=").append(financeIntrumentId);
        sb.append('}');
        return sb.toString();
    }
}
