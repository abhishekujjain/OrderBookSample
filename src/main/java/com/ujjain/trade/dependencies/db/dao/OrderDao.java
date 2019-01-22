package com.ujjain.trade.dependencies.db.dao;

import com.ujjain.trade.dependencies.db.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface OrderDao extends JpaRepository<OrderModel, Long> {


    @Query("from OrderModel i where i.isMarketOrder =:params")
    List<OrderModel> getAllByMarketOrder(boolean params);


    @Query("from OrderModel i where i.financeIntrumentId =:finId and i.isMarketOrder =:params order by orderedQuantity asc")
    List<OrderModel> getAllByFinId(Integer finId, Boolean params);


    @Query("from OrderModel i where i.financeIntrumentId =:finId and i.isMarketOrder =false and  i.price >=:priceOrder  order by orderedQuantity asc")
    List<OrderModel> getAllByFinIdLimitOrder(Integer finId,Double priceOrder);

    @Query("from OrderModel i where i.financeIntrumentId =:finId and i.isMarketOrder =true order by orderedQuantity asc")
    List<OrderModel> getAllByFinIdMarketOrder(Integer finId);


    @Transactional
    @Query("update OrderModel u set u.price = ?1 where u.id = ?2")
    int updateUserData(double price, Long id);

//     List<OrderModel> findAllByCreatedOnOrderByCreatedOnAsc();


    List<OrderModel> findAllByFinanceIntrumentId(Integer financeId);
}
