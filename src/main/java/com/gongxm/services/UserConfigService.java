package com.gongxm.services;

import java.util.List;

import com.gongxm.bean.UserConfig;

public interface UserConfigService extends Service<UserConfig> {

	List<UserConfig> findAll();

}
