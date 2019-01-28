package com.ujjain.trade.api.controller;

import com.ujjain.trade.api.model.*;
import com.ujjain.trade.api.service.OrderBookServices;
import com.ujjain.trade.api.service.OrderService;
import com.ujjain.trade.api.service.OrderstatsServices;
import com.ujjain.trade.dependencies.db.model.ExecutedOrder;
import com.ujjain.trade.dependencies.db.model.FinancialInstrument;
import com.ujjain.trade.dependencies.db.model.Order;
import com.ujjain.trade.dependencies.db.model.OrderRecieved;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "/api/Order", description = "Order for trade")
@RestController
@RequestMapping(value = "/api/order")
public class OrderController {

    final OrderService orderService;
    final OrderBookServices orderBookServices;
    final OrderstatsServices orderstatsServices;

    @Autowired
    public OrderController(OrderService orderService, OrderBookServices orderBookServices, OrderstatsServices orderstatsServices) {
        this.orderService = orderService;
        this.orderBookServices = orderBookServices;
        this.orderstatsServices = orderstatsServices;
    }

    @ApiOperation(value = "Add order to order book")
    @PostMapping(value = "/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addOrder(@RequestBody final OrderRequest orderRequest) {

        return new ResponseEntity<String>(orderService.addOrder(orderRequest), HttpStatus.OK);
    }

    @ApiOperation(value = "get all Order")
    @GetMapping(value = "/all")
    public List<Order> getAll() {
        return orderService.getAllWithFilter();
    }

    @ApiOperation(value = "get all Order based on Type")
    @GetMapping(value = "/all-order")
    public List<Order> getAll(@RequestParam("isMarketType") boolean isMarketType) {
        return orderService.getAll(isMarketType);
    }

    @ApiOperation(value = "1. open an order book for an instrument ")
    @PutMapping(value = "/open-order")
    public FinancialInstrument openInstrument(@RequestParam("finId") int finId) {

        return orderService.updateInstrumentStatus(finId, true);
    }

    @ApiOperation(value = "1.to close an order book for an instrument ")
    @PutMapping(value = "/close-order")
    public FinancialInstrument closeInstrument(@RequestParam("finId") int finId) {

        return orderService.updateInstrumentStatus(finId, false);
    }

    @ApiOperation(value = "get all Finanace Instrument based on type")
    @GetMapping(value = "/findall-by")
    public List<Order> getAllByFianaceId(@RequestParam("financeId") int financeId, @RequestParam("isMarketOrder") boolean isMarketOrder) {
        return orderService.getAllByInstrumentId(financeId, isMarketOrder);
    }

    @ApiOperation(value = "Add Instrument")
    @PostMapping(value = "/add-instrument")
    public FinancialInstrument addFinanceInstrument(@RequestBody FinacialInstrumentRequest finacialInstrumentRequest) {
        return orderService.addInstrument(finacialInstrumentRequest);
    }

    @ApiOperation(value = "Execute order ")
    @PostMapping(value = "/execute")
    public ResponseEntity<String> executeOrder(@RequestBody final ExecuteOrderRequest executeOrderRequest) {

        return new ResponseEntity<String>(orderBookServices.executeOrder(executeOrderRequest), HttpStatus.OK);
    }


    @ApiOperation(value = "All financial id orders")
    @GetMapping(value = "/all-recieced-order")
    public List<FinancialInstrument> findInsbyId() {
        return orderService.getOrderBookList();
    }

    @ApiOperation(value = "4. Details of executed order")
    @GetMapping(value = "/executed-orderd-details")
    public String getExecutedOrderDetails(@RequestParam("orderId") Long id) {
        return orderstatsServices.getbyId(id);
    }

    @ApiOperation(value = "4. Details of recieved order")
    @GetMapping(value = "/recieved-order-details")
    public String getRecievedOrderDetails(@RequestParam("orderId") Long id) {
        return orderService.getbyId(id);
    }


    @ApiOperation(value = "get all Executed Order")
    @GetMapping(value = "/all-executed-order")
    public List<ExecutedOrder> getAllExecuted() {
        return orderstatsServices.getAll();
    }

    @ApiOperation(value = "Limit order price demand")
    @GetMapping(value = "/demand-pricewise-limitorder")
    public List<OrderRecieved> getOrderPriceWise() {
        return orderstatsServices.findAll();
    }

    @ApiOperation(value = "2. obtain statistics about the amount of orders in each book, demand")
    @GetMapping(value = "/statistics-recieved-order")
    public ReciecevedOrderStatsModel getItems() {
        return orderstatsServices.getRecievedOrderStats();
    }


    @ApiOperation(value = "3. Obtain statistics about the amount")
    @GetMapping(value = "/statistics-executed-order")
    public ExecutedOrderResponse getItemsExecuted() {
        return orderstatsServices.getExecutedStats();
    }

}
