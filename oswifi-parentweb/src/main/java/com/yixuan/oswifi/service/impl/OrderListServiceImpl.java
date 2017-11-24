package com.yixuan.oswifi.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.yixuan.oswifi.dao.IOrderListDao;
import com.yixuan.oswifi.model.OrderDetailResponseEntity;
import com.yixuan.oswifi.model.OrderListRequestEntity;
import com.yixuan.oswifi.model.RequestParameterEntity;
import com.yixuan.oswifi.model.ResponseParameterEntity;
import com.yixuan.oswifi.service.IOrderListService;
import com.yixuan.oswifi.util.ESBHeaderConstant;
import com.yixuan.oswifi.util.ParameterUtil;

public class OrderListServiceImpl implements IOrderListService<OrderDetailResponseEntity> {

	private IOrderListDao orderListDao;
	
	@Override
	public ResponseParameterEntity<OrderDetailResponseEntity> processRestRequest(
			RequestParameterEntity request, HttpServletRequest request2,
			HttpServletResponse response2) {
		ResponseParameterEntity<OrderDetailResponseEntity> response = new ResponseParameterEntity<OrderDetailResponseEntity>();
		List<OrderListRequestEntity> requestlist = null;
		OrderListRequestEntity requestentity = null;
		try{
			requestlist = ParameterUtil.changeToManyBusiness(request.getRequestEntity(),OrderListRequestEntity.class);
		}catch(Exception e){
			
		}
		
		if(null == requestlist){
			requestentity = new OrderListRequestEntity();
		}else{
			requestentity = requestlist.get(0);
		}
		
		
		List<OrderDetailResponseEntity>  responselist=orderListDao.queryOrderList(requestentity);
		
		response.setResultFlag(true);
		response.setStatus(ParameterUtil.RETURN_SUCCESS_CODE);
		response.setResponseEntity(responselist);
		response2.addHeader(ESBHeaderConstant.RESULTCODE, ESBHeaderConstant.REQUEST_ESB_WEBSERVICE_SUCCESS);
		return response;
	}

	public IOrderListDao getOrderListDao() {
		return orderListDao;
	}

	@Autowired
	public void setOrderListDao(IOrderListDao orderListDao) {
		this.orderListDao = orderListDao;
	}

	
}
