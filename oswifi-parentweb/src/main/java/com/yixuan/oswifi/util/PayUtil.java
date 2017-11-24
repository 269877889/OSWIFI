package com.yixuan.oswifi.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.alipay.api.AlipayConstants;
public class PayUtil {

	/** 
     * 产生随机的2位数 
     * @return 
     */  
    public static String getTwo(){  
        Random rad=new Random();  
  
        String result  = rad.nextInt(100) +"";  
  
        if(result.length()==1){  
            result = "0" + result;  
        }  
        return result;  
    }
    
	  /**
     * 生成订单号
     * 
     * @return
     */
    public static String getTradeNo() {
    	 String date = new SimpleDateFormat("yyyyMMdd").format(new Date());  
         String seconds = new SimpleDateFormat("HHmmss").format(new Date());
         return "TNO" +date+"00001000"+getTwo()+seconds+getTwo();
        // 自增8位数 00000001
//        return "TNO" + DatetimeUtil.formatDate(new Date(), DatetimeUtil.TIME_STAMP_PATTERN) + "00000001";
    }

    /**
     * 退款单号
     * 
     * @return
     */
    public static String getRefundNo() {
    	 String date = new SimpleDateFormat("yyyyMMdd").format(new Date());  
         String seconds = new SimpleDateFormat("HHmmss").format(new Date());
         return "RNO" +date+"00001000"+getTwo()+seconds+getTwo();
        // 自增8位数 00000001
//        return "RNO" + DatetimeUtil.formatDate(new Date(), DatetimeUtil.TIME_STAMP_PATTERN) + "00000001";
    }

    /**
     * 退款单号
     * 
     * @return
     */
    public static String getTransferNo() {
    	 String date = new SimpleDateFormat("yyyyMMdd").format(new Date());  
         String seconds = new SimpleDateFormat("HHmmss").format(new Date());
         return "TNO" +date+"00001000"+getTwo()+seconds+getTwo();
        // 自增8位数 00000001
//        return "TNO" + DatetUtil.formatDate(new Date(), DatetimeUtil.TIME_STAMP_PATTERN) + "00000001";
    }

    /**
     * 返回客户端ip
     * 
     * @param request
     * @return
     */
    public static String getRemoteAddrIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }

    /**
     * 获取服务器的ip地址
     * 
     * @param request
     * @return
     */
    public static String getLocalIp(HttpServletRequest request) {
        return request.getLocalAddr();
    }

    /**
     * 创建支付随机字符串
     * 
     * @return
     */
    public static String getNonceStr() {
    	 String base = "abcdefghijklmnopqrstuvwxyz0123456789";  
         Random random = new Random();  
         StringBuffer sb = new StringBuffer();  
         for (int i = 0; i < 32; i++) {  
             int number = random.nextInt(base.length());  
             sb.append(base.charAt(number));  
         }  
         return sb.toString();
    }

    /**
     * 支付时间戳
     * 
     * @return
     */
    public static String payTimestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    /**
     * 返回签名编码拼接url
     * 
     * @param params
     * @param isEncode
     * @return
     */
    public static String getSignEncodeUrl(Map<String, String> map, boolean isEncode) {
        String sign = map.get("sign");
        String encodedSign = "";
        if (!CollectionUtils.isEmpty(map)) {
            map.remove("sign");
            List<String> keys = new ArrayList<String>(map.keySet());
            // key排序
            Collections.sort(keys);

            StringBuilder authInfo = new StringBuilder();

            boolean first = true;// 是否第一个
            for (String key: keys) {
                if (first) {
                    first = false;
                } else {
                    authInfo.append("&");
                }
                authInfo.append(key).append("=");
                if (isEncode) {
                    try {
                        authInfo.append(URLEncoder.encode(map.get(key), AlipayConstants.CHARSET_UTF8));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else {
                    authInfo.append(map.get(key));
                }
            }

            try {
                encodedSign = authInfo.toString() + "&sign=" + URLEncoder.encode(sign, AlipayConstants.CHARSET_UTF8);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return encodedSign.replaceAll("\\+", "%20");
    }

    /**
     * 对支付参数信息进行签名
     * 
     * @param map
     *            待签名授权信息
     * 
     * @return
     */
    public static String getSign(Map<String, String> map, String rsaKey) {
        List<String> keys = new ArrayList<String>(map.keySet());
        // key排序
        Collections.sort(keys);

        StringBuilder authInfo = new StringBuilder();
        boolean first = true;
        for (String key : keys) {
            if (first) {
                first = false;
            } else {
                authInfo.append("&");
            }
            authInfo.append(key).append("=").append(map.get(key)); 
        }

        return SignUtils.sign(authInfo.toString(), rsaKey);
    }
}
