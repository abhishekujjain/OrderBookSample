package com.ujjain.trade.dependencies.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class ExecutedOrdertable extends BaseEntity {

    @Column(unique = true)
    Double executionPrice;
    Integer allotedQuantity;
    Integer financeIntrumentId;

    public ExecutedOrdertable() {
    }

    public ExecutedOrdertable(Double executionPrice, Integer allotedQuantity, Integer financeIntrumentId) {
        this.executionPrice = executionPrice;
        this.allotedQuantity = allotedQuantity;
        this.financeIntrumentId = financeIntrumentId;
    }

    public Integer getFinanceIntrumentId() {
        return financeIntrumentId;
    }

    public void setFinanceIntrumentId(Integer financeIntrumentId) {
        this.financeIntrumentId = financeIntrumentId;
    }

    public Double getExecutionPrice() {
        return executionPrice;
    }

    public void setExecutionPrice(Double executionPrice) {
        this.executionPrice = executionPrice;
    }

    public Integer getAllotedQuantity() {
        return allotedQuantity;
    }

    public void setAllotedQuantity(Integer allotedQuantity) {
        this.allotedQuantity = allotedQuantity;
    }
}
