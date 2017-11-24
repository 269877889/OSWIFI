package com.yixuan.oswifi.model;

public class UserLogininfo {

	private int id;
	
	private String telephone;
	
	private String token;
	
	private String identifyingCode;
	
	private String createTime;
	
	private String modifyTime;
	
	private String logicstate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getIdentifyingCode() {
		return identifyingCode;
	}

	public void setIdentifyingCode(String identifyingCode) {
		this.identifyingCode = identifyingCode;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getLogicstate() {
		return logicstate;
	}

	public void setLogicstate(String logicstate) {
		this.logicstate = logicstate;
	}
	
	
}
