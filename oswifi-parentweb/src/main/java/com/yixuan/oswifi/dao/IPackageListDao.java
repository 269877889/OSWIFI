package com.yixuan.oswifi.dao;

import java.util.List;

import com.yixuan.oswifi.model.PackageDetailResponseEntity;
import com.yixuan.oswifi.model.PackageListRequestEntity;

public interface IPackageListDao {

	/**
	 * 查询套餐列表
	 * @param rquestentity
	 * @return
	 */
	public List<PackageDetailResponseEntity> queryPackageList(PackageListRequestEntity rquestentity);
}
