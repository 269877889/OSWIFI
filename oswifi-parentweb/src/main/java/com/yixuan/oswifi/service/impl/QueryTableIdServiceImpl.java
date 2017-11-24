package com.yixuan.oswifi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.yixuan.oswifi.dao.IQueryTableIdDao;
import com.yixuan.oswifi.service.IQueryTableIdService;

public class QueryTableIdServiceImpl implements IQueryTableIdService {

	private IQueryTableIdDao queryTableIdDao;
	
	public int queryTableIdbyTableName(String tableName){
		synchronized(tableName){
			int i = queryTableIdDao.updateTableIdbyTableName(tableName);
			if(i == 0){
				queryTableIdDao.insertTableIdbyTableName(tableName);
			}
			return queryTableIdDao.queryTableIdbyTableName(tableName);
		}
	}

	public IQueryTableIdDao getQueryTableId() {
		return queryTableIdDao;
	}

	@Autowired
	public void setQueryTableId(IQueryTableIdDao queryTableIdDao) {
		this.queryTableIdDao = queryTableIdDao;
	}
	
	
}
