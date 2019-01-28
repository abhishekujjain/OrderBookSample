package com.ujjain.trade.dependencies.db.dao;

import com.ujjain.trade.dependencies.db.model.FinancialInstrument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface OrderBookStatusDao extends JpaRepository<FinancialInstrument, Long> {

    @Modifying
    @Query("update FinancialInstrument instrumet set instrumet.status = ?1  where instrumet.instrumentId = ?2")
    int updateInstrumentStatus(boolean status, Integer insId);

    FinancialInstrument findByInstrumentId(Integer id);


 }
