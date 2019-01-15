package com.ujjain.trade;

import com.ujjain.trade.dependencies.db.dao.InstrumentStatusDao;
import com.ujjain.trade.dependencies.db.model.InstrumentStatus;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
public class TradeApplicationClass {



    @Autowired
    InstrumentStatusDao instrumentStatusDao;
    public static void main(String[] args) {

        SpringApplication.run(TradeApplicationClass.class, args);
       }


}
