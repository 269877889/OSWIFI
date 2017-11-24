package com.yixuan.oswifi.dao;

public interface IQueryTableIdDao {

	int insertTableIdbyTableName(String tableName);
	
	int updateTableIdbyTableName(String tableName);
	
	int queryTableIdbyTableName(String tableName);
	
}
