package com.yixuan.oswifi.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.yixuan.oswifi.dao.IPackageDetailDao;
import com.yixuan.oswifi.model.PackageDetailRequestEntity;
import com.yixuan.oswifi.model.PackageDetailResponseEntity;
import com.yixuan.oswifi.model.RequestParameterEntity;
import com.yixuan.oswifi.model.ResponseParameterEntity;
import com.yixuan.oswifi.service.IPackageDetailService;
import com.yixuan.oswifi.util.ESBHeaderConstant;
import com.yixuan.oswifi.util.ParameterUtil;

/**
 * 套餐详情
 * @author sss
 *
 */
public class PackageDetailServiceImpl implements IPackageDetailService<PackageDetailResponseEntity> {

	private IPackageDetailDao PackageDetailDao;
	
	

	@Override
	public ResponseParameterEntity<PackageDetailResponseEntity> processRestRequest(
			RequestParameterEntity request, HttpServletRequest request2,
			HttpServletResponse response2) {
		ResponseParameterEntity<PackageDetailResponseEntity> response = new ResponseParameterEntity<PackageDetailResponseEntity>();
		response.setStatus(ParameterUtil.RETURN_SUCCESS_CODE);
		List<PackageDetailRequestEntity> list = ParameterUtil.changeToManyBusiness(request.getRequestEntity(),PackageDetailRequestEntity.class);
		if(0 == list.get(0).getId()){
			response.setResultFlag(false);
			response2.addHeader(ESBHeaderConstant.RESULTCODE, ESBHeaderConstant.REQUEST_ESB_WEBSERVICE_SUCCESS);
			response.setFailureReason("ID为空");
		}
		
		PackageDetailResponseEntity entity = PackageDetailDao.queryPackageDetail(list.get(0).getId());
		List<PackageDetailResponseEntity> responselist = new ArrayList<PackageDetailResponseEntity>();
		if(null != entity){
			responselist.add(entity);
		}
		response.setResultFlag(true);
		response.setResponseEntity(responselist);
		response2.addHeader(ESBHeaderConstant.RESULTCODE, ESBHeaderConstant.REQUEST_ESB_WEBSERVICE_SUCCESS);
		return response;
	}

	public IPackageDetailDao getPackageDetailDao() {
		return PackageDetailDao;
	}

	@Autowired
	public void setPackageDetailDao(IPackageDetailDao packageDetailDao) {
		PackageDetailDao = packageDetailDao;
	}
}
