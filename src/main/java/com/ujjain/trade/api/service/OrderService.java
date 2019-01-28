package com.ujjain.trade.api.service;

import com.ujjain.trade.api.model.FinacialInstrumentRequest;
import com.ujjain.trade.api.model.OrderRequest;
import com.ujjain.trade.dependencies.db.dao.OrderBookStatusDao;
import com.ujjain.trade.dependencies.db.dao.OrderDao;
import com.ujjain.trade.dependencies.db.dao.OrderRecievedDao;
import com.ujjain.trade.dependencies.db.model.FinancialInstrument;
import com.ujjain.trade.dependencies.db.model.Order;
import com.ujjain.trade.dependencies.db.model.OrderRecieved;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderBookServices.class);

    final OrderDao orderDao;

    final
    OrderBookStatusDao orderBookStatusDao;

    final
    OrderRecievedDao orderRecievedDao;

    @Autowired
    public OrderService(OrderDao orderDao, OrderBookStatusDao orderBookStatusDao, OrderRecievedDao orderRecievedDao) {
        this.orderDao = orderDao;
        this.orderBookStatusDao = orderBookStatusDao;
        this.orderRecievedDao = orderRecievedDao;
    }


     public String addOrder(OrderRequest orderRequest) {

            FinancialInstrument financialInstrument = orderBookStatusDao.findByInstrumentId(orderRequest.getFinanceInstrumentId());
            if (financialInstrument != null && financialInstrument.getStatus()) {
                Order order = new Order(orderRequest.getQuantity(), orderRequest.getPrice(), orderRequest.isMarket(), orderRequest.getFinanceInstrumentId());
                updateOrderRecieveTable(order);
                if (orderRequest.isMarket() == false && order.getPrice() <= 0) {
                    logger.info("Not valid order");
                    return "Price Not valid order";
                }
                orderDao.save(order);

                return order.toString();
            } else if (financialInstrument == null) {
                return "Instrument does not exist";
            } else {
                return "Instrument is closed";

            }

    }

    private void updateOrderRecieveTable(Order order) {
        if (order.getMarketOrder() != null && order.getMarketOrder())
        {
            logger.info("No entry for market order");
            return;
        }

        OrderRecieved orderRecieved = null;

        orderRecieved = orderRecievedDao.findByPriceAndFinancialId(order.getPrice(), order.getFinanceIntrumentId());
            if (orderRecieved != null) {
                logger.info("Limit order table updated");
                orderRecievedDao.updateLimitOrder(orderRecieved.getQuantity() + order.getOrderedQuantity(), order.getPrice(), order.getFinanceIntrumentId());
            }

        if (orderRecieved == null) {
            orderRecievedDao.save(new OrderRecieved(order.getPrice(), order.getOrderedQuantity(), order.getFinanceIntrumentId()));
        }

    }

    public List<Order> getAll() {
        return orderDao.findAll();
    }

    public List<Order> getAllByInstrumentId(int financeInstrumentId, boolean isMarketOrder) {
        return orderDao.getAllByFinId(financeInstrumentId, isMarketOrder);
    }

    public List<Order> getAllWithFilter() {
        return orderDao.findAll(sortByIdAsc("createdOn"));
    }

    public List<Order> getAllWithFilterQty() {
        return orderDao.findAll(sortByIdAsc("orderedQuantity"));
    }

    public List<Order> getAllLimitOrder(Integer id, Double orderPrice) {
        return orderDao.getAllByFinIdLimitOrder(id, orderPrice);
    }

    public List<Order> getAllMarketOrder(Integer id) {
        return orderDao.getAllByFinIdMarketOrder(id);
    }


    public List<Order> getAll(boolean params) {
        return orderDao.getAllByMarketOrder(params);
    }

    public List<Order> getAllByFinId(int finId) {
        return orderDao.findAllByFinanceIntrumentId(finId);
    }

    public void deleteOrder(Order order) {
        orderDao.delete(order);
    }


    private Sort sortByIdAsc(String item) {
        return new Sort(Sort.Direction.DESC, item);
    }

    @Transactional
    @Modifying
    public FinancialInstrument updateInstrumentStatus(int finId, boolean status) {
        logger.info("Oderbooks status updated for " + finId + " to " + status);
        orderBookStatusDao.updateInstrumentStatus(status, finId);
        return orderBookStatusDao.findByInstrumentId(finId);
    }


    public FinancialInstrument findInstrumentById(int finId) {
        return orderBookStatusDao.findByInstrumentId(finId);
    }


    public List<FinancialInstrument> getOrderBookList() {
        return orderBookStatusDao.findAll();
    }

    public FinancialInstrument addInstrument(FinacialInstrumentRequest financialInstrument) {
        FinancialInstrument instrument = null;
        if (orderBookStatusDao.findByInstrumentId(financialInstrument.getInstrumentId()) == null) {
            instrument = orderBookStatusDao.save(new FinancialInstrument(financialInstrument.getInstrumentId(), financialInstrument.getStatus()));
        }

        return instrument;
    }

    public String getbyId(Long id) {
        Optional<Order> order = orderDao.findById(id);
        if(order==null)
        {
            return "Order is not present";
        }

        return order.toString();

    }
}
