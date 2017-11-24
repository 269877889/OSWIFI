package com.yixuan.oswifi.action;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.opensymphony.xwork2.ActionSupport;
import com.yixuan.oswifi.service.IAlipayCallbackService;
import com.yixuan.oswifi.util.AlipayConfig;

//public class alipayAction{
public class alipayAction extends ActionSupport implements ServletRequestAware {

	private static final Logger logger = Logger.getLogger(alipayAction.class);

	private HttpServletRequest request;

	private IAlipayCallbackService alipayCallbackService;

	public String alipaycallback() {

		// 获取支付宝POST过来反馈信息
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。
			try {
				logger.info("alipaycallback params name:" + name + "value:"+ valueStr);
				String valueStr1 = new String(valueStr.getBytes("ISO-8859-1"),"utf-8");
				logger.info("alipaycallback params valuer:" + valueStr1);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				logger.error("解决乱码报错，具体原因：" + e.getMessage());
			}
			params.put(name, valueStr);
		}
		try {
			JSONObject jsonobject = JSONObject.fromObject(params);
			logger.info("alipaycallback method get params:"+ jsonobject.toString());
		} catch (Exception e) {
			logger.error("解析alipaycallback get params error：" + e.getMessage());
		}
		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		// 商户订单号
		String out_trade_no = null;
		try {
			out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			logger.error("out_trade_no报错，具体原因：" + e1.getMessage());
		}

		// //支付宝交易号
		// try {
		// String trade_no = new
		// String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
		// } catch (UnsupportedEncodingException e1) {
		// logger.error("trade_no报错，具体原因："+e1.getMessage());
		// }
		//
		// 交易状态
		String trade_status = "";
		try {
			trade_status = new String(request.getParameter("trade_status")
					.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			logger.error("trade_status报错，具体原因：" + e1.getMessage());
		}

		// //异步通知ID
		// String notify_id=request.getParameter("notify_id");
		//
		// //sign
		// String sign=request.getParameter("sign");

		alipayCallbackService.alipaycallbackMethod(params, out_trade_no,trade_status);

		return "success";
	}

	@Override
	public void setServletRequest(HttpServletRequest servletquest) {
		request = servletquest;

	}

	// public IAlipayCallbackService getAlipayCallbackService() {
	// return alipayCallbackService;
	// }

	public void setAlipayCallbackService(
			IAlipayCallbackService alipayCallbackService) {
		this.alipayCallbackService = alipayCallbackService;
	}

}
