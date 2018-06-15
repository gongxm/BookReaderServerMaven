package com.gongxm.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import com.gongxm.bean.UserConfig;
import com.gongxm.dao.UserConfigDao;
@Repository
public class UserConfigDaoImpl extends BaseDao<UserConfig> implements UserConfigDao {

	@Override
	public List<UserConfig> findAll() {
		String sql = "select * from user_config";
		try {
			return qr.query(sql, new BeanListHandler<UserConfig>(UserConfig.class));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
