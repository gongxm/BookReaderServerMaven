package com.gongxm.services;

import com.gongxm.bean.User;

public interface UserService extends Service<User>{

	User findUserByName(String username);

	void addUser(User user);

	User findUser(String username, String password);

	User findUserByThirdSession(String thirdSession);

	User findUserByOpenId(String openid);

}
