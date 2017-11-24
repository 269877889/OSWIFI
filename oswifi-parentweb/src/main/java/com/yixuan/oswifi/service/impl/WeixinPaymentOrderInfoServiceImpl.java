package com.yixuan.oswifi.service.impl;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.geronimo.mail.util.Base64Encoder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfigImpl;
import com.github.wxpay.sdk.WXPayUtil;
import com.yixuan.oswifi.dao.IOrderDetailDao;
import com.yixuan.oswifi.dao.IPackageDetailDao;
import com.yixuan.oswifi.model.OrderDetailInfo;
import com.yixuan.oswifi.model.PackageDetailResponseEntity;
import com.yixuan.oswifi.model.RequestParameterEntity;
import com.yixuan.oswifi.model.ResponseParameterEntity;
import com.yixuan.oswifi.model.WeixinPaymentOrderInfoRequestEntity;
import com.yixuan.oswifi.model.WeixinPaymentOrderInfoResponseEntity;
import com.yixuan.oswifi.service.IQueryTableIdService;
import com.yixuan.oswifi.service.IWeixinPaymentOrderInfoService;
import com.yixuan.oswifi.util.ESBHeaderConstant;
import com.yixuan.oswifi.util.ParameterUtil;
import com.yixuan.oswifi.util.PayUtil;
import com.yixuan.oswifi.util.WeixinConfig;
import com.yixuan.oswifi.util.WeixinUtil;

public class WeixinPaymentOrderInfoServiceImpl implements IWeixinPaymentOrderInfoService<WeixinPaymentOrderInfoResponseEntity> {

	private static final Logger logger = Logger.getLogger(WeixinPaymentOrderInfoServiceImpl.class);
	
	 // 全局数组
    private final static String[] strDigits = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	
	private static final String T_OSWIFI_ORDER = "t_oswifi_order";
	
	private IPackageDetailDao PackageDetailDao; 
	
	private IOrderDetailDao orderDetailDao;
	
	private IQueryTableIdService queryTableIdService;
	
	
	@Override
	public ResponseParameterEntity<WeixinPaymentOrderInfoResponseEntity> processRestRequest(
			RequestParameterEntity request, HttpServletRequest request2,
			HttpServletResponse response2) {
		ResponseParameterEntity<WeixinPaymentOrderInfoResponseEntity> response = new ResponseParameterEntity<WeixinPaymentOrderInfoResponseEntity>();
		response.setStatus(ParameterUtil.RETURN_SUCCESS_CODE);
		List<WeixinPaymentOrderInfoResponseEntity> responselist= new ArrayList<WeixinPaymentOrderInfoResponseEntity>();
		WeixinPaymentOrderInfoResponseEntity responseentity = new WeixinPaymentOrderInfoResponseEntity();
		List<WeixinPaymentOrderInfoRequestEntity> requestentitylist = ParameterUtil.changeToManyBusiness(request.getRequestEntity(), WeixinPaymentOrderInfoRequestEntity.class);
		response2.addHeader(ESBHeaderConstant.RESULTCODE, ESBHeaderConstant.REQUEST_ESB_WEBSERVICE_SUCCESS);
		if(null == requestentitylist || null == requestentitylist.get(0)){
			response.setFailureReason("参数为空");
			response.setStatus(ParameterUtil.RETURN_CHECK_PARAMS_FALSE);
			response.setResultFlag(false);
			return response;
		}
		WeixinPaymentOrderInfoRequestEntity requestentity = requestentitylist.get(0);
		if(StringUtils.isBlank(requestentity.getTelephone())){
			return evaluateResponse(response,"手机号码为空");
		}
		
		if(StringUtils.isBlank(requestentity.getClientOS())){
			return evaluateResponse(response,"客户端操作系统为空");
		}
		
		if(0 == requestentity.getPackageId()){
			return evaluateResponse(response,"套餐id为空");
		}
		
		if(StringUtils.isBlank(requestentity.getClientOS()) ||
			(!"IOS".equalsIgnoreCase(requestentity.getClientOS()) &&
			!"Android".equalsIgnoreCase(requestentity.getClientOS()))
			){
			return evaluateResponse(response,"客户端操作系统名称不正确");
		}
		
		//如果没有订单号就重新生成一个
		if(StringUtils.isBlank(requestentity.getOrder_no())){
			requestentity.setOrder_no(PayUtil.getTradeNo());
		}
		
		PackageDetailResponseEntity packageentity = PackageDetailDao.queryPackageDetail(requestentity.getPackageId());
		
		if(null == packageentity){
			response.setResultFlag(false);
			response.setStatus(ParameterUtil.RETURN_CHECK_PARAMS_INVALID);
			response.setFailureReason("套餐id错误");;
			return response;
		}
		
		OrderDetailInfo info = new OrderDetailInfo();
		info.setId(queryTableIdService.queryTableIdbyTableName(T_OSWIFI_ORDER));
		info.setTelephone(requestentity.getTelephone());
		info.setOrderNo(requestentity.getOrder_no());
		info.setPackageId(requestentity.getPackageId());
		info.setPackageName(packageentity.getPackageName());
		info.setPrice(packageentity.getPackagePrice());
		info.setDays(packageentity.getPackageDays());
		info.setModeOfPayment("weixin");
		orderDetailDao.insertOrderDetail(info);
		
		SortedMap<String,String> parm = new TreeMap<String,String>();

//		Map<String,String> parm = new HashMap<String, String>();
		if("IOS".equals(requestentity.getClientOS())){
			parm.put("appid", WeixinConfig.IOSAppid);	
		}else{
			parm.put("appid", WeixinConfig.ANDROIDAppid);
		}
		
        parm.put("mch_id", WeixinConfig.mch_id);
//        parm.put("device_info", "WEB");
        parm.put("nonce_str", PayUtil.getNonceStr());
        parm.put("body", packageentity.getPackageContent());
        parm.put("attach", packageentity.getRemark());
        parm.put("out_trade_no", requestentity.getOrder_no());
        //微信的总金额，单位为分，数据库里面的为元
        parm.put("total_fee", (packageentity.getPackagePrice().multiply(new BigDecimal("100"))).toString());
        parm.put("spbill_create_ip", PayUtil.getRemoteAddrIp(request2));
        parm.put("notify_url", WeixinConfig.notify_url);
        parm.put("trade_type", "APP");
        String signValue = getSignValue((Map<String,String>)parm);
        //签名
        parm.put("sign",signValue);
        parm.put("sign_type","MD5");
        
        //封装请求参数
        String requestXML = WeixinUtil.getRequestXml(parm);
        
		String orderStr = ""; 
		
		String result  = WeixinUtil.httpsRequest(WeixinUtil.ORDER_PAY,"POST",requestXML);
		
		try {
			/**
			 * 统一下单接口返回正常的prepay_id，再按签名规范重新生成签名后，将数据传输给APP。参与签名的字段名为appId，
			 * partnerId，prepayId，nonceStr，timeStamp，package。注意：package的值格式为Sign=WXPay
			 **/
		 	Map<String, String> map = WXPayUtil.xmlToMap(result);
            SortedMap<String,String> parameterMap2 = new TreeMap<String,String>();  
            parameterMap2.put("appid", parm.get("appid"));  
            parameterMap2.put("partnerid", WeixinConfig.mch_id);  
            parameterMap2.put("prepayid", map.get("prepay_id"));  
            parameterMap2.put("package", "Sign=WXPay");  
            parameterMap2.put("noncestr", PayUtil.getNonceStr());  
            //本来生成的时间戳是13位，但是ios必须是10位，所以截取了一下
            if("IOS".equals(requestentity.getClientOS())){
            	parameterMap2.put("timestamp", String.valueOf(Long.parseLong(String.valueOf(System.currentTimeMillis()).toString().substring(0,10))));	
            }else{
            	parameterMap2.put("timestamp",String.valueOf(System.currentTimeMillis()));
            }
              
            String sign2 = getSignValue(parameterMap2);
            parameterMap2.put("sign", sign2);
            orderStr = JSONObject.toJSONString(parameterMap2);
            
		} catch (Exception e) {
			logger.error("调用 WXPayUtil.xmlToMap 时报错，具体原因为："+e.getMessage());
		}
		
//        winxinsign(parm);
		
		responseentity.setData(orderStr);
		responselist.add(responseentity);
		response.setResponseEntity(responselist);
		response.setResultFlag(true);
		response.setFailureReason("请求成功");;
		return response;
	}

	private String getSignValue(Map<String,String> parm){
		String stringA = WeixinUtil.formatUrlMap(parm, true, false);
		String stringSignTemp = stringA + "&key="+WeixinConfig.key;
		String result = "";
		try {
			result =  WXPayUtil.MD5(stringSignTemp);
		} catch (Exception e) {
			logger.error("MD5加密报错，具体原因为："+e.getMessage());
		}
		
		return result;
	}
	
//	/**
//	 * MD5加密
//	 * @param str
//	 * @return
//	 */
//	private String MD5(String str){
//		String resultString ="";
//		try {
//			MessageDigest md5 = MessageDigest.getInstance("MD5");
//			resultString = byteToString(md5.digest(str.getBytes()));
//		} catch (NoSuchAlgorithmException e) {
//			logger.error("MD5加密报错，具体原因为："+e.getMessage());
//		}
//		return resultString;
//	}
//	
//	 // 转换字节数组为16进制字串
//    private static String byteToString(byte[] bByte) {
//        StringBuffer sBuffer = new StringBuffer();
//        for (int i = 0; i < bByte.length; i++) {
//            sBuffer.append(byteToArrayString(bByte[i]));
//        }
//        return sBuffer.toString();
//    }
//	
//    // 返回形式为数字跟字符串
//    private static String byteToArrayString(byte bByte) {
//        int iRet = bByte;
//        if (iRet < 0) {
//            iRet += 256;
//        }
//        int iD1 = iRet / 16;
//        int iD2 = iRet % 16;
//        return strDigits[iD1] + strDigits[iD2];
//    }
    
	/**
	 * 使用微信的sdk
	 * @param params
	 * @return
	 */
	private String winxinsign(Map params){
		WXPayConfigImpl config;
		try {
			config = WXPayConfigImpl.getInstance();
			WXPay wxpay = new WXPay(config,WeixinConfig.notify_url,true, false);
			wxpay.unifiedOrder(params);
		} catch (Exception e) {
			logger.error("使用微信的sdk报错，具体原因为:"+e.getMessage());
		}
		
		return "";
	}
	
	private ResponseParameterEntity<WeixinPaymentOrderInfoResponseEntity> evaluateResponse(ResponseParameterEntity<WeixinPaymentOrderInfoResponseEntity> response,String name){
		response.setResultFlag(false);
		response.setStatus(ParameterUtil.RETURN_CHECK_PARAMS_FALSE);
		response.setFailureReason(name);
		return response;
	}
	
	 
		public IPackageDetailDao getPackageDetailDao() {
			return PackageDetailDao;
		}

		@Autowired
		public void setPackageDetailDao(IPackageDetailDao packageDetailDao) {
			PackageDetailDao = packageDetailDao;
		}
		
		public IOrderDetailDao getOrderDetailDao() {
			return orderDetailDao;
		}

		@Autowired
		public void setOrderDetailDao(IOrderDetailDao orderDetailDao) {
			this.orderDetailDao = orderDetailDao;
		}

		public IQueryTableIdService getQueryTableIdService() {
			return queryTableIdService;
		}

		public void setQueryTableIdService(IQueryTableIdService queryTableIdService) {
			this.queryTableIdService = queryTableIdService;
		}
}
