package com.yixuan.oswifi.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.yixuan.oswifi.dao.IFeedbackDao;
import com.yixuan.oswifi.model.FeedbackInfo;
import com.yixuan.oswifi.model.FeedbackRequestEntity;
import com.yixuan.oswifi.model.RequestParameterEntity;
import com.yixuan.oswifi.model.ResponseParameterEntity;
import com.yixuan.oswifi.service.IFeedbackService;
import com.yixuan.oswifi.service.IQueryTableIdService;
import com.yixuan.oswifi.util.ESBHeaderConstant;
import com.yixuan.oswifi.util.ParameterUtil;

public class FeedbackServiceImpl implements IFeedbackService<Object> {

	private static final Logger logger = Logger.getLogger(FeedbackServiceImpl.class);
	
	private IFeedbackDao FeedbackDao;
	
	private IQueryTableIdService queryTableIdService;
	
	private static final String T_OSWIFI_FEEDBACK = "t_oswifi_feedback";
	
	@Override
	public ResponseParameterEntity<Object> processRestRequest(
			RequestParameterEntity request, HttpServletRequest request2,
			HttpServletResponse response2) {
		
		response2.addHeader(ESBHeaderConstant.RESULTCODE, ESBHeaderConstant.REQUEST_ESB_WEBSERVICE_SUCCESS);
		ResponseParameterEntity<Object> response = new ResponseParameterEntity<Object>();
		List<FeedbackRequestEntity> requestlist = null;
		FeedbackRequestEntity requestentity = null;
		try{
				try{
					requestlist = ParameterUtil.changeToManyBusiness(request.getRequestEntity(),FeedbackRequestEntity.class);
					requestentity = requestlist.get(0);
				}catch(Exception e){
					logger.error("解析请求类出错，FeedbackRequestEntity");
					response.setFailureReason("解析反馈意见出错,请联系管理员");
					response.setStatus(ParameterUtil.RETURN_SYSTEM_ERROR);
					response.setResultFlag(false);
					return response;
				}
				//反馈意见为空
				if(StringUtils.isBlank(requestentity.getFeedback())){
					response.setFailureReason("反馈意见为空！");
					response.setStatus(ParameterUtil.RETURN_CHECK_PARAMS_FALSE);
					response.setResultFlag(false);
					return response;
				}
				FeedbackInfo info = new FeedbackInfo();
				info.setId(queryTableIdService.queryTableIdbyTableName(T_OSWIFI_FEEDBACK));
				info.setTelephone(requestentity.getTelephone());
				info.setFeedback(requestentity.getFeedback());
				
				int i = FeedbackDao.insertfeedback(info);
				
				if(i>0){
					response.setFailureReason("反馈意见成功!");
					response.setStatus(ParameterUtil.RETURN_SUCCESS_CODE);
					response.setResultFlag(true);
					return response;
				}else{
					response.setFailureReason("反馈意见失败!");
					response.setStatus(ParameterUtil.RETURN_SUCCESS_CODE);
					response.setResultFlag(true);
					return response;
				}
				
			}catch(Exception e){
				logger.error("反馈意见出错，请联系管理员，具体原因"+e.getMessage());
				//反馈意见为空
				response.setFailureReason("反馈意见出错，请联系管理员!");
				response.setStatus(ParameterUtil.RETURN_SYSTEM_ERROR);
				response.setResultFlag(false);
				return response;
			}
		
	}

	public IFeedbackDao getFeedbackDao() {
		return FeedbackDao;
	}

	@Autowired
	public void setFeedbackDao(IFeedbackDao feedbackDao) {
		FeedbackDao = feedbackDao;
	}

	public IQueryTableIdService getQueryTableIdService() {
		return queryTableIdService;
	}

	@Autowired
	public void setQueryTableIdService(IQueryTableIdService queryTableIdService) {
		this.queryTableIdService = queryTableIdService;
	}

}
