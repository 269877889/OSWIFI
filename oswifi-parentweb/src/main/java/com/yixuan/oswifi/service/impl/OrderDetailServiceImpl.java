package com.yixuan.oswifi.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.yixuan.oswifi.dao.IOrderDetailDao;
import com.yixuan.oswifi.model.OrderDetailRequestEntity;
import com.yixuan.oswifi.model.OrderDetailResponseEntity;
import com.yixuan.oswifi.model.RequestParameterEntity;
import com.yixuan.oswifi.model.ResponseParameterEntity;
import com.yixuan.oswifi.service.IOrderDetailService;
import com.yixuan.oswifi.util.ESBHeaderConstant;
import com.yixuan.oswifi.util.ParameterUtil;

public class OrderDetailServiceImpl implements IOrderDetailService<OrderDetailResponseEntity> {

	private IOrderDetailDao orderDetailDao;
	
	@Override
	public ResponseParameterEntity<OrderDetailResponseEntity> processRestRequest(
			RequestParameterEntity request, HttpServletRequest request2,
			HttpServletResponse response2) {
		ResponseParameterEntity<OrderDetailResponseEntity> response = new ResponseParameterEntity<OrderDetailResponseEntity>();
		List<OrderDetailRequestEntity> requestlist = null;
		OrderDetailRequestEntity requestentity = null;
		try{
			requestlist = ParameterUtil.changeToManyBusiness(request.getRequestEntity(),OrderDetailRequestEntity.class);
		}catch(Exception e){
			
		}
		
		if(null == requestlist){
			requestentity = new OrderDetailRequestEntity();
		}else{
			requestentity = requestlist.get(0);
		}
		
		OrderDetailResponseEntity  responseentity=orderDetailDao.queryOrderDetail(requestentity);
		
		List<OrderDetailResponseEntity> responselist = new ArrayList<OrderDetailResponseEntity>();
		if(null != responseentity){
			responselist.add(responseentity);
		}
		response.setResultFlag(true);
		response.setStatus(ParameterUtil.RETURN_SUCCESS_CODE);
		response.setResponseEntity(responselist);
		response2.addHeader(ESBHeaderConstant.RESULTCODE, ESBHeaderConstant.REQUEST_ESB_WEBSERVICE_SUCCESS);
		return response;
	}

	public IOrderDetailDao getOrderDetailDao() {
		return orderDetailDao;
	}

	@Autowired
	public void setOrderDetailDao(IOrderDetailDao orderDetailDao) {
		this.orderDetailDao = orderDetailDao;
	}

	
}
