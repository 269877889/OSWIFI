package com.yixuan.oswifi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.yixuan.oswifi.dao.IPCUserLoginDao;
import com.yixuan.oswifi.model.PCuserLoginEntity;
import com.yixuan.oswifi.service.IPCUserLoginService;

public class PCUserLoginServiceImpl implements IPCUserLoginService {

	private IPCUserLoginDao pcuserLoginDao;
	
	@Override
	public int queryCountUser(PCuserLoginEntity entity) {
		return pcuserLoginDao.queryCountUser(entity);
	}

	public IPCUserLoginDao getPcuserLoginDao() {
		return pcuserLoginDao;
	}

	@Autowired
	public void setPcuserLoginDao(IPCUserLoginDao pcuserLoginDao) {
		this.pcuserLoginDao = pcuserLoginDao;
	}

	
	
}
