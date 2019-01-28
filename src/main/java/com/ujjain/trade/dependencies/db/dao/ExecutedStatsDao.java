package com.ujjain.trade.dependencies.db.dao;

import com.ujjain.trade.dependencies.db.model.ExecutedOrdertable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ExecutedStatsDao extends JpaRepository<ExecutedOrdertable, Long> {

    @Transactional
    @Modifying
    @Query("update ExecutedOrdertable limitOrder set limitOrder.allotedQuantity = ?1  where limitOrder.executionPrice = ?2")
    int updateExecutedOrder(Integer qty, Double executionPrice);


    ExecutedOrdertable findByExecutionPrice(Double executionPrice);
 }
