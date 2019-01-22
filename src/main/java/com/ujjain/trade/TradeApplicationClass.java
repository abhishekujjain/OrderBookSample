package com.ujjain.trade;

import com.ujjain.trade.dependencies.db.dao.OrderBookStatusDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class TradeApplicationClass {

    private static final Logger logger = LoggerFactory.getLogger(TradeApplicationClass.class);


    @Autowired
    OrderBookStatusDao orderBookStatusDao;
    public static void main(String[] args) {

        SpringApplication.run(TradeApplicationClass.class, args);
       }


}
