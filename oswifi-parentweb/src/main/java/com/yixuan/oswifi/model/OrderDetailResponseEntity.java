package com.yixuan.oswifi.model;

import java.math.BigDecimal;

public class OrderDetailResponseEntity {

	private int id;
	private String orderNo;
	//会员编码
	private String telephone;
	//套餐id
	private int packageId;
	//套餐名称
	private String packageName;
	//订单价格
	private BigDecimal Price;
	//激活时间
	private int days;
	//套餐说明
	private String packageContent;
	//订单创建时间
	private String createTime;
	//订单修改时间
	private String modifyTime;
	//付款方式
	private String modeOfPayment;
	//是否有效
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
	public String getPackageContent() {
		return packageContent;
	}
	public void setPackageContent(String packageContent) {
		this.packageContent = packageContent;
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
