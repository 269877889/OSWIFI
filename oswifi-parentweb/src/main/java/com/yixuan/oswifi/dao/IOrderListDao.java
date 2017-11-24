package com.yixuan.oswifi.dao;

import java.util.List;

import com.yixuan.oswifi.model.OrderDetailResponseEntity;
import com.yixuan.oswifi.model.OrderListRequestEntity;

public interface IOrderListDao {

	List<OrderDetailResponseEntity>  queryOrderList(OrderListRequestEntity requestentity);
}
