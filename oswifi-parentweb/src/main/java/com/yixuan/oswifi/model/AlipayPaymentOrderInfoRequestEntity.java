package com.yixuan.oswifi.model;

public class AlipayPaymentOrderInfoRequestEntity {

	//手机号码
	private String telephone;
	
	private String order_no;
	
	private int packageId;
	
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public int getPackageId() {
		return packageId;
	}

	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}
	
}
