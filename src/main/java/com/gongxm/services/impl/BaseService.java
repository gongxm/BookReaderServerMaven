package com.gongxm.services.impl;

import java.io.Serializable;

import org.springframework.transaction.annotation.Transactional;

import com.gongxm.dao.Dao;
import com.gongxm.services.Service;
@Transactional
public abstract class BaseService<T> implements Service<T> {

	@Override
	public void add(T t) {
		getDao().add(t);
	}

	@Override
	public void update(T t) {
		getDao().update(t);
	}

	@Override
	public void delete(Serializable id) {
		getDao().delete(id);
	}

	@Override
	public T findById(Serializable id) {
		return getDao().findById(id);
	}

	public abstract Dao<T> getDao();
}
