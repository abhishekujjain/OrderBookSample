package com.ujjain.trade.api.service;

import com.ujjain.trade.api.Utils.ExecuteLimitOrder;
import com.ujjain.trade.api.Utils.ExecuteMarketOrder;
import com.ujjain.trade.api.model.ExecuteOrderRequest;
import com.ujjain.trade.dependencies.db.dao.ExecutedOrderDao;
import com.ujjain.trade.dependencies.db.dao.ExecutedStatsDao;
import com.ujjain.trade.dependencies.db.dao.OrderBookStatusDao;
import com.ujjain.trade.dependencies.db.model.ExecutedOrder;
import com.ujjain.trade.dependencies.db.model.ExecutedOrdertable;
import com.ujjain.trade.dependencies.db.model.OrderBookTable;
import com.ujjain.trade.dependencies.db.model.OrderModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Service
public class OrderBookServices {
    private static final Logger logger = LoggerFactory.getLogger(OrderBookServices.class);

    @Autowired
    ExecutedOrderDao executedOrderDao;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderBookStatusDao orderBookStatusDao;
    @Autowired
    ExecutedStatsDao executedStatsDao;
    private volatile int qty = 0;
    private Double allocationPerUnit = 0.0;

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Transactional
    public String executeOrder(ExecuteOrderRequest executeOrderRequest) {
        try {
            logger.info("Order Execution started");

            OrderBookTable orderBookTable = orderBookStatusDao.findByInstrumentId(executeOrderRequest.getFinanceInstrumentId());
            if (orderBookTable != null && orderBookTable.getStatus() == false) {
                callExecuteOrder(executeOrderRequest);
            } else {
                return "Close finance Instrument";
            }
        } catch (Exception e) {
            logger.info("Exeception while executing order " + e.getMessage());
            e.printStackTrace();
        }
        return "Success";
    }

    public void callExecuteOrder(ExecuteOrderRequest executeOrderRequest) {
        List<OrderModel> orderModelList = orderService.getAllByInstrumentId(executeOrderRequest.getFinanceInstrumentId(), false);

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        Object lock = new Object();
        qty = executeOrderRequest.getQuantity();
        logger.info("Total quantity recieved for execution :" + qty);
        allocationPerUnit = getAllocationPerUnitQuantity(executeOrderRequest);
        scheduledExecutorService.execute(new ExecuteMarketOrder(lock, orderService, this, executeOrderRequest));
        scheduledExecutorService.execute(new ExecuteLimitOrder(lock, orderService, this, executeOrderRequest));
        scheduledExecutorService.shutdown();
        logger.info("Order Execution Processed");


    }

    private Integer getCount(List<OrderModel> orderModelList) {
        int count = 0;
        for (OrderModel orderModel : orderModelList) {
            count += orderModel.getOrderedQuantity();
        }
        return count;
    }


    public void executeOrderUnitWise(ExecuteOrderRequest executedOrder, OrderModel orderModel, boolean isMarket) {
        logger.info("Execution for order started for  :" + orderModel.toString());

        Boolean status = true;
        if (orderModel.getPrice() < executedOrder.getPrice() && isMarket == false) {
            status = false;
        }
        int alloted = getExecutedQuantity(orderModel, allocationPerUnit);

        if (qty - alloted >= 0) {
            if (status) {
                qty = qty - alloted;
            }
            Double executedPrice = orderModel.getPrice();
            if (isMarket) {
                executedPrice = executedOrder.getPrice();
            }
            int leftQuantity = orderModel.getOrderedQuantity() - alloted;
            ExecutedOrder executedOrdr = new ExecutedOrder(alloted, leftQuantity, executedPrice, orderModel.getId(), status, orderModel.getMarketOrder(), orderModel.getFinanceIntrumentId());
            executedOrderDao.save(executedOrdr);
            orderService.deleteOrder(orderModel);
            updateExecutedTable(executedOrder);
            logger.info("Execution for order completed for  :" + orderModel.toString());

//
        }
    }

    private int getExecutedQuantity(OrderModel orderModel, Double allocationPerUnitQuantity) {
        int allotedQuantity = 0;
        allotedQuantity = (int) (orderModel.getOrderedQuantity() * allocationPerUnitQuantity);
        logger.info("allocated qty : " + allotedQuantity);
        return allotedQuantity;
    }

    private void updateExecutedTable(ExecuteOrderRequest request) {

        int val = 0;
        ExecutedOrdertable executedOrdertable = null;
        try {
            executedOrdertable = executedStatsDao.findByExecutionPrice(request.getPrice());
            if (executedOrdertable != null) {
                logger.info("Table updated for executed order");
                val = executedStatsDao.updateExecutedOrder(request.getQuantity(), request.getPrice());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (val <= 0 && executedOrdertable == null) {
            executedStatsDao.save(new ExecutedOrdertable(request.getPrice(), request.getQuantity(), request.getFinanceInstrumentId()));
        }

    }

    public ExecutedOrder getbyId(Long id) {
        ExecutedOrder executedOrder = executedOrderDao.findExecutedOrdersByOrderId(id);
        return executedOrder;
    }

    public Double getAllocationPerUnitQuantity(ExecuteOrderRequest orderRequest) {

        List<OrderModel> marketOrder = orderService.getAllMarketOrder(orderRequest.getFinanceInstrumentId());
        List<OrderModel> limitOrder = orderService.getAllLimitOrder(orderRequest.getFinanceInstrumentId(), orderRequest.getPrice());
        int marktQty = getCount(marketOrder);
        int limittQty = getCount(limitOrder);
        Double availableQty = Double.valueOf(limittQty + marktQty);
        Double distribution = orderRequest.getQuantity() / availableQty;
        return distribution;
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
        return executedOrderDao.findAll(sortByIdAsc("allocatedQuantity"));
    }

    private Sort sortByIdAsc(String item) {
        return new Sort(Sort.Direction.DESC, item);
    }
}
