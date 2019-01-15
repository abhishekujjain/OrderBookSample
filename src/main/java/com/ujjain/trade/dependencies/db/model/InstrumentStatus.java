package com.ujjain.trade.dependencies.db.model;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "InstrumentStatus")
public class InstrumentStatus extends BaseEntity {

    @Column(unique = true)
    int instrumentId;
    boolean status;


    public InstrumentStatus() {
    }

    public InstrumentStatus(int instrumentId, boolean status) {
        this.instrumentId = instrumentId;
        this.status = status;
    }

    public int getInstrumentId() {
        return instrumentId;
    }

//    public void setInstrumentId(int instrumentId) {
//        this.instrumentId = instrumentId;
//    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
