package com.yixuan.oswifi.model;

public class WeixinPaymentOrderInfoRequestEntity {

	//电话
	private String telephone;
	//客户端操作系统
	private String clientOS;

	private String order_no;
	
	private int packageId;

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getClientOS() {
		return clientOS;
	}

	public void setClientOS(String clientOS) {
		this.clientOS = clientOS;
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
