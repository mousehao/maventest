package mybatis.impl;

import common.model.Order;
import mybatis.IOrderDao;
import org.springframework.stereotype.Repository;

/**
 * Created by Huawei on 2015/8/14.
 */
@Repository(value = "orderDao")
public class OrderDaoImpl extends BaseDaoImpl<Order, String>
		implements IOrderDao {
}
