package com.ujjain.trade.dependencies.db.dao;

import com.ujjain.trade.dependencies.db.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface OrderDao extends JpaRepository<OrderModel, Long> {


    @Query("from OrderModel i where i.isMarketOrder =:params")
    public List<OrderModel> getAllByMarketOrder(boolean params);


    @Query("from OrderModel i where i.financeIntrumentId =:finId and i.isMarketOrder =:params order by createdOn")
    public List<OrderModel> getAllByFinId(int finId ,boolean params);


    @Transactional
    @Query("update OrderModel u set u.price = ?1 where u.id = ?2")
    int updateUserData(double price, Long id);

//     List<OrderModel> findAllByCreatedOnOrderByCreatedOnAsc();


    OrderModel findAllById(Long id);
}
