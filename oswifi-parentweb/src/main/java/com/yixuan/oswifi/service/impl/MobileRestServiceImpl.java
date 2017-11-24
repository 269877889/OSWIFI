package com.yixuan.oswifi.service.impl;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.yixuan.oswifi.model.RequestParameterEntity;
import com.yixuan.oswifi.model.ResponseParameterEntity;
import com.yixuan.oswifi.service.IMobileRestService;
import com.yixuan.oswifi.util.ParameterUtil;
import com.yixuan.oswifi.util.SpringContextUtil;
import com.yixuan.oswifi.util.SystemConstants;
/**
 * 接口类
 * @author sss
 *
 */
@SuppressWarnings("rawtypes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MobileRestServiceImpl implements IMobileRestService {

	private static final Logger logger = Logger.getLogger(MobileRestServiceImpl.class);
	
	@Path("/dealMobileMesssage")
	@POST
	@Override
	public ResponseParameterEntity<?> processRestRequest(RequestParameterEntity request,@Context HttpServletRequest request2,
			@Context HttpServletResponse response2) {
		logger.info("dealMobileMesssage is start,type is :"+request.getType());
		//初始化ESB头信息
    	ParameterUtil.initESBHeader(request2, response2, SystemConstants.CAPACITY_PROCUREMENT_SYSTEM_NAME, UUID.randomUUID().toString());
		
    	//验证具体处理类
    	IMobileRestService<?> Verificationservice = (IMobileRestService<?>)SpringContextUtil.getBean(ParameterUtil.getBeanName("OSWIFI_VERIFICATION_SERVICE"));
    	ResponseParameterEntity entity = (ResponseParameterEntity) Verificationservice.processRestRequest(request,request2,response2);
    	if(entity.isResultFlag()){
        	//确实具体处理类
        	IMobileRestService<?> service = (IMobileRestService<?>)SpringContextUtil.getBean(ParameterUtil.getBeanName(request.getType()));
        	return service.processRestRequest(request,request2,response2);
    	}else{
    		return entity;
    	}
    	
	}

}
