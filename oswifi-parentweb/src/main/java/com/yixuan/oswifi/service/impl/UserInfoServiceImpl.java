package com.yixuan.oswifi.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.yixuan.oswifi.dao.IUserInfoDao;
import com.yixuan.oswifi.model.RequestParameterEntity;
import com.yixuan.oswifi.model.ResponseParameterEntity;
import com.yixuan.oswifi.model.UserInfoEntity;
import com.yixuan.oswifi.service.IQueryTableIdService;
import com.yixuan.oswifi.service.IUserInfoService;
import com.yixuan.oswifi.util.ESBHeaderConstant;
import com.yixuan.oswifi.util.ParameterUtil;
import com.yixuan.oswifi.util.PayUtil;

public class UserInfoServiceImpl implements IUserInfoService<UserInfoEntity> {

	private static final String T_OSWIFI_MEMBERINFO = "t_oswifi_memberinfo";

	private IUserInfoDao userInfoDao;

	private IQueryTableIdService queryTableIdService;
	
	@Override
	public ResponseParameterEntity<UserInfoEntity> processRestRequest(
			RequestParameterEntity request, HttpServletRequest request2,
			HttpServletResponse response2) {
		response2.addHeader(ESBHeaderConstant.RESULTCODE,ESBHeaderConstant.REQUEST_ESB_WEBSERVICE_SUCCESS);
		ResponseParameterEntity<UserInfoEntity> response = new ResponseParameterEntity<UserInfoEntity>();
		List<UserInfoEntity> requestlist = null;
		List<UserInfoEntity> responselist = new ArrayList<UserInfoEntity>();
		
		requestlist = ParameterUtil.changeToManyBusiness(request.getRequestEntity(),UserInfoEntity.class);
		if(null == requestlist || 0 == requestlist.size() || null == requestlist.get(0)){			
			response.setResultFlag(false);
			response.setStatus(ParameterUtil.RETURN_CHECK_PARAMS_FALSE);
			response.setFailureReason("参数为空");
			return response;
			
		}
		UserInfoEntity requestentity = requestlist.get(0);
		
		if(StringUtils.isBlank(requestentity.getTelephone())){
			return evaluateResponse(response,"手机号码为空");
		}
		
		//operate,做保存或修改;query，做查询
		String method = requestentity.getMethod();
		
		if(StringUtils.isBlank(method)){
			return evaluateResponse(response,"操作方法为空");
		}else if(!"operate".equalsIgnoreCase(method) && !"query".equalsIgnoreCase(method)){
			return evaluateResponse(response,"操作方法为参数错误");
		}
		
		//保存或修改
		if("operate".equalsIgnoreCase(method)){
			
			if(StringUtils.isBlank(requestentity.getUsername())){
//				return evaluateResponse(response,"用户名为空");
				requestentity.setUsername(requestentity.getNickName());
			}
			
			if(StringUtils.isBlank(requestentity.getSex())){
				return evaluateResponse(response,"性别为空");
			}
		}
		
		
		UserInfoEntity oldentity = userInfoDao.queryUserInfo(requestentity);
		
		if("query".equalsIgnoreCase(method)){
			if(null != oldentity && 0 != oldentity.getId()){
				responselist.add(oldentity);
			}
			response.setResultFlag(true);
			response.setResponseEntity(responselist);
			response.setStatus(ParameterUtil.RETURN_SUCCESS_CODE);
			response.setFailureReason("查询成功");
			return response;
		}
		
		if(null == oldentity){
			requestentity.setId(queryTableIdService.queryTableIdbyTableName(T_OSWIFI_MEMBERINFO));
			String uniqueNumber = createUniqueNumber();
			requestentity.setUniqueNumber(uniqueNumber);
			userInfoDao.insertUserInfo(requestentity);
		}else{
			requestentity.setId(oldentity.getId());
			userInfoDao.updateUserInfo(requestentity);
		}
		
		response.setResultFlag(true);
		response.setStatus(ParameterUtil.RETURN_SUCCESS_CODE);
		response.setFailureReason("保存成功");
		return response;
	}

	private String createUniqueNumber(){
	    	 String date = new SimpleDateFormat("yyyyMMdd").format(new Date());  
	         String seconds = new SimpleDateFormat("HHmmss").format(new Date());
	         return date+PayUtil.getTwo()+seconds+PayUtil.getTwo();
	}
	
	private ResponseParameterEntity<UserInfoEntity> evaluateResponse(ResponseParameterEntity<UserInfoEntity> response,String name){
		response.setResultFlag(false);
		response.setStatus(ParameterUtil.RETURN_CHECK_PARAMS_FALSE);
		response.setFailureReason(name);
		return response;
	}

	public IUserInfoDao getUserInfoDao() {
		return userInfoDao;
	}

	@Autowired
	public void setUserInfoDao(IUserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	public IQueryTableIdService getQueryTableIdService() {
		return queryTableIdService;
	}

	@Autowired
	public void setQueryTableIdService(IQueryTableIdService queryTableIdService) {
		this.queryTableIdService = queryTableIdService;
	}
	
}
