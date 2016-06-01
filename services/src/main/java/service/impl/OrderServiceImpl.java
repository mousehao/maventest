package service.impl;

import common.model.Order;
import mybatis.IOrderDao;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.IOrderService;


/**
 * Created  on 2016/6/1.
 */
@Service("orderService")
public class OrderServiceImpl extends BaseServiceImpl<Order,String> implements IOrderService {

	private IOrderDao orderDao;

	@Autowired
	public void setOrderDao(IOrderDao orderDao) {
		this.baseDao=orderDao;
		this.orderDao = orderDao;
	}
}
