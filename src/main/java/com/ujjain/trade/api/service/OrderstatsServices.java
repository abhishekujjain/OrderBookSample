package com.ujjain.trade.api.service;

import com.ujjain.trade.api.model.OrderModelRespose;
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


    @Autowired
    OrderBookServices orderBookServices;

    @Autowired
    OrderService orderService;

    @Autowired
    private OrderRecievedDao orderRecievedDao;

    @Autowired
    private ExecutedStatsDao executedStatsDao;

    @Autowired
    private OrderBookStatusDao orderBookStatusDao;

    public OrderModelRespose getExecutedStats() {
        logger.info("Processing Executed order data");
        OrderModelRespose orderModelRespose = new OrderModelRespose();
        List<OrderModel> orderModelList = orderService.getAll();
        int size = orderModelList.size();
        orderModelRespose.setNumberofOrder(size);
        List<ExecutedOrder> executedOrderList = orderBookServices.getAll();
        List<ExecutedOrder> validOrderList = orderBookServices.getAllByValidity(true);
        if (orderBookServices.getAllWithFilterQty() != null && orderBookServices.getAllWithFilterQty().size() > 0) {

            orderModelRespose.setBiggestOrder(orderBookServices.getAllWithFilterQty().get(0).getAllocatedQuantity());
            orderModelRespose.setSmallestOrder(orderBookServices.getAllWithFilterQty().get(orderBookServices.getAllWithFilterQty().size() - 1).getAllocatedQuantity());
            orderModelRespose.setEarlierOrderEntryExecuted(orderBookServices.getAll().get(orderBookServices.getAll().size() - 1));
            orderModelRespose.setLastOrderEntryExecuted(orderBookServices.getAll().get(0));
        }
        orderModelRespose.setQuantityDemanded(findQuantity(orderService.getAll()));
        orderModelRespose.setQuantityDemandedValid(validOrderList.size());
        orderModelRespose.setQuantityDemandedInvalid(executedOrderList.size() - validOrderList.size());
        List<ExecutedOrdertable> executedOrdertableList = executedStatsDao.findAll();
        HashMap<Double, Integer> priceExecuted = new HashMap<>();
        for (ExecutedOrdertable executedOrdertable : executedOrdertableList) {
            priceExecuted.put(executedOrdertable.getExecutionPrice(), executedOrdertable.getAllotedQuantity());
        }
        orderModelRespose.setExecutionQuantityWithPrice(priceExecuted);

        List<OrderRecieved> orderRecievedList = orderRecievedDao.findAll();
        HashMap<Double, Integer> priceRequested = new HashMap<>();
        for (OrderRecieved orderRecieved : orderRecievedList) {
            priceRequested.put(orderRecieved.getPrice(), orderRecieved.getQuantity());
        }
        orderModelRespose.setRequestedOrderPrice(priceRequested);
        logger.info("Processing done for Executed order data");

        return orderModelRespose;
    }

    private int findQuantity(List<OrderModel> all) {
        int qty = 0;

        for (OrderModel val : all) {
            qty += val.getOrderedQuantity();
        }
        return qty;
    }

    public OrderModelRespose getRecievedOrderStats() {
        logger.info("Processing Recieved order data");

        List<OrderModel> orderModelList = orderService.getAllWithFilter();
        int size = orderModelList.size();
        OrderModelRespose orderModelRespose = new OrderModelRespose();
        List<OrderModel> marketOrderList = orderService.getAll(true);
        if (marketOrderList != null) {
            orderModelRespose.setMarketOrderQuantity(getCount(marketOrderList));
        }
        orderModelRespose.setNumberofOrder(size);
        if (orderService.getAllWithFilterQty() != null && orderService.getAllWithFilterQty().size() > 0) {
            orderModelRespose.setBiggestOrder(orderService.getAllWithFilterQty().get(0).getOrderedQuantity());
            orderModelRespose.setSmallestOrder(orderService.getAllWithFilterQty().get(orderService.getAllWithFilterQty().size() - 1).getOrderedQuantity());
            orderModelRespose.setEarlierOrderEntry(orderService.getAll().get(orderService.getAll().size() - 1));
            orderModelRespose.setLastOrderEntry(orderService.getAll().get(0));

        }

        orderModelRespose.setQuantityDemanded(findQuantity(orderService.getAll()));
        List<OrderBookTable> orderBookTableList = orderBookStatusDao.findAll();
        HashMap<Integer, Integer> orderBookWise = new HashMap<>();

        for (OrderBookTable orderBookTable : orderBookTableList) {
            orderBookWise.put(orderBookTable.getInstrumentId(), getCount(orderService.getAllByFinId(orderBookTable.getInstrumentId())));

        }
        orderModelRespose.setOrderBookWiseWise(orderBookWise);
        logger.info("Processing done for Recieved order data");

        return orderModelRespose;
    }

    private Integer getCount(List<OrderModel> orderModelList) {
        int count = 0;
        for (OrderModel orderModel : orderModelList) {
            count += orderModel.getOrderedQuantity();
        }
        return count;
    }

    public List<OrderRecieved> findAll() {
        return orderRecievedDao.findAll();
    }

    public List<ExecutedOrder> getAll() {
        return orderBookServices.getAll();
    }

    public ExecutedOrder getbyId(Long id) {
        return orderBookServices.getbyId(id);
    }
}
