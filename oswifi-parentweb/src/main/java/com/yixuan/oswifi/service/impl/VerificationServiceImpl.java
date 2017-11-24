package com.yixuan.oswifi.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import net.sf.json.JSONObject;

import com.yixuan.oswifi.dao.IMemberinfoDao;
import com.yixuan.oswifi.dao.IUserLoginDao;
import com.yixuan.oswifi.model.RequestParameterEntity;
import com.yixuan.oswifi.model.ResponseParameterEntity;
import com.yixuan.oswifi.model.UserLoginRequestEntity;
import com.yixuan.oswifi.service.IVerificationService;
import com.yixuan.oswifi.util.ESBHeaderConstant;
import com.yixuan.oswifi.util.ParameterUtil;

public class VerificationServiceImpl implements IVerificationService<Object> {

//	private IMemberinfoDao memberinfoDao;
	private IUserLoginDao userLoginDao;
	
	@Override
	public ResponseParameterEntity<Object> processRestRequest(
			RequestParameterEntity request, HttpServletRequest request2,
			HttpServletResponse response2) {
		ResponseParameterEntity entity = new ResponseParameterEntity();
		response2.addHeader(ESBHeaderConstant.RESULTCODE, ESBHeaderConstant.REQUEST_ESB_WEBSERVICE_SUCCESS);
		if("OSWIFI_SEND_VERIFICATION_CODE".equals(request.getType()) || 
				"OSWIFI_LOGIN_MOBILE".equals(request.getType()) ||
				"OSWIFI_PACKAGELIST_MOBILE".equals(request.getType()) ||
				"OSWIFI_PACKAGEDETAIL_MOBILE".equals(request.getType())){
			entity.setResultFlag(true);
			entity.setStatus(ParameterUtil.RETURN_SUCCESS_CODE);
			return entity;
		}
		JSONObject jsonobject = JSONObject.fromObject(request.getRequestEntity());
		String telephone = (String) jsonobject.get("telephone");
		if(StringUtils.isBlank(telephone)){
			entity.setResultFlag(false);
			entity.setFailureReason("手机号码为空！");
			entity.setStatus(ParameterUtil.RETURN_CHECK_PARAMS_FALSE);
			return entity;
		}else if(StringUtils.isBlank(request.getToken())){
			entity.setResultFlag(false);
			entity.setFailureReason("token为空！");
			entity.setStatus(ParameterUtil.RETURN_NOT_LOGIN);
			return entity;
		}else{
			UserLoginRequestEntity userinfo = new UserLoginRequestEntity();
			userinfo.setTelephone(telephone);
			userinfo.setToken(request.getToken());
			int i = userLoginDao.queryUserhaveRight(userinfo);
			if(i == 0){
				entity.setResultFlag(false);
				entity.setFailureReason("请重新登录！");
				entity.setStatus(ParameterUtil.RETURN_TOKEN_INVALID);
				return entity;
			}else{
				entity.setResultFlag(true);
				entity.setStatus(ParameterUtil.RETURN_SUCCESS_CODE);
				return entity;
			}
		}
	}

	public IUserLoginDao getUserLoginDao() {
		return userLoginDao;
	}

	@Autowired
	public void setUserLoginDao(IUserLoginDao userLoginDao) {
		this.userLoginDao = userLoginDao;
	}

//	public IMemberinfoDao getMemberinfoDao() {
//		return memberinfoDao;
//	}
//
//	@Autowired
//	public void setMemberinfoDao(IMemberinfoDao memberinfoDao) {
//		this.memberinfoDao = memberinfoDao;
//	}

}
