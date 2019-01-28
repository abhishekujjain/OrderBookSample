package com.ujjain.trade.dependencies.db.dao;

import com.ujjain.trade.dependencies.db.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface OrderDao extends JpaRepository<Order, Long> {


    @Query("from Order i where i.isMarketOrder =:params")
    List<Order> getAllByMarketOrder(boolean params);


    @Query("from Order i where i.financeIntrumentId =:finId and i.isMarketOrder =:params order by orderedQuantity asc")
    List<Order> getAllByFinId(Integer finId, Boolean params);


    @Query("from Order i where i.financeIntrumentId =:finId and i.isMarketOrder =false and  i.price >=:priceOrder  order by orderedQuantity asc")
    List<Order> getAllByFinIdLimitOrder(Integer finId, Double priceOrder);

    @Query("from Order i where i.financeIntrumentId =:finId and i.isMarketOrder =true order by orderedQuantity asc")
    List<Order> getAllByFinIdMarketOrder(Integer finId);


    @Transactional
    @Query("update Order u set u.price = ?1 where u.id = ?2")
    int updateUserData(double price, Long id);

//     List<Order> findAllByCreatedOnOrderByCreatedOnAsc();


    List<Order> findAllByFinanceIntrumentId(Integer financeId);
}
