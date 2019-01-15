package com.ujjain.trade.dependencies.db.dao;

import com.ujjain.trade.dependencies.db.model.ExecutedOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ExecutedOrderDao extends JpaRepository<ExecutedOrder, Long> {

    ExecutedOrder findExecutedOrdersByOrderId(Long id);

//    List<ExecutedOrder> findAllByFinanceInstrument(int finId);

    @Query("from ExecutedOrder i where i.isValid =:params")
    List<ExecutedOrder> findAllByValidOrder(boolean params);

    @Transactional
    @Query("update ExecutedOrder u set u.isValid = ?1 where u.orderId = ?2")
    int updateExecutedOrder(boolean isValid, Long id);
}
