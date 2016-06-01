package service.impl;

import common.model.User;
import mybatis.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.IUserService;


/**
 * Created  on 2016/6/1.
 */
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User,String> implements IUserService {
	private IUserDao userDao;

	@Autowired
	public void setOrderDao(IUserDao userDao) {
		this.baseDao=userDao;
		this.userDao = userDao;
	}
}
