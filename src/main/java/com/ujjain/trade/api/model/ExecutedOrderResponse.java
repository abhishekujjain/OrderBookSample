package com.ujjain.trade.api.model;

import com.ujjain.trade.dependencies.db.model.ExecutedOrder;

import java.util.HashMap;

public class ExecutedOrderResponse {

    Integer numberofOrder;
    Integer quantityDemanded;
    Integer quantityDemandedValid;
    Integer quantityDemandedInvalid;
    Integer biggestOrder;
    Integer smallestOrder;
    HashMap<Double, Integer> requestedOrderPriceForLimitOrder;
    HashMap<Integer, Integer> OrderBookWise;
    ExecutedOrder earlierOrderEntryExecuted;
    ExecutedOrder lastOrderEntryExecuted;
    HashMap<Double, Integer> executionQuantityWithPrice;

    public HashMap<Double, Integer> getExecutionQuantityWithPrice() {
        return executionQuantityWithPrice;
    }

    public void setExecutionQuantityWithPrice(HashMap<Double, Integer> executionQuantityWithPrice) {
        this.executionQuantityWithPrice = executionQuantityWithPrice;
    }

    public HashMap<Integer, Integer> getOrderBookWiseWise() {
        return OrderBookWise;
    }

    public void setOrderBookWiseWise(HashMap<Integer, Integer> orderBookWiseWise) {
        OrderBookWise = orderBookWiseWise;
    }



    public HashMap<Double, Integer> getRequestedOrderPrice() {
        return requestedOrderPriceForLimitOrder;
    }

    public void setRequestedOrderPrice(HashMap<Double, Integer> requestedOrderPrice) {
        this.requestedOrderPriceForLimitOrder = requestedOrderPrice;
    }



    public ExecutedOrder getEarlierOrderEntryExecuted() {
        return earlierOrderEntryExecuted;
    }

    public void setEarlierOrderEntryExecuted(ExecutedOrder earlierOrderEntryExecuted) {
        this.earlierOrderEntryExecuted = earlierOrderEntryExecuted;
    }

    public ExecutedOrder getLastOrderEntryExecuted() {
        return lastOrderEntryExecuted;
    }

    public void setLastOrderEntryExecuted(ExecutedOrder lastOrderEntryExecuted) {
        this.lastOrderEntryExecuted = lastOrderEntryExecuted;
    }

    public Integer getNumberofOrder() {
        return numberofOrder;
    }

    public void setNumberofOrder(Integer numberofOrder) {
        this.numberofOrder = numberofOrder;
    }

    public Integer getQuantityDemanded() {
        return quantityDemanded;
    }

    public void setQuantityDemanded(Integer quantityDemanded) {
        this.quantityDemanded = quantityDemanded;
    }

    public Integer getBiggestOrder() {
        return biggestOrder;
    }

    public void setBiggestOrder(Integer biggestOrder) {
        this.biggestOrder = biggestOrder;
    }

    public Integer getSmallestOrder() {
        return smallestOrder;
    }

    public void setSmallestOrder(Integer smallestOrder) {
        this.smallestOrder = smallestOrder;
    }


    public Integer getQuantityDemandedValid() {
        return quantityDemandedValid;
    }

    public void setQuantityDemandedValid(Integer quantityDemandedValid) {
        this.quantityDemandedValid = quantityDemandedValid;
    }

    public Integer getQuantityDemandedInvalid() {
        return quantityDemandedInvalid;
    }

    public void setQuantityDemandedInvalid(Integer quantityDemandedInvalid) {
        this.quantityDemandedInvalid = quantityDemandedInvalid;
    }


}
