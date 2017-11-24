package com.yixuan.oswifi.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import com.yixuan.oswifi.model.RequestParameterEntity;
import com.yixuan.oswifi.model.ResponseParameterEntity;

public interface IMobileRestService<T> extends IService {

	ResponseParameterEntity<T> processRestRequest(RequestParameterEntity request,@Context HttpServletRequest request2,
			@Context HttpServletResponse response2);
}
