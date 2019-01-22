package com.ujjain.trade.api.controller;


import com.ujjain.trade.api.model.OrderModelRespose;
import com.ujjain.trade.api.service.OrderBookServices;
import com.ujjain.trade.api.service.OrderService;
import com.ujjain.trade.api.service.OrderstatsServices;
import com.ujjain.trade.dependencies.db.dao.ExecutedStatsDao;
import com.ujjain.trade.dependencies.db.dao.OrderBookStatusDao;
import com.ujjain.trade.dependencies.db.dao.OrderDao;
import com.ujjain.trade.dependencies.db.dao.OrderRecievedDao;
import com.ujjain.trade.dependencies.db.model.ExecutedOrder;
import com.ujjain.trade.dependencies.db.model.OrderRecieved;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "/api/statistics", description = "Executes and Order stats ")
@RestController
@RequestMapping(value = "/api/statistics")
public class OrderStatsController {


    @Autowired
    OrderstatsServices orderstatsServices;


    @ApiOperation(value = "4. get, for the given order id, validity status")
    @GetMapping(value = "/findByParams")
    public ExecutedOrder getOrderBased(@RequestParam("orderId") Long id) {

        return orderstatsServices.getbyId(id);
    }


    @ApiOperation(value = "get all Executed Order based on")
    @GetMapping(value = "/findAll")
    public List<ExecutedOrder> getAllExecuted() {

        return orderstatsServices.getAll();
    }

    @ApiOperation(value = "get all Executed Order based on")
    @GetMapping(value = "/demandPriceWiseLimitOrder")
    public List<OrderRecieved> getOrderPriceWise() {
        return orderstatsServices.findAll();
    }

    @ApiOperation(value = "2. obtain statistics about the amount of orders in each book, demand")
    @GetMapping(value = "/statsOrderRecieved")
    public OrderModelRespose getItems() {

        return orderstatsServices.getRecievedOrderStats();

    }




    @ApiOperation(value = "3. obtain statistics about the amount")
    @GetMapping(value = "/statsExecutedOrder")
    public OrderModelRespose getItemsExecuted() {

        return orderstatsServices.getExecutedStats();

    }



}
