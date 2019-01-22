package com.ujjain.trade.api.service;

import com.ujjain.trade.api.model.OrderRequest;
import com.ujjain.trade.dependencies.db.dao.OrderBookStatusDao;
import com.ujjain.trade.dependencies.db.dao.OrderDao;
import com.ujjain.trade.dependencies.db.dao.OrderRecievedDao;
import com.ujjain.trade.dependencies.db.model.OrderBookTable;
import com.ujjain.trade.dependencies.db.model.OrderModel;
import com.ujjain.trade.dependencies.db.model.OrderRecieved;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderBookServices.class);

    @Autowired
    OrderDao orderDao;

    @Autowired
    OrderBookStatusDao orderBookStatusDao;

    @Autowired
    OrderRecievedDao orderRecievedDao;


    @Transactional
    public String addOrder(OrderRequest orderRequest) {

        try {
            OrderBookTable orderBookTable = orderBookStatusDao.findByInstrumentId(orderRequest.getFinanceInstrumentId());
            if (orderBookTable != null && orderBookTable.getStatus()) {
                OrderModel orderModel = new OrderModel(orderRequest.getQuantity(), orderRequest.getPrice(), orderRequest.getFinanceInstrumentId(), orderRequest.isMarket());
                updateOrderRecieveTable(orderModel);
                if (orderRequest.isMarket()==false && orderModel.getPrice()<=0) {
                    logger.info("Not valid order");
                    return "Not valid order";
                }
                orderDao.save(orderModel);

                return orderModel.toString();
            } else {
                logger.info("Instrument id closed");
                return "Instrument id closed";
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Something went wrong while adding order " + e.getMessage());
            return "Something went wrong";

        }
    }

    private void updateOrderRecieveTable(OrderModel orderModel) {
        if(orderModel.getMarketOrder()!=null && orderModel.getMarketOrder() )
        {
            logger.info("No entry for market order");
            return;
        }

        int val = 0;
        OrderRecieved orderRecieved = null;
        try {
            orderRecieved = orderRecievedDao.findByPriceAndFinancialId(orderModel.getPrice(),orderModel.getFinanceIntrumentId());
            if (orderRecieved != null) {
                logger.info("Limit order table updated");
                val = orderRecievedDao.updateLimitOrder(orderRecieved.getQuantity() + orderModel.getOrderedQuantity(), orderModel.getPrice(),orderModel.getFinanceIntrumentId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (val <= 0 && orderRecieved == null) {
            orderRecievedDao.save(new OrderRecieved(orderModel.getPrice(), orderModel.getOrderedQuantity(), orderModel.getFinanceIntrumentId()));
        }

    }

    public List<OrderModel> getAll() {
        return orderDao.findAll();
    }

    public List<OrderModel> getAllByInstrumentId(int financeInstrumentId, boolean isMarketOrder) {
        return orderDao.getAllByFinId(financeInstrumentId, isMarketOrder);
    }

    public List<OrderModel> getAllWithFilter() {
        return orderDao.findAll(sortByIdAsc("createdOn"));
    }

    public List<OrderModel> getAllWithFilterQty() {
        return orderDao.findAll(sortByIdAsc("orderedQuantity"));
    }

    public List<OrderModel> getAllLimitOrder(Integer id, Double orderPrice) {
        return orderDao.getAllByFinIdLimitOrder(id, orderPrice);
    }

    public List<OrderModel> getAllMarketOrder(Integer id) {
        return orderDao.getAllByFinIdMarketOrder(id);
    }




    public List<OrderModel> getAll(boolean params) {
        return orderDao.getAllByMarketOrder(params);
    }

    public List<OrderModel> getAllByFinId(int finId) {
        return orderDao.findAllByFinanceIntrumentId(finId);
    }

    public void deleteOrder(OrderModel orderModel) {
        orderDao.delete(orderModel);
    }


    private Sort sortByIdAsc(String item) {
        return new Sort(Sort.Direction.DESC, item);
    }

    @Transactional
    @Modifying
    public List<OrderBookTable> updateInstrumentStatus(int finId, boolean status) {
        logger.info("Oderbooks status updated for " + finId + " to " + status);
        orderBookStatusDao.updateInstrumentStatus(status, finId);
        return orderBookStatusDao.findAll();
    }



    public OrderBookTable findInstrumentById(int finId) {
        return orderBookStatusDao.findByInstrumentId(finId);
    }


    public void updateFinanceInstrumentId() {
        logger.info("Test updateFinanceInstrumentId");

        orderBookStatusDao.save(new OrderBookTable(25, true));
        orderBookStatusDao.save(new OrderBookTable(4, true));
        orderBookStatusDao.save(new OrderBookTable(7, true));
    }


    public List<OrderBookTable> getOrderBookList() {
        return orderBookStatusDao.findAll();
    }

    public OrderBookTable addInstrument(OrderBookTable bookTable) {
        return orderBookStatusDao.save(bookTable);
    }
}
