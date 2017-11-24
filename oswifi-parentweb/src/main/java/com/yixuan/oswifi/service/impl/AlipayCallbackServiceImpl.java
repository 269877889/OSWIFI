package com.yixuan.oswifi.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.yixuan.oswifi.dao.IOrderDetailDao;
import com.yixuan.oswifi.model.OrderDetailInfo;
import com.yixuan.oswifi.service.IAlipayCallbackService;
import com.yixuan.oswifi.util.AlipayConfig;

public class AlipayCallbackServiceImpl implements IAlipayCallbackService {
	private IOrderDetailDao orderDetailDao;
	
	public String alipaycallbackMethod(Map params,String out_trade_no,String trade_status){
		
        try {
			boolean flag = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, "UTF-8", "RSA");
			OrderDetailInfo info = new OrderDetailInfo();
			info.setOrderNo(out_trade_no);
			if(flag){
				if("TRADE_FINISHED".equals(trade_status) || "TRADE_SUCCESS".equals(trade_status)){
					info.setStatus("havePay");
					info.setIsUse("Y");
				}else {
					info.setStatus("payfale");
				}
				
			}else{
				if("TRADE_FINISHED".equals(trade_status) || "TRADE_SUCCESS".equals(trade_status)){
					info.setStatus("havePay_NC");
					info.setIsUse("Y");
				}
			}
			orderDetailDao.updateOrderDetail(info);
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
        return "true";
    }

	public IOrderDetailDao getOrderDetailDao() {
		return orderDetailDao;
	}

	@Autowired
	public void setOrderDetailDao(IOrderDetailDao orderDetailDao) {
		this.orderDetailDao = orderDetailDao;
	}
}
