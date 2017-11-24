package com.yixuan.oswifi.model;

import java.math.BigDecimal;

public class OrderDetailInfo {

	private int id;
	
	private String orderNo;
	
	private String status;
	
	private String telephone;
	
	private int packageId;
	
	private String packageName;
	
	private BigDecimal Price;
	
	//套餐时常
	private int days;
	
	private String isUse;

	/**
	 * 付款方式:Alipay，weixin
	 */
	private String modeOfPayment;
	
	private int logicstate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public int getPackageId() {
		return packageId;
	}

	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public BigDecimal getPrice() {
		return Price;
	}

	public void setPrice(BigDecimal price) {
		Price = price;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public String getIsUse() {
		return isUse;
	}

	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}

	public String getModeOfPayment() {
		return modeOfPayment;
	}

	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}

	public int getLogicstate() {
		return logicstate;
	}

	public void setLogicstate(int logicstate) {
		this.logicstate = logicstate;
	}
	
}
