package com.yixuan.oswifi.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.yixuan.oswifi.dao.IOrderListDao;
import com.yixuan.oswifi.dao.IUserInfoDao;
import com.yixuan.oswifi.dao.IUserLoginDao;
import com.yixuan.oswifi.model.OrderDetailResponseEntity;
import com.yixuan.oswifi.model.OrderListRequestEntity;
import com.yixuan.oswifi.model.RequestParameterEntity;
import com.yixuan.oswifi.model.ResponseParameterEntity;
import com.yixuan.oswifi.model.UserInfoEntity;
import com.yixuan.oswifi.model.UserLoginRequestEntity;
import com.yixuan.oswifi.model.UserLoginResponseEntity;
import com.yixuan.oswifi.service.IUserLoginService;
import com.yixuan.oswifi.util.ESBHeaderConstant;
import com.yixuan.oswifi.util.ParameterUtil;

/**
 * 用户登录
 * @author sss
 *
 */
public class UserLoginServiceImpl implements IUserLoginService<UserLoginResponseEntity> {

	private IUserLoginDao userLoginDao;

	private IUserInfoDao userInfoDao;
	
	private IOrderListDao orderListDao;
	@Override
	public ResponseParameterEntity<UserLoginResponseEntity> processRestRequest(
			RequestParameterEntity request, HttpServletRequest request2,
			HttpServletResponse response2) {
		ResponseParameterEntity<UserLoginResponseEntity> response = new ResponseParameterEntity<UserLoginResponseEntity>();
		List<UserLoginRequestEntity> list = ParameterUtil.changeToManyBusiness(request.getRequestEntity(),UserLoginRequestEntity.class);
		response2.addHeader(ESBHeaderConstant.RESULTCODE, ESBHeaderConstant.REQUEST_ESB_WEBSERVICE_SUCCESS);
		UserLoginRequestEntity requestentity = list.get(0); 
		//校验数据
		if(StringUtils.isBlank(requestentity.getTelephone())){
			return evaluateResponse(response,"手机号码为空");
		}
		
		if(StringUtils.isBlank(requestentity.getIdentifyingCode())){
			return evaluateResponse(response,"手机验证码为空");
		}
		
		int count = userLoginDao.queryUsercount(requestentity);
		
		if(count >0){
			String token = UUID.randomUUID().toString();
			requestentity.setToken(token);
			//更新token信息
			userLoginDao.updateUsertoken(requestentity);
			UserInfoEntity userinfo = new UserInfoEntity();
			userinfo.setTelephone(requestentity.getTelephone());
			//查询个人信息
			UserInfoEntity userentity = userInfoDao.queryUserInfo(userinfo);
			//查询正在使用的订单信息
			OrderListRequestEntity orderquery  = new OrderListRequestEntity();
			orderquery.setTelephone(requestentity.getTelephone());
			orderquery.setLogicstate(0);
			orderquery.setIsUse("Y");
			List<OrderDetailResponseEntity>  orderlist=orderListDao.queryOrderList(orderquery);
			//返回列表
			List<UserLoginResponseEntity> responselist = new ArrayList<UserLoginResponseEntity>();
			//返回的实体
			UserLoginResponseEntity responseEntity = new UserLoginResponseEntity();
			responseEntity.setToken(token);
			responseEntity.setUserentity(userentity);
			responseEntity.setOrderlist(orderlist);
			responselist.add(responseEntity);
			response.setResponseEntity(responselist);
			response.setFailureReason("验证成功");
			response.setStatus(ParameterUtil.RETURN_SUCCESS_CODE);
//			response2.addHeader(ESBHeaderConstant.RESULTCODE, ESBHeaderConstant.REQUEST_ESB_WEBSERVICE_SUCCESS);
			response.setResultFlag(true);	
		}else{
			response.setFailureReason("验证失败");
			response.setStatus(ParameterUtil.RETURN_SYSTEM_DW_BUSINESS_FALSE);
//			response2.addHeader(ESBHeaderConstant.RESULTCODE, ESBHeaderConstant.REQUEST_ESB_WEBSERVICE_SUCCESS);
			response.setResultFlag(false);
		}
		return response;
	}
	
	private ResponseParameterEntity<UserLoginResponseEntity> evaluateResponse(ResponseParameterEntity<UserLoginResponseEntity> response,String name){
		response.setResultFlag(false);
		response.setStatus(ParameterUtil.RETURN_CHECK_PARAMS_FALSE);
		response.setFailureReason(name);
		return response;
	}
	
	public IUserLoginDao getUserLoginDao() {
		return userLoginDao;
	}
	@Autowired
	public void setUserLoginDao(IUserLoginDao userLoginDao) {
		this.userLoginDao = userLoginDao;
	}

	public IUserInfoDao getUserInfoDao() {
		return userInfoDao;
	}

	@Autowired
	public void setUserInfoDao(IUserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	public IOrderListDao getOrderListDao() {
		return orderListDao;
	}

	@Autowired
	public void setOrderListDao(IOrderListDao orderListDao) {
		this.orderListDao = orderListDao;
	}
	
}
