package com.yixuan.oswifi.dao;

import com.yixuan.oswifi.model.OrderDetailInfo;
import com.yixuan.oswifi.model.OrderDetailRequestEntity;
import com.yixuan.oswifi.model.OrderDetailResponseEntity;

public interface IOrderDetailDao {

	public OrderDetailResponseEntity queryOrderDetail(OrderDetailRequestEntity requestentity);
	
	public int insertOrderDetail(OrderDetailInfo info);
	
	public int updateOrderDetail(OrderDetailInfo info);
}
