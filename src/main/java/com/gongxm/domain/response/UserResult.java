package com.gongxm.domain.response;

import java.io.Serializable;

public class UserResult implements Serializable {
	private static final long serialVersionUID = 1L;
	private String openId;

	private String nickName;// ": "NICKNAME",
	private String gender;// ": GENDER,
	private String city;// ": "CITY",
	private String province;// ": "PROVINCE",
	private String country;// ": "COUNTRY",
	private String avatarUrl;// ": LOGO

	public UserResult() {
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
}
