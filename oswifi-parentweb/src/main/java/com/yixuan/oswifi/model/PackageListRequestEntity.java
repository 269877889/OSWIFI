package com.yixuan.oswifi.model;

import java.math.BigDecimal;

public class PackageListRequestEntity {


	//手机号码
	private String telephone;
	
	//套餐名称
	private String packageName;
	
	//套餐类型
	private String packageType;
	
	//套餐天数
	private int packageDays;
	
	//套餐价格
	private BigDecimal packagePrice;
	
	//是否过期
	private int logicstate;

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getPackageType() {
		return packageType;
	}

	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}

	public int getPackageDays() {
		return packageDays;
	}

	public void setPackageDays(int packageDays) {
		this.packageDays = packageDays;
	}

	public BigDecimal getPackagePrice() {
		return packagePrice;
	}

	public void setPackagePrice(BigDecimal packagePrice) {
		this.packagePrice = packagePrice;
	}

	public int getLogicstate() {
		return logicstate;
	}

	public void setLogicstate(int logicstate) {
		this.logicstate = logicstate;
	}
	
}
