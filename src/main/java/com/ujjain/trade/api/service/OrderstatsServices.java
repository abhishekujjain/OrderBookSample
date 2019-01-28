package com.ujjain.trade.api.service;

import com.ujjain.trade.api.model.ExecutedOrderResponse;
import com.ujjain.trade.api.model.ReciecevedOrderStatsModel;
import com.ujjain.trade.dependencies.db.dao.ExecutedStatsDao;
import com.ujjain.trade.dependencies.db.dao.OrderBookStatusDao;
import com.ujjain.trade.dependencies.db.dao.OrderRecievedDao;
import com.ujjain.trade.dependencies.db.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class OrderstatsServices {

    private static final Logger logger = LoggerFactory.getLogger(OrderstatsServices.class);

    final OrderBookServices orderBookServices;

    final OrderService orderService;

    private final OrderRecievedDao orderRecievedDao;

    private final ExecutedStatsDao executedStatsDao;

    private final OrderBookStatusDao orderBookStatusDao;

    @Autowired
    public OrderstatsServices(OrderBookServices orderBookServices, OrderService orderService, OrderRecievedDao orderRecievedDao, ExecutedStatsDao executedStatsDao, OrderBookStatusDao orderBookStatusDao) {
        this.orderBookServices = orderBookServices;
        this.orderService = orderService;
        this.orderRecievedDao = orderRecievedDao;
        this.executedStatsDao = executedStatsDao;
        this.orderBookStatusDao = orderBookStatusDao;
    }

    public ExecutedOrderResponse getExecutedStats() {
        logger.info("Processing Executed order data");
        ExecutedOrderResponse executedOrderResponse = new ExecutedOrderResponse();
        List<Order> orderList = orderService.getAll();
        int size = orderList.size();
        executedOrderResponse.setNumberofOrder(size);
        List<ExecutedOrder> executedOrderList = orderBookServices.getAll();
        List<ExecutedOrder> validOrderList = orderBookServices.getAllByValidity(true);
        if (orderBookServices.getAllWithFilterQty() != null && orderBookServices.getAllWithFilterQty().size() > 0) {

            executedOrderResponse.setBiggestOrder(orderBookServices.getAllWithFilterQty().get(0).getAllocatedQuantity());
            executedOrderResponse.setSmallestOrder(orderBookServices.getAllWithFilterQty().get(orderBookServices.getAllWithFilterQty().size() - 1).getAllocatedQuantity());
            executedOrderResponse.setEarlierOrderEntryExecuted(orderBookServices.getAll().get(orderBookServices.getAll().size() - 1));
            executedOrderResponse.setLastOrderEntryExecuted(orderBookServices.getAll().get(0));
        }
        executedOrderResponse.setQuantityDemanded(findQuantity(orderService.getAll()));
        executedOrderResponse.setQuantityDemandedValid(validOrderList.size());
        executedOrderResponse.setQuantityDemandedInvalid(executedOrderList.size() - validOrderList.size());
        List<ExecutedOrdertable> executedOrdertableList = executedStatsDao.findAll();
        HashMap<Double, Integer> priceExecuted = new HashMap<>();
        for (ExecutedOrdertable executedOrdertable : executedOrdertableList) {
            priceExecuted.put(executedOrdertable.getExecutionPrice(), executedOrdertable.getAllotedQuantity());
        }
        executedOrderResponse.setExecutionQuantityWithPrice(priceExecuted);

        List<OrderRecieved> orderRecievedList = orderRecievedDao.findAll();
        HashMap<Double, Integer> priceRequested = new HashMap<>();
        for (OrderRecieved orderRecieved : orderRecievedList) {
            priceRequested.put(orderRecieved.getPrice(), orderRecieved.getQuantity());
        }
        executedOrderResponse.setRequestedOrderPrice(priceRequested);
        logger.info("Processing done for Executed order data");

        return executedOrderResponse;
    }

    private int findQuantity(List<Order> all) {
        int qty = 0;

        for (Order val : all) {
            qty += val.getOrderedQuantity();
        }
        return qty;
    }

    public ReciecevedOrderStatsModel getRecievedOrderStats() {
        logger.info("Processing Recieved order data");

        List<Order> orderList = orderService.getAllWithFilter();
        int size = orderList.size();
        ReciecevedOrderStatsModel reciecevedOrderStatsModel = new ReciecevedOrderStatsModel();
        List<Order> marketOrderList = orderService.getAll(true);
        if (marketOrderList != null) {
            reciecevedOrderStatsModel.setMarketOrderQuantity(getCount(marketOrderList));
        }
        reciecevedOrderStatsModel.setNumberofOrder(size);
        if (orderService.getAllWithFilterQty() != null && orderService.getAllWithFilterQty().size() > 0) {
            reciecevedOrderStatsModel.setBiggestOrder(orderService.getAllWithFilterQty().get(0).getOrderedQuantity());
            reciecevedOrderStatsModel.setSmallestOrder(orderService.getAllWithFilterQty().get(orderService.getAllWithFilterQty().size() - 1).getOrderedQuantity());
            reciecevedOrderStatsModel.setEarlierOrderEntry(orderService.getAll().get(orderService.getAll().size() - 1));
            reciecevedOrderStatsModel.setLastOrderEntry(orderService.getAll().get(0));

        }

        reciecevedOrderStatsModel.setQuantityDemanded(findQuantity(orderService.getAll()));
        List<FinancialInstrument> financialInstrumentList = orderBookStatusDao.findAll();
        HashMap<Integer, Integer> orderBookWise = new HashMap<>();

        for (FinancialInstrument financialInstrument : financialInstrumentList) {
            orderBookWise.put(financialInstrument.getInstrumentId(), getCount(orderService.getAllByFinId(financialInstrument.getInstrumentId())));

        }
        reciecevedOrderStatsModel.setOrderBookWiseWise(orderBookWise);
        logger.info("Processing done for Recieved order data");

        return reciecevedOrderStatsModel;
    }

    private Integer getCount(List<Order> orderList) {
        int count = 0;
        for (Order order : orderList) {
            count += order.getOrderedQuantity();
        }
        return count;
    }

    public List<OrderRecieved> findAll() {
        return orderRecievedDao.findAll();
    }

    public List<ExecutedOrder> getAll() {
        return orderBookServices.getAll();
    }

    public String getbyId(Long id) {
        ExecutedOrder executedOrder = orderBookServices.getbyId(id);
        if (executedOrder == null) {
            return "Order doesn't exist";
        }
        return executedOrder.toString();
    }
}
