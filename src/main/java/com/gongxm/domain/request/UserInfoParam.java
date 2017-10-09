package com.gongxm.domain.request;

public class UserInfoParam{
	private String thirdSession;

	private String encryptedData;
	
	private String iv;

	public String getThirdSession() {
		return thirdSession;
	}

	public void setThirdSession(String thirdSession) {
		this.thirdSession = thirdSession;
	}

	public String getEncryptedData() {
		return encryptedData;
	}

	public void setEncryptedData(String encryptedData) {
		this.encryptedData = encryptedData;
	}

	public String getIv() {
		return iv;
	}

	public void setIv(String iv) {
		this.iv = iv;
	}
	
}
