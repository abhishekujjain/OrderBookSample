package com.ujjain.trade.api.controller;

import com.ujjain.trade.api.model.OrderRequest;
import com.ujjain.trade.api.service.OrderService;
import com.ujjain.trade.dependencies.db.model.InstrumentStatus;
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

    @ApiOperation(value = "1. open and to close an order book for an instrument ")
    @PutMapping(value = "/updateInstrument")
    public List<InstrumentStatus> updateInstrument(@RequestParam("finId") int finId, @RequestParam("status") boolean status) {
        try {
            orderService.updateFinanceInstrumentId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderService.updateInstrumentStatus(finId, status);
    }

    //    @ApiOperation(value = "Update Order ")
//    @PutMapping(value = "/updateOrder")
    public OrderModel updateOrder(@RequestParam("finId") Long id, @RequestParam("price") double price) {
        try {
            return orderService.updateOrder(id, price);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @ApiOperation(value = "get all Order based on type")
    @GetMapping(value = "/findInstrumentById")
    public InstrumentStatus findInsbyId(@RequestParam("finId") int finId) {
        return orderService.findInstrumentById(finId);
    }
}
