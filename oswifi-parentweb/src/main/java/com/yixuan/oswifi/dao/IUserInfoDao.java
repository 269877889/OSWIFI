package com.yixuan.oswifi.dao;

import com.yixuan.oswifi.model.UserInfoEntity;

public interface IUserInfoDao {

	UserInfoEntity queryUserInfo(UserInfoEntity requestentity);
	
	int insertUserInfo(UserInfoEntity requestentity);
	
	int updateUserInfo(UserInfoEntity requestentity);
}
