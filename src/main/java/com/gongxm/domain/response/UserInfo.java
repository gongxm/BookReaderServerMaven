package com.gongxm.domain.response;

import com.gongxm.bean.User;

public class UserInfo {
	private int errcode;
	private String errmsg;

	private String username;
	private String permissions;
	private String phone;
	private String nickName;// ": "NICKNAME",
	private String gender;// ": GENDER,
	private String city;// ": "CITY",
	private String province;// ": "PROVINCE",
	private String country;// ": "COUNTRY",
	private String avatarUrl;// ": 用户头像
	
	public void setUser(User user){
		this.username = user.getUsername();
		this.permissions = user.getPermissions();
		this.phone = user.getPhone();
		this.nickName = user.getNickName();
		this.gender = user.getGender();
		this.city = user.getCity();
		this.province = user.getProvince();
		this.country = user.getCountry();
		this.avatarUrl = user.getAvatarUrl();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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
