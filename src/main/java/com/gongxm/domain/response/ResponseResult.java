package com.gongxm.domain.response;

import com.gongxm.utils.MyConstants;
import com.gongxm.utils.StringConstants;
import com.google.gson.annotations.Expose;

public class ResponseResult {
	@Expose
	private int errcode;
	@Expose
	private String errmsg;
	@Expose
	private Object result;

	public ResponseResult() {
		this.errcode = MyConstants.FAILURE;
		this.errmsg = StringConstants.HTTP_REQUEST_ERROR;
	}

	public ResponseResult(int errcode, String errmsg) {
		this.errcode=errcode;
		this.errmsg=errmsg;
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

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "ResponseResult [errcode=" + errcode + ", errmsg=" + errmsg + ", result=" + result + "]";
	}

	public void setSuccess() {
		this.errcode = MyConstants.SUCCESS;
		this.errmsg = StringConstants.HTTP_REQUEST_SUCCESS;
	}

}
