package com.yixuan.oswifi.service;

import java.util.Map;

public interface IAlipayCallbackService {

	public String alipaycallbackMethod(Map params,String out_trade_no,String trade_status);
}
