package com.yixuan.oswifi.model;

public class OrderListRequestEntity {


	//手机号码
	private String telephone;
	
	//正在使用标示
	private String isUse;
	
	private int logicstate;

	public int getLogicstate() {
		return logicstate;
	}

	public void setLogicstate(int logicstate) {
		this.logicstate = logicstate;
	}

	public String getIsUse() {
		return isUse;
	}

	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
}
