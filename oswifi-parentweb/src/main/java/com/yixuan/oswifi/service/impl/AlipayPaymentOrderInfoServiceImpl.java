package com.yixuan.oswifi.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.yixuan.oswifi.dao.IOrderDetailDao;
import com.yixuan.oswifi.dao.IPackageDetailDao;
import com.yixuan.oswifi.model.AlipayCore;
import com.yixuan.oswifi.model.OrderDetailInfo;
import com.yixuan.oswifi.model.PackageDetailResponseEntity;
import com.yixuan.oswifi.model.PackageListRequestEntity;
import com.yixuan.oswifi.model.AlipayPaymentOrderInfoRequestEntity;
import com.yixuan.oswifi.model.AlipayPaymentOrderInfoResponseEntity;
import com.yixuan.oswifi.model.RequestParameterEntity;
import com.yixuan.oswifi.model.ResponseParameterEntity;
import com.yixuan.oswifi.service.IAlipayPaymentOrderInfoService;
import com.yixuan.oswifi.service.IQueryTableIdService;
import com.yixuan.oswifi.util.AlipayConfig;
import com.yixuan.oswifi.util.AlipayUtil;
import com.yixuan.oswifi.util.ESBHeaderConstant;
import com.yixuan.oswifi.util.ParameterUtil;
import com.yixuan.oswifi.util.PayUtil;

/**
 * about Payment order information
 * 
 * @author sjj
 * 
 */
public class AlipayPaymentOrderInfoServiceImpl implements IAlipayPaymentOrderInfoService<AlipayPaymentOrderInfoResponseEntity> {

	private static final String T_OSWIFI_ORDER = "t_oswifi_order";
	
	private IPackageDetailDao PackageDetailDao; 
	
	private IOrderDetailDao orderDetailDao;
	
	private IQueryTableIdService queryTableIdService;
	
	@Override
	public ResponseParameterEntity<AlipayPaymentOrderInfoResponseEntity> processRestRequest(
			RequestParameterEntity request, HttpServletRequest request2,
			HttpServletResponse response2) {
		response2.addHeader(ESBHeaderConstant.RESULTCODE, ESBHeaderConstant.REQUEST_ESB_WEBSERVICE_SUCCESS);
		ResponseParameterEntity<AlipayPaymentOrderInfoResponseEntity> response = new ResponseParameterEntity<AlipayPaymentOrderInfoResponseEntity>();
		response.setStatus(ParameterUtil.RETURN_SUCCESS_CODE);
		List<AlipayPaymentOrderInfoResponseEntity> responselist = new ArrayList<AlipayPaymentOrderInfoResponseEntity>();
		AlipayPaymentOrderInfoResponseEntity responseentity = new AlipayPaymentOrderInfoResponseEntity();
		List<AlipayPaymentOrderInfoRequestEntity> list = null;
		AlipayPaymentOrderInfoRequestEntity requestentity = null;
		try {
			list = ParameterUtil.changeToManyBusiness(
					request.getRequestEntity(), AlipayPaymentOrderInfoRequestEntity.class);
		} catch (Exception e) {

		}
		if(null == list || list.size() == 0 ||null == list.get(0)){
			response.setResultFlag(false);
			response.setFailureReason("请求参数为空");;
			return response;
		}
		
		requestentity = list.get(0);

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
		info.setModeOfPayment("Alipay");
		orderDetailDao.insertOrderDetail(info);
		
		String orderStr = aliPay(packageentity.getPackagePrice(),packageentity.getPackageName(),requestentity.getOrder_no(),packageentity.getPackageContent());
		responseentity.setData(orderStr);
		responselist.add(responseentity);
		response.setResponseEntity(responselist);
		response.setResultFlag(true);
		response.setFailureReason("请求成功");;
		return response;
	}

    public String aliPay(BigDecimal amount,String subject,String order_no,String body){
        //实例化客户端
//        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", alipay_appId, alipay_private_key , "json", charset, alipay_public_key, "RSA2");
    	AlipayClient alipayClient = AlipayUtil.getAlipayClient();
    	//实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
//        model.setPassbackParams(body);;  //描述信息  添加附加数据
        model.setBody(body);
        model.setSubject(subject); //商品标题
        model.setOutTradeNo(order_no); //商家订单编号
        model.setTimeoutExpress("30m"); //超时关闭该订单时间
        model.setTotalAmount(String.valueOf(amount));  //订单总金额
        model.setProductCode("QUICK_MSECURITY_PAY"); //销售产品码，商家和支付宝签约的产品码，为固定值QUICK_MSECURITY_PAY
        request.setBizModel(model);
        request.setNotifyUrl(AlipayConfig.service);//回调地址
        String orderStr = "";
        try {
                //这里和普通的接口调用不同，使用的是sdkExecute
                AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
                orderStr = response.getBody();
            } catch (AlipayApiException e) {
                e.printStackTrace();
        }
        return orderStr;
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
