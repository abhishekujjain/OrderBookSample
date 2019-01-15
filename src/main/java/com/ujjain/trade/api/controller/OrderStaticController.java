package com.ujjain.trade.api.controller;


import com.ujjain.trade.api.model.OrderModelRespose;
import com.ujjain.trade.api.service.OrderBookServices;
import com.ujjain.trade.api.service.OrderService;
import com.ujjain.trade.dependencies.db.dao.ExecutedStatsDao;
import com.ujjain.trade.dependencies.db.dao.LimitOrderDao;
import com.ujjain.trade.dependencies.db.dao.OrderDao;
import com.ujjain.trade.dependencies.db.model.ExecutedOrder;
import com.ujjain.trade.dependencies.db.model.ExecutedOrdertable;
import com.ujjain.trade.dependencies.db.model.LimitOrder;
import com.ujjain.trade.dependencies.db.model.OrderModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@Api(value = "/api/statictics", description = "Executes and Order statictics ")
@RestController
@RequestMapping(value = "/api/statistics")
public class OrderStaticController {

    @Autowired
    OrderBookServices orderBookServices;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderDao orderDao;

    @Autowired
    ExecutedStatsDao executedStatsDao;

    @Autowired
    LimitOrderDao limitOrderDao;


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

    @ApiOperation(value = "2. obtain statistics about the amount of orders in each book, demand")
    @GetMapping(value = "/statsOrderRecieved")
    public OrderModelRespose getItems() {
        List<OrderModel> orderModelList = orderService.getAll();
        int size = orderModelList.size();
        OrderModelRespose orderModelRespose = new OrderModelRespose();
        orderModelRespose.setNumberofOrder(size);
        orderModelRespose.setBiggestOrder(orderService.getAllWithFilterQty().get(0).getQuantity());
        orderModelRespose.setSmallestOrder(orderService.getAllWithFilterQty().get(orderService.getAllWithFilterQty().size() - 1).getQuantity());
        orderModelRespose.setEarlierOrderEntry(orderService.getAll().get(orderService.getAll().size() - 1));
        orderModelRespose.setLastOrderEntry(orderService.getAll().get(0));
        orderModelRespose.setQuantityDemanded(findQuantity(orderService.getAll()));
        return orderModelRespose;

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
        orderModelRespose.setBiggestOrder(orderBookServices.getAllWithFilterQty().get(0).getQuantity());
        orderModelRespose.setSmallestOrder(orderBookServices.getAllWithFilterQty().get(orderBookServices.getAllWithFilterQty().size() - 1).getQuantity());
        orderModelRespose.setEarlierOrderEntryExecuted(orderBookServices.getAll().get(orderBookServices.getAll().size() - 1));
        orderModelRespose.setLastOrderEntryExecuted(orderBookServices.getAll().get(0));
        orderModelRespose.setQuantityDemanded(findQuantity(orderService.getAll()));
        orderModelRespose.setQuantityDemandedValid(validOrderList.size());
        orderModelRespose.setQuantityDemandedInvalid(executedOrderList.size() - validOrderList.size());
        List<ExecutedOrdertable> executedOrdertableList = executedStatsDao.findAll();
        HashMap<Double, Integer> priceExecuted = new HashMap<>();
        for (ExecutedOrdertable executedOrdertable : executedOrdertableList) {
            priceExecuted.put(executedOrdertable.getPrice(), executedOrdertable.getQuantity());
        }
        orderModelRespose.setExecutionQuantityWithPrice(priceExecuted);

        List<LimitOrder> limitOrderList = limitOrderDao.findAll();
        HashMap<Double, Integer> priceRequested = new HashMap<>();
        for (LimitOrder limitOrder : limitOrderList) {
            priceRequested.put(limitOrder.getPrice(), limitOrder.getQuantity());
        }
        orderModelRespose.setRequestedOrderPrice(priceRequested);
        return orderModelRespose;

    }


    private int findQuantity(List<OrderModel> all) {
        int qty = 0;

        for (OrderModel val : all) {
            qty += val.getQuantity();
        }
        return qty;
    }
}
