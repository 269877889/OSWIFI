package com.yixuan.oswifi.dao;

import com.yixuan.oswifi.model.VerificationCodeInfo;

public interface ISendVerificationCodeDao {

	VerificationCodeInfo queryVerificationCodeInfo(VerificationCodeInfo info);
	
	int insertVerificationCodeInfo(VerificationCodeInfo info);
	
	int updateVerificationCodeInfo(VerificationCodeInfo info);
}
