package com.gongxm.dao;

import com.gongxm.bean.User;

public interface UserDao extends Dao<User> {

	User findUserByName(String username);

	User findUser(String username, String password);

	User findUserByThirdSession(String thirdSession);

	User findUserByOpenId(String openid);

}
