package com.ujjain.trade.api.model;

import com.ujjain.trade.dependencies.db.model.Order;

import java.util.HashMap;

public class ReciecevedOrderStatsModel {

    Integer numberofOrder;
    Integer quantityDemanded;
    Integer quantityDemandedValid;
    Integer quantityDemandedInvalid;
    Integer biggestOrder;
    Integer smallestOrder;
    Order earlierOrderEntry;
    Order lastOrderEntry;
    Integer marketOrderQuantity;
    HashMap<Double, Integer> requestedOrderPrice;
    HashMap<Integer, Integer> OrderBookWiseWise;

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

    public Order getEarlierOrderEntry() {
        return earlierOrderEntry;
    }

    public void setEarlierOrderEntry(Order earlierOrderEntry) {
        this.earlierOrderEntry = earlierOrderEntry;
    }

    public Order getLastOrderEntry() {
        return lastOrderEntry;
    }

    public void setLastOrderEntry(Order lastOrderEntry) {
        this.lastOrderEntry = lastOrderEntry;
    }

    public Integer getMarketOrderQuantity() {
        return marketOrderQuantity;
    }

    public void setMarketOrderQuantity(Integer marketOrderQuantity) {
        this.marketOrderQuantity = marketOrderQuantity;
    }

    public HashMap<Double, Integer> getRequestedOrderPrice() {
        return requestedOrderPrice;
    }

    public void setRequestedOrderPrice(HashMap<Double, Integer> requestedOrderPrice) {
        this.requestedOrderPrice = requestedOrderPrice;
    }

    public HashMap<Integer, Integer> getOrderBookWiseWise() {
        return OrderBookWiseWise;
    }

    public void setOrderBookWiseWise(HashMap<Integer, Integer> orderBookWiseWise) {
        OrderBookWiseWise = orderBookWiseWise;
    }
}
