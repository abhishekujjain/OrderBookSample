package com.ujjain.trade;

import com.ujjain.trade.api.model.OrderRequest;
import com.ujjain.trade.api.service.OrderService;
import com.ujjain.trade.dependencies.db.dao.LimitOrderDao;
import com.ujjain.trade.dependencies.db.model.LimitOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Autowired
    LimitOrderDao limitOrderDao;

    @Before
    public void setUp() {
        orderService = new OrderService();

    }

    @Test
    public void name() {
    }

    @Test
    public void testOrderBookImmutable() {

        orderService.addOrder(new OrderRequest(1223, 255, 44, true, true));
        long k = 5;
//        OrderModel orderModel = orderService.getAll(k);
//        orderModel.setPrice(1);
//        assertNotEquals("failed", orderService.updateOrder(7777,k), 1);
    }


    @Test
    public void testLimitOrder() {

//        orderService.addOrder(new OrderRequest(3233, 445.44, 25, true, true));
        limitOrderDao.save(new LimitOrder(439,44));
    }

}
