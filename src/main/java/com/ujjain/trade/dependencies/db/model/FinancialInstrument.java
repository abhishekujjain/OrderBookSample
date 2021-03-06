package com.ujjain.trade.dependencies.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "FinancialInstrument")
public class FinancialInstrument extends BaseEntity {

    @Column(unique = true)
    Integer instrumentId;
    Boolean status;


    public FinancialInstrument() {
    }

    public FinancialInstrument(int instrumentId, boolean status) {
        this.instrumentId = instrumentId;
        this.status = status;
    }

    public Integer getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(Integer instrumentId) {
        this.instrumentId = instrumentId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
