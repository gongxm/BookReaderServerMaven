package com.gongxm.dao;

import java.io.Serializable;

public interface Dao<T> {
	public abstract void add(T t);
	public abstract void update(T t);
	public abstract void delete(Serializable id);
	public abstract T findById(Serializable id);
}
