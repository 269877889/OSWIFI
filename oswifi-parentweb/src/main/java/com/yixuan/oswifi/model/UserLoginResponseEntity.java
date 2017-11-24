package com.yixuan.oswifi.model;

import java.util.List;

public class UserLoginResponseEntity {

	private String token;
	
	private boolean nowUsePackage;
	
	private UserInfoEntity userentity;
	
	private List<OrderDetailResponseEntity> orderlist;
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isNowUsePackage() {
		return nowUsePackage;
	}

	public void setNowUsePackage(boolean nowUsePackage) {
		this.nowUsePackage = nowUsePackage;
	}

	public UserInfoEntity getUserentity() {
		return userentity;
	}

	public void setUserentity(UserInfoEntity userentity) {
		this.userentity = userentity;
	}

	public List<OrderDetailResponseEntity> getOrderlist() {
		return orderlist;
	}

	public void setOrderlist(List<OrderDetailResponseEntity> orderlist) {
		this.orderlist = orderlist;
	}
	
}
