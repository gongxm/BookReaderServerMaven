package com.gongxm.domain;

public class OpenIdResult {
	private int errcode;
	private String errmsg;
	private String session_key;
	private int expires_in;
	private String openid;
	
	public OpenIdResult() {
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

	public String getSession_key() {
		return session_key;
	}

	public void setSession_key(String session_key) {
		this.session_key = session_key;
	}

	public int getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
//	{"session_key":"TeE0CFtZ2kWS3dLIZA69SA==","expires_in":7200,"openid":"ocTjs0PcVEtUfsHxoPqUKGS75m_E"} //��ȷ״̬
	
//	{"errcode":40029,"errmsg":"invalid code, hints: [ req_id: SDLllA0708d997 ]"}						// ����״̬
}
