package com.ujjain.trade.api.Utils;

import com.ujjain.trade.api.model.ExecuteOrderRequest;
import com.ujjain.trade.api.service.OrderBookServices;
import com.ujjain.trade.api.service.OrderService;
import com.ujjain.trade.dependencies.db.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ExecuteMarketOrder implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ExecuteMarketOrder.class);

    Object lock;
    OrderService orderService;
    OrderBookServices orderBookServices;
    ExecuteOrderRequest executeOrderRequest;

    public ExecuteMarketOrder(Object lock, OrderService orderService, OrderBookServices orderBookServices, ExecuteOrderRequest executeOrderRequest) {
        this.lock = lock;
        this.orderService = orderService;
        this.orderBookServices = orderBookServices;
        this.executeOrderRequest = executeOrderRequest;
    }

    @Override
    public void run() {
        List<Order> orderList = orderService.getAllByInstrumentId(executeOrderRequest.getFinanceInstrumentId(), true);


        for (Order order : orderList)
        {


            logger.info("Executing market order");
            if (orderList != null && orderList.size() > 0 && orderBookServices.getQty() > 0) {
                orderBookServices.executeOrderUnitWise(executeOrderRequest, order, true);
                }

        }


    }
}
