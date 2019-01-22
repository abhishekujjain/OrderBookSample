package com.ujjain.trade.api.controller;

import com.ujjain.trade.api.model.ExecuteOrderRequest;
import com.ujjain.trade.api.service.OrderBookServices;
import com.ujjain.trade.api.service.OrderService;
import com.ujjain.trade.dependencies.db.model.OrderBookTable;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "/api/Orderbook", description = "Orderbook ")
@RestController
@RequestMapping(value = "/api/Orderbook")
public class OrderBookController {

    @Autowired
    OrderBookServices orderBookServices;

    @Autowired
    OrderService orderService;

    @ApiOperation(value = "Add order to order book")
    @PostMapping(value = "/execute")
    public ResponseEntity<String> executeOrder(@RequestBody final ExecuteOrderRequest executeOrderRequest) {

        return new ResponseEntity<String>(orderBookServices.executeOrder(executeOrderRequest), HttpStatus.OK);
    }

    @ApiOperation(value = "1. open an order book for an instrument ")
    @PutMapping(value = "/openOrder")
    public List<OrderBookTable> openInstrument(@RequestParam("finId") int finId) {
        try {
            orderService.updateFinanceInstrumentId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderService.updateInstrumentStatus(finId, true);
    }

    @ApiOperation(value = "1.to close an order book for an instrument ")
    @PutMapping(value = "/closeOrder")
    public List<OrderBookTable> closeInstrument(@RequestParam("finId") int finId) {
        try {
            orderService.updateFinanceInstrumentId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderService.updateInstrumentStatus(finId, false);
    }


    @ApiOperation(value = "get all OrderBook")
    @GetMapping(value = "/orderBookList")
    public List<OrderBookTable> findInsbyId() {
        return orderService.getOrderBookList();
    }

}
