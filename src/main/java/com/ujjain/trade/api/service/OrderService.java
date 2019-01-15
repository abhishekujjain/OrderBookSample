package com.ujjain.trade.api.service;

import com.ujjain.trade.api.model.OrderRequest;
import com.ujjain.trade.dependencies.db.dao.InstrumentStatusDao;
import com.ujjain.trade.dependencies.db.dao.LimitOrderDao;
import com.ujjain.trade.dependencies.db.dao.OrderDao;
import com.ujjain.trade.dependencies.db.model.ExecutedOrder;
import com.ujjain.trade.dependencies.db.model.InstrumentStatus;
import com.ujjain.trade.dependencies.db.model.LimitOrder;
import com.ujjain.trade.dependencies.db.model.OrderModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderService {


    @Autowired
    OrderDao orderDao;

    @Autowired
    InstrumentStatusDao instrumentStatusDao;

    @Autowired
    LimitOrderDao limitOrderDao;





    @Transactional
    public String addOrder(OrderRequest orderRequest) {

        try {
            InstrumentStatus instrumentStatus = instrumentStatusDao.findByInstrumentId(orderRequest.getFinanceInstrumentId());
            if (instrumentStatus != null && instrumentStatus.isStatus()) {
                OrderModel orderModel = new OrderModel(orderRequest.getQuantity(), orderRequest.getPrice(), orderRequest.isMarket(), orderRequest.getFinanceInstrumentId(), orderRequest.isSell());
                updateLimitTable(orderModel);
                 orderDao.save(orderModel);
                 return orderModel.toString();
            } else {
                return "Instrument id closed";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Something went wrong";

        }
    }

    private void updateLimitTable(OrderModel orderModel) {

        int val = 0;
        LimitOrder limitOrder=null;
        try {
            limitOrder = limitOrderDao.findByPrice(orderModel.getPrice());
            if (limitOrder!=null) {
                val = limitOrderDao.updateLimitOrder(limitOrder.getQuantity()+orderModel.getQuantity(), orderModel.getPrice());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (val <= 0 && limitOrder == null ) {
            limitOrderDao.save(new LimitOrder(orderModel.getPrice(), orderModel.getQuantity()));
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
        return orderDao.findAll(sortByIdAsc("quantity"));
    }





    public List<OrderModel> getAll(boolean params) {
        return orderDao.getAllByMarketOrder(params);
    }

    public void deleteOrder(OrderModel orderModel) {
        orderDao.delete(orderModel);
    }

    public int updateOrderOld(double price, Long id) {
        return orderDao.updateUserData(price, id);
    }

    public void updateOrderBookstatus(boolean state) {

//        orderBookStatusDao.saveAndFlush(new OrderBookStatus(state));
    }

    private Sort sortByIdAsc(String item) {
        return new Sort(Sort.Direction.DESC, item);
    }

    @Transactional
    @Modifying
    public List<InstrumentStatus> updateInstrumentStatus(int finId, boolean status) {
        int val = instrumentStatusDao.updateInstrumentStatus(status, finId);
        return instrumentStatusDao.findAll();
    }


    @Transactional
    @Modifying
    public OrderModel updateOrder(Long id, double price) {
        int val = orderDao.updateUserData(price, id);
        return orderDao.findAllById(id);
    }


    public InstrumentStatus findInstrumentById(int finId) {
        return instrumentStatusDao.findByInstrumentId(finId);
    }


    public void updateFinanceInstrumentId() {
        System.out.println("Test updateFinanceInstrumentId");

        instrumentStatusDao.save(new InstrumentStatus(25, true));
        instrumentStatusDao.save(new InstrumentStatus(4, true));
        instrumentStatusDao.save(new InstrumentStatus(7, true));
    }

}
