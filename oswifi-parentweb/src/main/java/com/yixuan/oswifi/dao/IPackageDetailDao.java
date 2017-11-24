package com.yixuan.oswifi.dao;

import com.yixuan.oswifi.model.PackageDetailResponseEntity;

public interface IPackageDetailDao {
	/**
	 * 套餐详情
	 * @param id
	 * @return
	 */
	public PackageDetailResponseEntity queryPackageDetail(int id);
}
