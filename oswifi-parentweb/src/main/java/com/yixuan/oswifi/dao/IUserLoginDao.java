package com.yixuan.oswifi.dao;

import com.yixuan.oswifi.model.UserLoginRequestEntity;


public interface IUserLoginDao {

	public int queryUsercount(UserLoginRequestEntity userinfo);
	
	public int queryUserhaveRight(UserLoginRequestEntity userinfo);
	
	public int updateUsertoken(UserLoginRequestEntity userinfo);
}
