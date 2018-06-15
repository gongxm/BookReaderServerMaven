package com.gongxm.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gongxm.bean.UserConfig;
import com.gongxm.dao.Dao;
import com.gongxm.dao.UserConfigDao;
import com.gongxm.services.UserConfigService;
@Service()
@Transactional
public class UserConfigServiceImpl extends BaseService<UserConfig> implements UserConfigService {
	@Autowired
	private UserConfigDao dao;
	@Override
	public Dao<UserConfig> getDao() {
		return dao;
	}
	@Override
	public List<UserConfig> findAll() {
		return dao.findAll();
	}

}
