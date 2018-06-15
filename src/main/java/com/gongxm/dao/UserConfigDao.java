package com.gongxm.dao;

import java.util.List;

import com.gongxm.bean.UserConfig;

public interface UserConfigDao extends Dao<UserConfig> {

	List<UserConfig> findAll();

}
