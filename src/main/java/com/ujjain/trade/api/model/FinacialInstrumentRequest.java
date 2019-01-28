package com.ujjain.trade.api.model;

public class FinacialInstrumentRequest {

    Integer instrumentId;
    Boolean status;

    public FinacialInstrumentRequest() {
    }

    public FinacialInstrumentRequest(Integer instrumentId, Boolean status) {
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
