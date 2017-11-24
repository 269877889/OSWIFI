package com.yixuan.oswifi.model;

import java.util.List;

public class ResponseParameterEntity<T> {
	// 公共返回参数集合
	private List<T> responseEntity;
	// 是否成功标志
	private boolean resultFlag;
	// 失败原因
	private String failureReason;
	// 1:全部 0 已合作
	private String responseType; 
	//状态码--200成功，201失效，202未登录,203校验未通过
	private String status;
	
	public List<T> getResponseEntity() {
		return responseEntity;
	}

	public void setResponseEntity(List<T> responseEntity) {
		this.responseEntity = responseEntity;
	}

	public boolean isResultFlag() {
		return resultFlag;
	}

	public void setResultFlag(boolean resultFlag) {
		this.resultFlag = resultFlag;
	}

	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

	public String getResponseType() {
		return responseType;
	}

	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
