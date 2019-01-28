package com.ujjain.trade.dependencies.db.dao;

import com.ujjain.trade.dependencies.db.model.OrderBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderBookDao extends JpaRepository<OrderBook,Long> {
}
