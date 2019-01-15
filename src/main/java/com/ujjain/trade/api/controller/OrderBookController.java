package com.ujjain.trade.api.controller;

import com.ujjain.trade.api.model.ExecuteOrderRequest;
import com.ujjain.trade.api.model.OrderRequest;
import com.ujjain.trade.api.service.OrderBookServices;
import com.ujjain.trade.dependencies.db.model.ExecutedOrder;
import com.ujjain.trade.dependencies.db.model.InstrumentStatus;
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

    @ApiOperation(value = "Add order to order book")
    @PostMapping(value = "/execute")
    public ResponseEntity<String> executeOrder(@RequestBody final ExecuteOrderRequest executeOrderRequest) {

        return new ResponseEntity<String>(orderBookServices.executeOrder(executeOrderRequest), HttpStatus.OK);
    }


    @ApiOperation(value = "Update ExecutedOrder ")
    @PutMapping(value = "/updateExecutedOrder")
    public ExecutedOrder updateInstrument(@RequestParam("id") Long id, @RequestParam("status") boolean status) {
        try {
            orderBookServices.updateOrder(status,id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderBookServices.getbyId(id);
    }

}
