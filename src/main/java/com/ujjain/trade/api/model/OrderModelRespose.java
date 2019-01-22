package com.ujjain.trade.api.model;

import com.ujjain.trade.dependencies.db.model.ExecutedOrder;
import com.ujjain.trade.dependencies.db.model.OrderModel;

import java.util.HashMap;

public class OrderModelRespose {

    Integer numberofOrder;
    Integer quantityDemanded;
    Integer quantityDemandedValid;
    Integer quantityDemandedInvalid;
    Integer biggestOrder;
    Integer smallestOrder;
    OrderModel earlierOrderEntry;
    OrderModel lastOrderEntry;
    Integer marketOrderQuantity;
    HashMap<Double, Integer> executionQuantityWithPrice;
    HashMap<Double, Integer> requestedOrderPrice;
    HashMap<Integer, Integer> OrderBookWiseWise;
    ExecutedOrder earlierOrderEntryExecuted;
    ExecutedOrder lastOrderEntryExecuted;

    public Integer getMarketOrderQuantity() {
        return marketOrderQuantity;
    }

    public void setMarketOrderQuantity(Integer marketOrderQuantity) {
        this.marketOrderQuantity = marketOrderQuantity;
    }

    public HashMap<Integer, Integer> getOrderBookWiseWise() {
        return OrderBookWiseWise;
    }

    public void setOrderBookWiseWise(HashMap<Integer, Integer> orderBookWiseWise) {
        OrderBookWiseWise = orderBookWiseWise;
    }

    public HashMap<Double, Integer> getExecutionQuantityWithPrice() {
        return executionQuantityWithPrice;
    }

    public void setExecutionQuantityWithPrice(HashMap<Double, Integer> executionQuantityWithPrice) {
        this.executionQuantityWithPrice = executionQuantityWithPrice;
    }

    public HashMap<Double, Integer> getRequestedOrderPrice() {
        return requestedOrderPrice;
    }

    public void setRequestedOrderPrice(HashMap<Double, Integer> requestedOrderPrice) {
        this.requestedOrderPrice = requestedOrderPrice;
    }

    public HashMap<Double, Integer> getExecutionQuntityWithPrice() {
        return executionQuantityWithPrice;
    }

    public void setExecutionQuntityWithPrice(HashMap<Double, Integer> executionQuntityWithPrice) {
        this.executionQuantityWithPrice = executionQuntityWithPrice;
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

    public OrderModel getEarlierOrderEntry() {
        return earlierOrderEntry;
    }

    public void setEarlierOrderEntry(OrderModel earlierOrderEntry) {
        this.earlierOrderEntry = earlierOrderEntry;
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

    public OrderModel getLastOrderEntry() {
        return lastOrderEntry;
    }

    public void setLastOrderEntry(OrderModel lastOrderEntry) {
        this.lastOrderEntry = lastOrderEntry;
    }
}
