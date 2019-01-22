package com.ujjain.trade.api.Utils;

import com.ujjain.trade.api.model.ExecuteOrderRequest;
import com.ujjain.trade.api.service.OrderBookServices;
import com.ujjain.trade.api.service.OrderService;
import com.ujjain.trade.dependencies.db.model.OrderModel;
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
        List<OrderModel> orderModelList = orderService.getAllByInstrumentId(executeOrderRequest.getFinanceInstrumentId(), true);


        for(OrderModel orderModel:orderModelList)
        {
            synchronized (lock) {

                logger.info("Executing market order");
                if (orderModelList != null && orderModelList.size() > 0 && orderBookServices.getQty()>0) {
                    orderBookServices.executeOrderUnitWise(executeOrderRequest, orderModel,true);
                }
                try {
                lock.notifyAll();

                    if (orderService.getAll(false) != null && orderService.getAll(false).size() > 0) {
                        lock.wait();
                    }                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }



    }
}
