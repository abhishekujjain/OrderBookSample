package com.ujjain.trade.dependencies.db.dao;

import com.ujjain.trade.dependencies.db.model.LimitOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LimitOrderDao extends JpaRepository<LimitOrder, Long> {

    @Modifying
    @Query("update LimitOrder limitOrder set limitOrder.quantity = ?1  where limitOrder.price = ?2")
    int updateLimitOrder(int qty, double price);


    LimitOrder findByPrice(double price);
}
