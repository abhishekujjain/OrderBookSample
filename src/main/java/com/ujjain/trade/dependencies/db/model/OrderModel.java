package com.ujjain.trade.dependencies.db.model;

import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "OrderRecord")
@Immutable
public class OrderModel extends BaseEntity {

    private Integer orderedQuantity;
    private Integer executedQuantity;
    private Double price;
    private Boolean isMarketOrder;
    private Boolean isExecuted;
    private Boolean isInvalid;
    private Integer financeIntrumentId;

    public OrderModel(Integer orderedQuantity, Double price, Integer financeIntrumentId, Boolean isMarketOrder) {
        this.orderedQuantity = orderedQuantity;
        this.price = price;
        this.isMarketOrder = isMarketOrder;
        this.financeIntrumentId = financeIntrumentId;
    }

    public OrderModel() {
    }

    public Integer getOrderedQuantity() {
        return orderedQuantity;
    }

    public void setOrderedQuantity(Integer orderedQuantity) {
        this.orderedQuantity = orderedQuantity;
    }

    public Integer getExecutedQuantity() {
        return executedQuantity;
    }

    public void setExecutedQuantity(Integer executedQuantity) {
        this.executedQuantity = executedQuantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getMarketOrder() {
        return isMarketOrder;
    }

    public void setMarketOrder(Boolean marketOrder) {
        isMarketOrder = marketOrder;
    }

    public Boolean getExecuted() {
        return isExecuted;
    }

    public void setExecuted(Boolean executed) {
        isExecuted = executed;
    }

    public Boolean getInvalid() {
        return isInvalid;
    }

    public void setInvalid(Boolean invalid) {
        isInvalid = invalid;
    }

    public Integer getFinanceIntrumentId() {
        return financeIntrumentId;
    }

    public void setFinanceIntrumentId(Integer financeIntrumentId) {
        this.financeIntrumentId = financeIntrumentId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderModel{");
        sb.append("orderedQuantity=").append(orderedQuantity);
        sb.append(", executedQuantity=").append(executedQuantity);
        sb.append(", price=").append(price);
        sb.append(", isMarketOrder=").append(isMarketOrder);
        sb.append(", isExecuted=").append(isExecuted);
        sb.append(", isInvalid=").append(isInvalid);
        sb.append(", financeIntrumentId=").append(financeIntrumentId);
        sb.append('}');
        return sb.toString();
    }
}
