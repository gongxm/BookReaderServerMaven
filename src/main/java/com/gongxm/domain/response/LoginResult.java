package com.gongxm.domain.response;

public class LoginResult {
	private String thirdSession;
	private int errcode;
	private String errmsg;

	public String getThirdSession() {
		return thirdSession;
	}

	public void setThirdSession(String thirdSession) {
		this.thirdSession = thirdSession;
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

}
