package com.yixuan.oswifi.model;

public class VerificationCodeInfo {

	private int id;
	
	private String telephone;
	
	private String identifyingCode;

	private int logicstate;

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

	public String getIdentifyingCode() {
		return identifyingCode;
	}

	public void setIdentifyingCode(String identifyingCode) {
		this.identifyingCode = identifyingCode;
	}

	public int getLogicstate() {
		return logicstate;
	}

	public void setLogicstate(int logicstate) {
		this.logicstate = logicstate;
	}
	
}
