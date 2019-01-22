package com.ujjain.trade.api.Utils;

import com.ujjain.trade.api.model.ExecuteOrderRequest;
import com.ujjain.trade.api.service.OrderBookServices;
import com.ujjain.trade.api.service.OrderService;
import com.ujjain.trade.dependencies.db.model.OrderModel;
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

            List<OrderModel> orderModelList = orderService.getAll(false);


            for (OrderModel orderModel : orderModelList) {
                synchronized (lock) {

                    logger.info("Executing limit order");
                    if (orderModelList != null && orderModelList.size() > 0 && orderBookServices.getQty() > 0) {
                        orderBookServices.executeOrderUnitWise(executeOrderRequest, orderModel,false);
                    }
                    try {
                        lock.notifyAll();
                        if (orderService.getAll(true) != null && orderService.getAll(true).size() > 0) {
                            lock.wait();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }


        }

    }
}
