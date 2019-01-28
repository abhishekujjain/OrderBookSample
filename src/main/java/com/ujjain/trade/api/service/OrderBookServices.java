package com.ujjain.trade.api.service;

import com.ujjain.trade.api.Utils.ExecuteLimitOrder;
import com.ujjain.trade.api.Utils.ExecuteMarketOrder;
import com.ujjain.trade.api.model.ExecuteOrderRequest;
import com.ujjain.trade.dependencies.db.dao.ExecutedOrderDao;
import com.ujjain.trade.dependencies.db.dao.ExecutedStatsDao;
import com.ujjain.trade.dependencies.db.dao.OrderBookDao;
import com.ujjain.trade.dependencies.db.dao.OrderBookStatusDao;
import com.ujjain.trade.dependencies.db.model.*;
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

    final ExecutedOrderDao executedOrderDao;
    final OrderService orderService;
    final OrderBookStatusDao orderBookStatusDao;
    final ExecutedStatsDao executedStatsDao;
    final OrderBookDao orderBookDao;

    private volatile int qty = 0;
    private Double allocationPerUnit = 0.0;

    @Autowired
    public OrderBookServices(ExecutedOrderDao executedOrderDao, OrderService orderService, OrderBookStatusDao orderBookStatusDao, ExecutedStatsDao executedStatsDao, OrderBookDao orderBookDao) {
        this.executedOrderDao = executedOrderDao;
        this.orderService = orderService;
        this.orderBookStatusDao = orderBookStatusDao;
        this.executedStatsDao = executedStatsDao;
        this.orderBookDao = orderBookDao;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Transactional
    public String executeOrder(ExecuteOrderRequest executeOrderRequest) {


        FinancialInstrument financialInstrument = orderBookStatusDao.findByInstrumentId(executeOrderRequest.getFinanceInstrumentId());
        if (financialInstrument != null && financialInstrument.getStatus() == false) {
                callExecuteOrder(executeOrderRequest);
            orderBookDao.save(new OrderBook(executeOrderRequest.getQuantity(), executeOrderRequest.getPrice(), executeOrderRequest.getQuantity(), true));
        } else if (financialInstrument == null) {
            return "finance Instrument not present";
        } else {
            return "finance Instrument still open ! Can't execute open orderbook";

        }

        return "Order Executed";
    }

    public void callExecuteOrder(ExecuteOrderRequest executeOrderRequest) {
        List<Order> orderList = orderService.getAllByInstrumentId(executeOrderRequest.getFinanceInstrumentId(), false);

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        Object lock = new Object();
        qty = executeOrderRequest.getQuantity();
        logger.info("Total quantity recieved for execution :" + qty);
        allocationPerUnit = getAllocationPerUnitQuantity(executeOrderRequest);
        scheduledExecutorService.execute(new ExecuteMarketOrder(lock,orderService, this, executeOrderRequest));
        scheduledExecutorService.execute(new ExecuteLimitOrder(lock,orderService, this, executeOrderRequest));
        scheduledExecutorService.shutdown();
        logger.info("Order Execution Processed");


    }

    private Integer getCount(List<Order> orderList) {
        int count = 0;
        for (Order order : orderList) {
            count += order.getOrderedQuantity();
        }
        return count;
    }


    @Transactional
    public void executeOrderUnitWise(ExecuteOrderRequest executedOrder, Order order, boolean isMarket) {
        logger.info("Execution for order started for  :" + order.toString());

        Boolean status = true;
        if (order.getPrice() < executedOrder.getPrice() && isMarket == false) {
            status = false;
        }
        int alloted = getExecutedQuantity(order, allocationPerUnit);

        if (qty - alloted >= 0) {
            if (status) {
                qty = qty - alloted;
            }
            Double executedPrice = order.getPrice();
            if (isMarket) {
                executedPrice = executedOrder.getPrice();
            }
            int leftQuantity = order.getOrderedQuantity() - alloted;
            ExecutedOrder executedOrdr = new ExecutedOrder(alloted, order.getOrderedQuantity(), executedPrice, order.getId(), status, order.getMarketOrder(), order.getFinanceIntrumentId());
            executedOrderDao.save(executedOrdr);
            orderService.deleteOrder(order);
            updateExecutedTable(executedOrder);
            logger.info("Execution for order completed for  :" + order.toString());

        }
//

    }

    private int getExecutedQuantity(Order order, Double allocationPerUnitQuantity) {
        int allotedQuantity = 0;
        allotedQuantity = (int) (order.getOrderedQuantity() * allocationPerUnitQuantity);
        logger.info("allocated qty : " + allotedQuantity);
        return allotedQuantity;
    }

     void updateExecutedTable(ExecuteOrderRequest request) {

        ExecutedOrdertable executedOrdertable = null;
        executedOrdertable = executedStatsDao.findByExecutionPrice(request.getPrice());
            if (executedOrdertable != null) {
                logger.info("Table updated for executed order");
                executedStatsDao.updateExecutedOrder(executedOrdertable.getAllotedQuantity() + request.getQuantity(), request.getPrice());
            }else if (executedOrdertable == null) {
                logger.info("Table added for executed order");
                executedStatsDao.save(new ExecutedOrdertable(request.getPrice(), request.getQuantity(), request.getFinanceInstrumentId()));
        }


    }

    public ExecutedOrder getbyId(Long id) {
        ExecutedOrder executedOrder = executedOrderDao.findExecutedOrdersByOrderId(id);
        return executedOrder;
    }

    public Double getAllocationPerUnitQuantity(ExecuteOrderRequest orderRequest) {

        List<Order> marketOrder = orderService.getAllMarketOrder(orderRequest.getFinanceInstrumentId());
        List<Order> limitOrder = orderService.getAllLimitOrder(orderRequest.getFinanceInstrumentId(), orderRequest.getPrice());
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
