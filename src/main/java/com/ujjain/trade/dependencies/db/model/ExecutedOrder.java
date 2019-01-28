package com.ujjain.trade.dependencies.db.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ExecutedOrder")
public class ExecutedOrder extends BaseEntity {

    Integer allocatedQuantity;
    Integer actualQuantity;
    Double price;
    Long orderId;
    Boolean isValid;
    Boolean isMarketOrder;
    Integer financeIntrumentId;

    public ExecutedOrder() {
    }

    public ExecutedOrder(Integer allocatedQuantity, Integer actualQuantity, Double price, Long orderId, Boolean isValid, Boolean isMarketOrder, Integer financeIntrumentId) {
        this.allocatedQuantity = allocatedQuantity;
        this.actualQuantity = actualQuantity;
        this.price = price;
        this.orderId = orderId;
        this.isValid = isValid;
        this.isMarketOrder = isMarketOrder;
        this.financeIntrumentId = financeIntrumentId;
    }

    public Boolean getMarketOrder() {
        return isMarketOrder;
    }

    public void setMarketOrder(Boolean marketOrder) {
        isMarketOrder = marketOrder;
    }

    public Integer getFinanceIntrumentId() {
        return financeIntrumentId;
    }

    public void setFinanceIntrumentId(Integer financeIntrumentId) {
        this.financeIntrumentId = financeIntrumentId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ExecutedOrder{");
        sb.append("allocatedQuantity=").append(allocatedQuantity);
        sb.append(", actualQuantity=").append(actualQuantity);
        sb.append(", price=").append(price);
        sb.append(", orderId=").append(orderId);
        sb.append(", isValid=").append(isValid);
        sb.append(", isMarketOrder=").append(isMarketOrder);
        sb.append(", financeIntrumentId=").append(financeIntrumentId);
        sb.append('}');
        return sb.toString();
    }

    public Integer getAllocatedQuantity() {
        return allocatedQuantity;
    }

    public void setAllocatedQuantity(Integer allocatedQuantity) {
        this.allocatedQuantity = allocatedQuantity;
    }

    public Integer getActualQuantity() {
        return actualQuantity;
    }

    public void setActualQuantity(Integer actualQuantity) {
        this.actualQuantity = actualQuantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public Integer getFinanceInstrument() {
        return financeIntrumentId;
    }

    public void setFinanceInstrument(Integer financeInstrument) {
        this.financeIntrumentId = financeInstrument;
    }
}
