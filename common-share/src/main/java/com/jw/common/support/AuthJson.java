package com.jw.common.support;

public class AuthJson {
	/* access_token */
	private String access_token;
	
	/* 用于处理商户的回调处理*/
	private String callback_url;
	
	/* 商户APPID */
	private String mer_id;
	
	/* 商户的用户的guid，可以直接用openID代替*/
	private String guid;
	
	/* 用户授权完成后重定向指定URL（商户提供），需与http或https开始。*/
	private String redirect_url;
	
	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getCallback_url() {
		return callback_url;
	}

	public void setCallback_url(String callback_url) {
		this.callback_url = callback_url;
	}

	public String getMer_id() {
		return mer_id;
	}

	public void setMer_id(String mer_id) {
		this.mer_id = mer_id;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getRedirect_url() {
		return redirect_url;
	}

	public void setRedirect_url(String redirect_url) {
		this.redirect_url = redirect_url;
	}
}
