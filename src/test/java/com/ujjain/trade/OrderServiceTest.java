package com.ujjain.trade;

import com.ujjain.trade.dependencies.db.dao.OrderDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class OrderServiceTest {


//    @Autowired
//    OrderService orderService;

    @Autowired
    OrderDao orderDao;

    @Test
    public void testDataEntry() {

//       Assert.assertTrue("true", orderDao.save(new Order(22, 323.23, 25, true, true))!=null);
//       Order orderModel=orderDao.findAllById(2L);
//       Assert.assertNotNull(orderDao.findAllById(2L));
//       Assert.assertFalse("true", (orderDao.updateUserData(21.00,2L))==1);
    }


}
