package com.yixuan.oswifi.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RequestParameterEntity")
public class RequestParameterEntity {

	// 公共实体,可为数组类型
	private Object requestEntity;
	// 接口类型
	private String type;

	//token
	private String token;
	
	public Object getRequestEntity() {
		return requestEntity;
	}

	public void setRequestEntity(Object requestEntity) {
		this.requestEntity = requestEntity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
