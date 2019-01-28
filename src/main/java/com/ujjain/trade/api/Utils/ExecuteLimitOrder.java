package com.ujjain.trade.api.Utils;

import com.ujjain.trade.api.model.ExecuteOrderRequest;
import com.ujjain.trade.api.service.OrderBookServices;
import com.ujjain.trade.api.service.OrderService;
import com.ujjain.trade.dependencies.db.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ExecuteLimitOrder implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ExecuteLimitOrder.class);

    Object lock;
    OrderService orderService;
    OrderBookServices orderBookServices;
    ExecuteOrderRequest executeOrderRequest;

    public ExecuteLimitOrder(Object lock, OrderService orderService, OrderBookServices orderBookServices, ExecuteOrderRequest executeOrderRequest) {
        this.lock = lock;
        this.orderService = orderService;
        this.orderBookServices = orderBookServices;
        this.executeOrderRequest = executeOrderRequest;
    }

    @Override
    public void run() {
        synchronized (lock) {

            List<Order> orderModelList = orderService.getAllByInstrumentId(executeOrderRequest.getFinanceInstrumentId(), false);


            for (Order orderModel : orderModelList) {

                    logger.info("Executing limit order");
                    if (orderModelList != null && orderModelList.size() > 0 && orderBookServices.getQty() > 0) {
                        orderBookServices.executeOrderUnitWise(executeOrderRequest, orderModel,false);
                    }

            }


        }

    }
}
