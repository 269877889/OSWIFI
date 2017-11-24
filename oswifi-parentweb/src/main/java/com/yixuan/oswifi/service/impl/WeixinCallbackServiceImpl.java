package com.yixuan.oswifi.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.wxpay.sdk.WXPayUtil;
import com.yixuan.oswifi.dao.IOrderDetailDao;
import com.yixuan.oswifi.exception.BusinessException;
import com.yixuan.oswifi.model.OrderDetailInfo;
import com.yixuan.oswifi.service.IWeixinCallbackService;
import com.yixuan.oswifi.util.WeixinUtil;

@Controller
@RequestMapping("/weixinCallback")
@Scope("prototype")
public class WeixinCallbackServiceImpl implements IWeixinCallbackService {

	private static final Logger logger = Logger
			.getLogger(WeixinCallbackServiceImpl.class);

	private IOrderDetailDao orderDetailDao;
	
	@RequestMapping("/reciveWeixinNotificationInfo")
	@ResponseBody
	public String reciveWeixinNotificationInfo(HttpServletRequest request,
			HttpServletResponse response) {

		BufferedReader reader = null;

		String line = "";
		String xmlString = null;
		StringBuffer inputString = new StringBuffer();
		Map<String, String> map = new HashMap<String, String>();
		String result_code = "";
		String return_code = "";
		String out_trade_no = "";
		try {
			reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				inputString.append(line);
			}
			xmlString = inputString.toString();
			logger.info("接收微信返回信息成功，单号："+out_trade_no+",具体内容为:"+xmlString);
			request.getReader().close();
			System.out.println("----接收到的数据如下：---" + xmlString);

			map = WXPayUtil.xmlToMap(xmlString);
			result_code = map.get("result_code");
			out_trade_no = map.get("out_trade_no");
			return_code = map.get("return_code");
			
			//返回信息
//			try{
//				logger.info("微信接收返回信息,单号："+out_trade_no+"，获取失败信息,"+map.get("return_msg"));
//			}catch(BusinessException be){
//				logger.error("微信接收返回信息，获取失败信息报错，具体原因："+be.getMessage());
//			}
//			//付款失败
//			if("FAIL".equalsIgnoreCase(result_code)){
//				OrderDetailInfo info = new OrderDetailInfo();
//				info.setStatus("Payfail");
//				info.setIsUse("N");
//				orderDetailDao.updateOrderDetail(info);
//				return returnXML("SUCCESS");
//			}
			
		} catch (Exception e) {
			logger.error("微信接收返回信息报错，具体原因为：" + e.getMessage());
		}
		OrderDetailInfo info = new OrderDetailInfo();
		info.setOrderNo(out_trade_no);
		if (checkSign(xmlString)) {
			logger.info("微信签名校验成功，单号："+out_trade_no);
			if("SUCCESS".equalsIgnoreCase(result_code)){
				info.setStatus("havePay");
				info.setIsUse("Y");
			}else{
				info.setStatus("Payfail");
				info.setIsUse("N");
			}
			orderDetailDao.updateOrderDetail(info);
			return returnXML("SUCCESS");
		} else {
			logger.info("微信签名校验失败，单号："+out_trade_no);
			info.setStatus("Payfail");
			info.setIsUse("N");
			orderDetailDao.updateOrderDetail(info);
			return returnXML("FAIL");
		}
	}

	private boolean checkSign(String xmlString) {

		Map<String, String> map = null;

		try {

			map = WXPayUtil.xmlToMap(xmlString);

		} catch (Exception e) {
			e.printStackTrace();
		}

		String signFromAPIResponse = map.get("sign").toString();

		if (signFromAPIResponse == "" || signFromAPIResponse == null) {

			logger.error("API返回的数据签名数据不存在，有可能被第三方篡改!!!");
			System.out.println("API返回的数据签名数据不存在，有可能被第三方篡改!!!");
			return false;

		}
		System.out.println("服务器回包里面的签名是:" + signFromAPIResponse);

		// 清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名

		map.put("sign", "");

		// 将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较

		String signForAPIResponse = getSign(map);

		if (!signForAPIResponse.equals(signFromAPIResponse)) {

			// 签名验不过，表示这个API返回的数据有可能已经被篡改了
			logger.error("API返回的数据签名验证不通过，有可能被第三方篡改!!! signForAPIResponse生成的签名为"
					+ signForAPIResponse);
			System.out.println("API返回的数据签名验证不通过，有可能被第三方篡改!!! signForAPIResponse生成的签名为"
							+ signForAPIResponse);
			return false;

		}

		System.out.println("恭喜，API返回的数据签名验证通过!!!");

		return true;

	}

	private String returnXML(String return_code) {

		return "<xml><return_code><![CDATA["

		+ return_code

		+ "]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
	}

	public String getSign(Map<String, String> map) {
		SortedMap<String, String> signParams = new TreeMap<String, String>();
		for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
			signParams.put(stringStringEntry.getKey(),
					stringStringEntry.getValue());
		}
		signParams.remove("sign");
		String sign = WeixinUtil.getSignValue(signParams);
		return sign;
	}
	
	public IOrderDetailDao getOrderDetailDao() {
		return orderDetailDao;
	}

	@Autowired
	public void setOrderDetailDao(IOrderDetailDao orderDetailDao) {
		this.orderDetailDao = orderDetailDao;
	}
}
