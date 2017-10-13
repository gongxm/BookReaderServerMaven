package com.gongxm.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.gongxm.dao.Dao;

public class BaseDao<T> extends HibernateDaoSupport implements Dao<T> {
	private Class<?> clazz;
	protected HibernateTemplate hqlObj; // hql执行对象
	@Autowired
	protected JdbcTemplate sqlObj; // 普通sql执行对象

	public BaseDao() {
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		this.clazz = (Class<?>) type.getActualTypeArguments()[0];
	}

	@Autowired
	@Qualifier("sessionFactory")
	public void setSuperSessionFactory(SessionFactory factory) {
		super.setSessionFactory(factory);
		this.hqlObj = getHibernateTemplate();
	}

	@Override
	public void add(T t) {
		hqlObj.save(t);
	}

	@Override
	public void update(T t) {
		hqlObj.update(t);
	}

	/**
	 * 删除元素
	 */
	@Override
	public void delete(Serializable id) {
		hqlObj.delete(id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public T findById(Serializable id) {
		return (T) hqlObj.get(clazz, id);
	}
	

}
