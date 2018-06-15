package com.gongxm.domain.response;

import java.util.List;

import com.gongxm.bean.Book;
import com.gongxm.utils.MyConstants;
import com.gongxm.utils.StringConstants;

public class UpdateUserBookStoreResp {
	private int errcode;
	private String errmsg;
	private List<Book> list;
	private Object setting;

	public UpdateUserBookStoreResp() {
		this.errcode = MyConstants.FAILURE;
		this.errmsg = StringConstants.HTTP_REQUEST_ERROR;
	}

	public UpdateUserBookStoreResp(List<Book> list, Object setting) {
		this();
		this.list = list;
		this.setting = setting;
	}

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public List<Book> getList() {
		return list;
	}

	public void setList(List<Book> list) {
		this.list = list;
	}

	public Object getSetting() {
		return setting;
	}

	public void setSetting(Object setting) {
		this.setting = setting;
	}

	public void setSuccess() {
		this.errcode = MyConstants.SUCCESS;
		this.errmsg = StringConstants.HTTP_REQUEST_SUCCESS;
	}
}
