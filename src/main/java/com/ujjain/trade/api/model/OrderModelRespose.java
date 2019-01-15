package com.ujjain.trade.api.model;

import com.ujjain.trade.dependencies.db.model.ExecutedOrder;
import com.ujjain.trade.dependencies.db.model.OrderModel;

import java.util.HashMap;

public class OrderModelRespose {

    int numberofOrder;
    int quantityDemanded;
    int quantityDemandedValid;
    int quantityDemandedInvalid;
    int biggestOrder;
    int smallestOrder;
    OrderModel earlierOrderEntry;
    OrderModel lastOrderEntry;

    HashMap<Double, Integer> executionQuantityWithPrice;
    HashMap<Double, Integer> requestedOrderPrice;
    ExecutedOrder earlierOrderEntryExecuted;
    ExecutedOrder lastOrderEntryExecuted;

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

    public int getNumberofOrder() {
        return numberofOrder;
    }

    public void setNumberofOrder(int numberofOrder) {
        this.numberofOrder = numberofOrder;
    }

    public int getQuantityDemanded() {
        return quantityDemanded;
    }

    public void setQuantityDemanded(int quantityDemanded) {
        this.quantityDemanded = quantityDemanded;
    }

    public int getBiggestOrder() {
        return biggestOrder;
    }

    public void setBiggestOrder(int biggestOrder) {
        this.biggestOrder = biggestOrder;
    }

    public int getSmallestOrder() {
        return smallestOrder;
    }

    public void setSmallestOrder(int smallestOrder) {
        this.smallestOrder = smallestOrder;
    }

    public OrderModel getEarlierOrderEntry() {
        return earlierOrderEntry;
    }

    public void setEarlierOrderEntry(OrderModel earlierOrderEntry) {
        this.earlierOrderEntry = earlierOrderEntry;
    }

    public int getQuantityDemandedValid() {
        return quantityDemandedValid;
    }

    public void setQuantityDemandedValid(int quantityDemandedValid) {
        this.quantityDemandedValid = quantityDemandedValid;
    }

    public int getQuantityDemandedInvalid() {
        return quantityDemandedInvalid;
    }

    public void setQuantityDemandedInvalid(int quantityDemandedInvalid) {
        this.quantityDemandedInvalid = quantityDemandedInvalid;
    }

    public OrderModel getLastOrderEntry() {
        return lastOrderEntry;
    }

    public void setLastOrderEntry(OrderModel lastOrderEntry) {
        this.lastOrderEntry = lastOrderEntry;
    }
}
