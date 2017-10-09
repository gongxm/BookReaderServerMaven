package com.gongxm.services;

import java.io.Serializable;
public interface Service<T> {
	public abstract void add(T t);
	public abstract void update(T t);
	public abstract void delete(Serializable id);
	public abstract T findById(Serializable id);
}
