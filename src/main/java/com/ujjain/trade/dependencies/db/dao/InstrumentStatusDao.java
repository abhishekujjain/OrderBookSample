package com.ujjain.trade.dependencies.db.dao;

import com.ujjain.trade.dependencies.db.model.InstrumentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface InstrumentStatusDao extends JpaRepository<InstrumentStatus, Long> {

    @Modifying
    @Query("update InstrumentStatus instrumet set instrumet.status = ?1  where instrumet.instrumentId = ?2")
    int updateInstrumentStatus(boolean status, Integer insId);

    InstrumentStatus findByInstrumentId(Integer id);


 }
