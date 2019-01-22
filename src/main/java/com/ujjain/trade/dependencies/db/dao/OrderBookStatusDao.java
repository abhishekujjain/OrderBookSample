package com.ujjain.trade.dependencies.db.dao;

import com.ujjain.trade.dependencies.db.model.OrderBookTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface OrderBookStatusDao extends JpaRepository<OrderBookTable, Long> {

    @Modifying
    @Query("update OrderBookTable instrumet set instrumet.status = ?1  where instrumet.instrumentId = ?2")
    int updateInstrumentStatus(boolean status, Integer insId);

    OrderBookTable findByInstrumentId(Integer id);


 }
