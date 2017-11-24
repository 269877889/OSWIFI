package com.yixuan.oswifi.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.yixuan.oswifi.dao.IPackageListDao;
import com.yixuan.oswifi.model.PackageDetailResponseEntity;
import com.yixuan.oswifi.model.PackageListRequestEntity;
import com.yixuan.oswifi.model.RequestParameterEntity;
import com.yixuan.oswifi.model.ResponseParameterEntity;
import com.yixuan.oswifi.service.IPackageListService;
import com.yixuan.oswifi.util.ESBHeaderConstant;
import com.yixuan.oswifi.util.ParameterUtil;

/**
 * 套餐列表
 * @author sss
 *
 */
public class PackageListServiceImpl implements IPackageListService<PackageDetailResponseEntity> {

	private IPackageListDao packageListDao;
	
	@Override
	public ResponseParameterEntity<PackageDetailResponseEntity> processRestRequest(
			RequestParameterEntity request, HttpServletRequest request2,
			HttpServletResponse response2) {
		ResponseParameterEntity<PackageDetailResponseEntity> response = new ResponseParameterEntity<PackageDetailResponseEntity>();
		List<PackageListRequestEntity> list = null;
		PackageListRequestEntity requestentity = null;
		try{
		list = ParameterUtil.changeToManyBusiness(request.getRequestEntity(),PackageListRequestEntity.class);
		}catch(Exception e){
			
		}
		if(list == null ){
			requestentity = new PackageListRequestEntity();
		}else{
			requestentity = list.get(0);
		}
		List<PackageDetailResponseEntity> responselist = packageListDao.queryPackageList(requestentity);
		
		response.setResultFlag(true);
		response.setStatus(ParameterUtil.RETURN_SUCCESS_CODE);
		response.setResponseEntity(responselist);
		response2.addHeader(ESBHeaderConstant.RESULTCODE, ESBHeaderConstant.REQUEST_ESB_WEBSERVICE_SUCCESS);
		return response;
	}

	public IPackageListDao getPackageListDao() {
		return packageListDao;
	}

	@Autowired
	public void setPackageListDao(IPackageListDao packageListDao) {
		this.packageListDao = packageListDao;
	}

}
