package com.yixuan.oswifi.model;

public class UserInfoEntity {

	private int id;
	
	private String telephone;
	
	//用户名
	private String username;
	
	//性别 1：男，2：女
	private String sex;
	
	//昵称
	private String nickName;
	
	//个性签名
	private String PersonalizedSignature;
	
	//身份证
	private String IDCardNo;
	
	//唯一识别码
	private String UniqueNumber;
	
	private String createTime;
	
	private String modifyTime;
	
	//operate,做保存或修改;query，做查询
	private String method;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPersonalizedSignature() {
		return PersonalizedSignature;
	}

	public void setPersonalizedSignature(String personalizedSignature) {
		PersonalizedSignature = personalizedSignature;
	}

	public String getIDCardNo() {
		return IDCardNo;
	}

	public void setIDCardNo(String iDCardNo) {
		IDCardNo = iDCardNo;
	}

	public String getUniqueNumber() {
		return UniqueNumber;
	}

	public void setUniqueNumber(String uniqueNumber) {
		UniqueNumber = uniqueNumber;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	
}
