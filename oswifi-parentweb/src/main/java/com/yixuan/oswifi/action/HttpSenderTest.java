package com.yixuan.oswifi.action;

import com.bcloud.msg.http.HttpSender;

public class HttpSenderTest {

	public static void main(String[] args) {
		int verificationCode = 1111;
		String uri = "http://114.55.30.192/msg/HttpSendSM";//应用地址
		String account = "zhao123";//账号
		String pswd = "zhao123ZHAO";//密码
		String mobiles = "18701872151";//手机号码，多个号码使用","分割
		String content = "【壹轩文化】洋葱侠登录短信验证码："+verificationCode;
		boolean needstatus = true;//是否需要状态报告，需要true，不需要false
		String product = "2404";//产品ID
		String extno = "001";//扩展码
		try {
			String returnString = HttpSender.send(uri, account, pswd, mobiles, content, needstatus, product, extno);
			System.out.println(returnString);
			
			if(returnString.indexOf(",") != -1){
				String s = returnString.substring(returnString.indexOf(",")+1, returnString.indexOf("\n"));
			}
			//TODO 处理返回值,参见HTTP协议文档
		} catch (Exception e) {
			//TODO 处理异常
			e.printStackTrace();
		}
//		try {
//			String returnString = HttpSender.batchSend(uri, account, pswd, mobiles, content, needstatus, product, extno);
//			System.out.println(returnString);
//			//TODO 处理返回值,参见HTTP协议文档
//		} catch (Exception e) {
//			//TODO 处理异常
//			e.printStackTrace();
//		}
	}
}
