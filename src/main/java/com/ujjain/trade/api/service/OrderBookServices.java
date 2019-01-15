package com.ujjain.trade.api.service;

import com.ujjain.trade.api.Utils.ExecuteLimitOrder;
import com.ujjain.trade.api.Utils.ExecuteMarketOrder;
import com.ujjain.trade.api.model.ExecuteOrderRequest;
import com.ujjain.trade.dependencies.db.dao.ExecutedOrderDao;
import com.ujjain.trade.dependencies.db.dao.ExecutedStatsDao;
import com.ujjain.trade.dependencies.db.dao.InstrumentStatusDao;
import com.ujjain.trade.dependencies.db.model.ExecutedOrder;
import com.ujjain.trade.dependencies.db.model.ExecutedOrdertable;
import com.ujjain.trade.dependencies.db.model.InstrumentStatus;
import com.ujjain.trade.dependencies.db.model.OrderModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Service
public class OrderBookServices {
    @Autowired
    ExecutedOrderDao executedOrderDao;
    @Autowired
    OrderService orderService;
    @Autowired
    InstrumentStatusDao instrumentStatusDao;
    @Autowired
    ExecutedStatsDao executedStatsDao;
    private volatile int qty = 0;

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Transactional
    public String executeOrder(ExecuteOrderRequest executeOrderRequest) {
        try {
            InstrumentStatus instrumentStatus = instrumentStatusDao.findByInstrumentId(executeOrderRequest.getFinanceInstrumentId());
            if (instrumentStatus != null && instrumentStatus.isStatus() == false) {
                callExecuteOrder(executeOrderRequest);
            } else {
                return "Close finance Instrument";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Success";
    }

    public void callExecuteOrder(ExecuteOrderRequest executeOrderRequest) {
        List<OrderModel> orderModelList = orderService.getAllByInstrumentId(executeOrderRequest.getFinanceInstrumentId(), false);

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        Object lock = new Object();
        qty = executeOrderRequest.getQuantity();
        scheduledExecutorService.execute(new ExecuteMarketOrder(lock, orderService, this, executeOrderRequest));
        scheduledExecutorService.execute(new ExecuteLimitOrder(lock, orderService, this, executeOrderRequest));
        scheduledExecutorService.shutdown();

    }

    public void executeOrderUnitWise(ExecuteOrderRequest executedOrder, OrderModel orderModel, boolean isMarket) {
        boolean status = true;
        if (orderModel.getPrice() > executedOrder.getPrice() || isMarket) {
            status = false;
        }

        if (qty - orderModel.getQuantity() > 0) {
            if (status) {
                qty = qty - orderModel.getQuantity();
            }
            double exeCutedPrice = orderModel.getPrice();
            if (isMarket)
                exeCutedPrice = executedOrder.getPrice();

            executedOrderDao.save(new ExecutedOrder(orderModel.getQuantity(), exeCutedPrice, orderModel.getId(), status, orderModel.getFinanceIntrumentId()));
            orderService.deleteOrder(orderModel);
            updateExecutedTable(orderModel);
//
        }
    }

    private void updateExecutedTable(OrderModel orderModel) {

        int val = 0;
        ExecutedOrdertable executedOrdertable = null;
        try {
            executedOrdertable = executedStatsDao.findByPrice(orderModel.getPrice());
            if (executedOrdertable != null) {
                val = executedStatsDao.updateExecutedOrder(executedOrdertable.getQuantity() + orderModel.getQuantity(), orderModel.getPrice());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (val <= 0 && executedOrdertable == null) {
            executedStatsDao.save(new ExecutedOrdertable(orderModel.getPrice(), orderModel.getQuantity()));
        }

    }

    public ExecutedOrder getbyId(Long id) {
        ExecutedOrder executedOrder = executedOrderDao.findExecutedOrdersByOrderId(id);
        return executedOrder;
    }

    public List<ExecutedOrder> getAll() {
        return executedOrderDao.findAll();
    }

    public List<ExecutedOrder> getAllByValidity(boolean status) {
        return executedOrderDao.findAllByValidOrder(status);
    }

    public int updateOrder(boolean status, Long id) {
        return executedOrderDao.updateExecutedOrder(status, id);
    }


    public List<ExecutedOrder> getAllWithFilter() {
        return executedOrderDao.findAll(sortByIdAsc("createdOn"));
    }

    public List<ExecutedOrder> getAllWithFilterQty() {
        return executedOrderDao.findAll(sortByIdAsc("quantity"));
    }

    private Sort sortByIdAsc(String item) {
        return new Sort(Sort.Direction.DESC, item);
    }
}
