package com.ujjain.trade.dependencies.db.dao;

import com.ujjain.trade.dependencies.db.model.OrderRecieved;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface OrderRecievedDao extends JpaRepository<OrderRecieved, Long> {

    @Modifying
    @Query("update OrderRecieved limitOrder set limitOrder.quantity = ?1  where limitOrder.price = ?2 and limitOrder.financialId = ?3")
    int updateLimitOrder(int qty, double price, int financeIntrumentId);


    OrderRecieved findByPriceAndFinancialId(double price,int id);
}
