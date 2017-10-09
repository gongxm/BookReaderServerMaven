package com.gongxm.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.gongxm.bean.User;
import com.gongxm.dao.UserDao;

@Repository("userDao")
public class UserDaoImpl extends BaseDao<User> implements UserDao {

	@Override
	public User findUserByName(String username) {
		String sql = "select * from user where username=?";
		User user = null;
		try {
			user = sqlObj.queryForObject(sql, new UserMap(), username);
		} catch (DataAccessException e) {
		}
		return user;
	}

	@Override
	public User findUser(String username, String password) {
		String sql = "select * from user where username=? and password=?";
		User user = null;
		try {
			user = sqlObj.queryForObject(sql, new UserMap(), username, password);
		} catch (DataAccessException e) {
		}
		return user;
	}

	@Override
	public User findUserByThirdSession(String thirdSession) {
		String sql = "select * from user where thirdSession=?";
		User user = null;
		try {
			user = sqlObj.queryForObject(sql, new UserMap(), thirdSession);
		} catch (DataAccessException e) {
		}
		return user;
	}

	@Override
	public User findUserByOpenId(String openid) {
		String sql = "select * from user where openid=?";
		User user = null;
		try {
			user = sqlObj.queryForObject(sql, new UserMap(), openid);
		} catch (DataAccessException e) {
		}
		return user;
	}

	class UserMap implements RowMapper<User> {
		@Override
		public User mapRow(ResultSet rs, int index) throws SQLException {
			User user = new User();
			user.setAvatarUrl(rs.getString("avatarUrl"));
			user.setCity(rs.getString("city"));
			user.setCountry(rs.getString("country"));
			user.setId(rs.getInt("id"));
			user.setGender(rs.getString("gender"));
			user.setNickName(rs.getString("nickName"));
			user.setOpenid(rs.getString("openid"));
			user.setPassword(rs.getString("password"));
			user.setPermissions(rs.getString("permissions"));
			user.setPhone(rs.getString("phone"));
			user.setProvince(rs.getString("province"));
			user.setRegistTime(rs.getDate("registTime"));
			user.setSession_key(rs.getString("session_key"));
			user.setThirdSession(rs.getString("thirdSession"));
			user.setUsername(rs.getString("username"));
			return user;
		}

	}
}
