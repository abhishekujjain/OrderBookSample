package com.ujjain.trade.api.controller;

import com.ujjain.trade.api.model.OrderRequest;
import com.ujjain.trade.api.service.OrderService;
import com.ujjain.trade.dependencies.db.model.OrderBookTable;
import com.ujjain.trade.dependencies.db.model.OrderModel;
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

    @Autowired
    OrderService orderService;


    @ApiOperation(value = "Add order to order book")
    @PostMapping(value = "/add")
    public ResponseEntity<String> addOrder(@RequestBody final OrderRequest orderRequest) {

        return new ResponseEntity<String>(orderService.addOrder(orderRequest), HttpStatus.OK);
    }

    @ApiOperation(value = "get all Order")
    @GetMapping(value = "/all")
    public List<OrderModel> getAll() {
        return orderService.getAllWithFilter();
    }

    @ApiOperation(value = "get all Order based on type")
    @GetMapping(value = "/find")
    public List<OrderModel> getAll(@RequestParam("isMarketType") boolean isMarketType) {
        return orderService.getAll(isMarketType);
    }


    @ApiOperation(value = "get all Finanace Instrument based on type")
    @GetMapping(value = "/findbyFinanceId")
    public List<OrderModel> getAllByFianaceId(@RequestParam("financeId") int financeId,@RequestParam("isMarketOrder") boolean isMarketOrder) {
        return orderService.getAllByInstrumentId(financeId,isMarketOrder);
    }

    @ApiOperation(value = "Add Instrument")
    @PostMapping(value = "/addInstrument")
    public OrderBookTable addFinanceInstrument(@RequestBody OrderBookTable bookTable) {
        return orderService.addInstrument(bookTable);
    }



//    @ApiOperation(value = "get orderBook table by Id")
//    @GetMapping(value = "/findInstrumentById")
//    public OrderBookTable findInsbyId(@RequestParam("finId") int finId) {
//        return orderService.findInstrumentById(finId);
//    }
}
