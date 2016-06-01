package mybatis.impl;

import common.model.User;
import mybatis.IUserDao;
import org.springframework.stereotype.Repository;

/**
 * Created by Huawei on 2015/8/14.
 */
@Repository(value = "userDao")
public class UserDaoImpl extends BaseDaoImpl<User, String>
		implements IUserDao {
}
