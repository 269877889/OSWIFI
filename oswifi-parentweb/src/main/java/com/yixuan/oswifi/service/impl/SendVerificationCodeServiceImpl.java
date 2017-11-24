package com.yixuan.oswifi.service.impl;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.bcloud.msg.http.HttpSender;
import com.yixuan.oswifi.dao.ISendVerificationCodeDao;
import com.yixuan.oswifi.model.RequestParameterEntity;
import com.yixuan.oswifi.model.ResponseParameterEntity;
import com.yixuan.oswifi.model.SendVerificationCodeRequestEntity;
import com.yixuan.oswifi.model.VerificationCodeInfo;
import com.yixuan.oswifi.service.IQueryTableIdService;
import com.yixuan.oswifi.service.ISendVerificationCodeService;
import com.yixuan.oswifi.util.ESBHeaderConstant;
import com.yixuan.oswifi.util.ParameterUtil;

public class SendVerificationCodeServiceImpl implements
		ISendVerificationCodeService<Object> {

	private static final String T_OSWIFI_LOGININFO = "t_oswifi_logininfo";

	private ISendVerificationCodeDao sendVerificationCodeDao;

	private IQueryTableIdService queryTableIdService;

	@Override
	public ResponseParameterEntity<Object> processRestRequest(
			RequestParameterEntity request, HttpServletRequest request2,
			HttpServletResponse response2) {
		ResponseParameterEntity<Object> response = new ResponseParameterEntity<Object>();
		response.setStatus(ParameterUtil.RETURN_SUCCESS_CODE);
		List<SendVerificationCodeRequestEntity> requestlist = null;
		VerificationCodeInfo info = new VerificationCodeInfo();
		try {
			requestlist = ParameterUtil.changeToManyBusiness(
					request.getRequestEntity(),
					SendVerificationCodeRequestEntity.class);
		} catch (Exception e) {
			response.setResultFlag(false);
			response.setResponseEntity(null);
			response.setStatus(ParameterUtil.RETURN_CHECK_PARAMS_FALSE);
			response.setFailureReason("参数为空");
			response2.addHeader(ESBHeaderConstant.RESULTCODE,
					ESBHeaderConstant.REQUEST_ESB_WEBSERVICE_SUCCESS);
			return response;
		}
		if (null == requestlist
				|| StringUtils.isBlank(requestlist.get(0).getTelephone())) {
			response.setResultFlag(false);
			response.setResponseEntity(null);
			response.setStatus(ParameterUtil.RETURN_CHECK_PARAMS_FALSE);
			response.setFailureReason("电话号码为空");
			response2.addHeader(ESBHeaderConstant.RESULTCODE,
					ESBHeaderConstant.REQUEST_ESB_WEBSERVICE_SUCCESS);
			return response;
		}

		String verificationCode = String.valueOf((int) (Math.random() * 10000));
		boolean havesend = sendVerificationCode(requestlist.get(0).getTelephone(), verificationCode);
		if (havesend) {

			info.setTelephone(requestlist.get(0).getTelephone());
			info.setLogicstate(0);
			VerificationCodeInfo oldinfo = sendVerificationCodeDao.queryVerificationCodeInfo(info);
			if (null == oldinfo) {
				int id = queryTableIdService.queryTableIdbyTableName(T_OSWIFI_LOGININFO);
				info.setId(id);
			} else {
				info.setId(oldinfo.getId());
			}
			info.setIdentifyingCode(verificationCode);

			if (null == oldinfo) {
				sendVerificationCodeDao.insertVerificationCodeInfo(info);
			} else {
				sendVerificationCodeDao.updateVerificationCodeInfo(info);
			}

			response.setResultFlag(true);
			response.setFailureReason("发送成功");
		} else {
			response.setResultFlag(false);
			response.setStatus(ParameterUtil.RETURN_SYSTEM_DW_ERROR);
			response.setFailureReason("发送失败");
		}
		response2.addHeader(ESBHeaderConstant.RESULTCODE,
				ESBHeaderConstant.REQUEST_ESB_WEBSERVICE_SUCCESS);
		return response;
	}

	private boolean sendVerificationCode(String telephone,
			String verificationCode) {
		String uri = "http://114.55.30.192/msg/HttpSendSM";// 应用地址
		String account = "zhao123";// 账号
		String pswd = "zhao123ZHAO";// 密码
		String mobiles = telephone;// 手机号码，多个号码使用","分割
		String content = "【壹轩文化】洋葱侠登录短信验证码：" + verificationCode;// 短信内容
		boolean needstatus = true;// 是否需要状态报告，需要true，不需要false
		String product = "2404";// 产品ID
		String extno = "000001";// 扩展码
		try {
			String returnString = HttpSender.send(uri, account, pswd, mobiles,
					content, needstatus, product, extno);
			if (returnString.indexOf(",") != -1) {
				String returnCode = returnString.substring(
						returnString.indexOf(",") + 1,
						returnString.indexOf("\n"));
				if (returnCode.equals("0")) {
					return true;
				}
			}
		} catch (Exception e) {
			// TODO 处理异常
			e.printStackTrace();
		}
		return false;

	}

	public IQueryTableIdService getQueryTableIdService() {
		return queryTableIdService;
	}

	public void setQueryTableIdService(IQueryTableIdService queryTableIdService) {
		this.queryTableIdService = queryTableIdService;
	}

	public ISendVerificationCodeDao getSendVerificationCodeDao() {
		return sendVerificationCodeDao;
	}

	@Autowired
	public void setSendVerificationCodeDao(
			ISendVerificationCodeDao sendVerificationCodeDao) {
		this.sendVerificationCodeDao = sendVerificationCodeDao;
	}

}
