package com.ujjain.trade.dependencies.db.dao;

import com.ujjain.trade.dependencies.db.model.ExecutedOrdertable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ExecutedStatsDao extends JpaRepository<ExecutedOrdertable, Long> {

    @Modifying
    @Query("update ExecutedOrdertable limitOrder set limitOrder.quantity = ?1  where limitOrder.price = ?2")
    int updateExecutedOrder(int qty, double price);


    ExecutedOrdertable findByPrice(double price);
}
