package com.gongxm.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;

import com.gongxm.bean.User;

public class BeanFillUtils {
	public static User fillBean(HttpServletRequest request) {
		User user=new User();
		try {
			BeanUtils.populate(user, request.getParameterMap());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
}
