package com.yixuan.oswifi.model;

import java.math.BigDecimal;


/**
 * 套餐
 *
 */
public class PackageDetailResponseEntity {

	private int id;
	
	//套餐名称
	private String packageName;
	
	//套餐编码
	private String packageNo;
	
	//套餐类型
	private String packageType;
	
	//套餐时常
	private int packageDays;
	
	//套餐价格
	private BigDecimal packagePrice;
	
	//套餐内容
	private String packageContent;
	
	//额外说明-备注
	private String remark;
	
	private String createTime;
	
	private String createUser;
	
	private String modifyTime;
	
	private String modifyUser;
	
	private int logicstate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getPackageNo() {
		return packageNo;
	}

	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
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

	public String getPackageContent() {
		return packageContent;
	}

	public void setPackageContent(String packageContent) {
		this.packageContent = packageContent;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public int getLogicstate() {
		return logicstate;
	}

	public void setLogicstate(int logicstate) {
		this.logicstate = logicstate;
	}
	
	
}
