package com.ujjain.trade.api.controller;


import com.ujjain.trade.api.model.OrderModelRespose;
import com.ujjain.trade.api.service.OrderBookServices;
import com.ujjain.trade.api.service.OrderService;
import com.ujjain.trade.dependencies.db.dao.ExecutedStatsDao;
import com.ujjain.trade.dependencies.db.dao.OrderBookStatusDao;
import com.ujjain.trade.dependencies.db.dao.OrderDao;
import com.ujjain.trade.dependencies.db.dao.OrderRecievedDao;
import com.ujjain.trade.dependencies.db.model.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@Api(value = "/api/statistics", description = "Executes and Order stats ")
@RestController
@RequestMapping(value = "/api/statistics")
public class OrderStatsController {

    @Autowired
    OrderBookServices orderBookServices;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderDao orderDao;

    @Autowired
    ExecutedStatsDao executedStatsDao;

    @Autowired
    OrderRecievedDao orderRecievedDao;

    @Autowired
    OrderBookStatusDao orderBookStatusDao;


    @ApiOperation(value = "4. get, for the given order id, validity status")
    @GetMapping(value = "/findByParams")
    public ExecutedOrder getOrderBased(@RequestParam("orderId") Long id) {

        return orderBookServices.getbyId(id);
    }


    @ApiOperation(value = "get all Executed Order based on")
    @GetMapping(value = "/findAll")
    public List<ExecutedOrder> getAllExecuted() {

        return orderBookServices.getAll();
    }

    @ApiOperation(value = "get all Executed Order based on")
    @GetMapping(value = "/demandPriceWiseLimitOrder")
    public List<OrderRecieved> getOrderPriceWise() {
        return orderRecievedDao.findAll();
    }

    @ApiOperation(value = "2. obtain statistics about the amount of orders in each book, demand")
    @GetMapping(value = "/statsOrderRecieved")
    public OrderModelRespose getItems() {
        List<OrderModel> orderModelList = orderService.getAllWithFilter();
        int size = orderModelList.size();
        OrderModelRespose orderModelRespose = new OrderModelRespose();
        List<OrderModel> marketOrderList = orderService.getAll(true);
        if (marketOrderList!=null) {
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
        return orderModelRespose;

    }

    private Integer getCount(List<OrderModel> orderModelList) {
        int count =0;
        for(OrderModel orderModel:orderModelList)
        {
            count+=orderModel.getOrderedQuantity();
        }
        return count;
    }


    @ApiOperation(value = "3. obtain statistics about the amount")
    @GetMapping(value = "/statsExecutedOrder")
    public OrderModelRespose getItemsExecuted() {
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
        return orderModelRespose;

    }


    private int findQuantity(List<OrderModel> all) {
        int qty = 0;

        for (OrderModel val : all) {
            qty += val.getOrderedQuantity();
        }
        return qty;
    }
}
