package com.gongxm.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "user_config")
public class UserConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	@Type(type = "text")
	@Column
	private String store; // 书架信息
	@Type(type = "text")
	@Column
	private String setting;// 设置信息

	public UserConfig() {
		super();
	}

	public UserConfig(String id, String store, String setting) {
		super();
		this.id = id;
		this.store = store;
		this.setting = setting;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public String getSetting() {
		return setting;
	}

	public void setSetting(String setting) {
		this.setting = setting;
	}

}
