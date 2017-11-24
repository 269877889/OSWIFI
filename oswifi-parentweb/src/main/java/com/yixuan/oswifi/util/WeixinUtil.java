package com.yixuan.oswifi.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.github.wxpay.sdk.WXPayUtil;

/**
 * 微信支付的公共类
 * 
 */
public class WeixinUtil {

	private static final Logger logger = Logger.getLogger(WeixinUtil.class);
	
	/**
	 * 统一下单
	 */
	public static final String ORDER_PAY = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	/**
	 * 支付订单查询
	 */
	public static final String ORDER_PAY_QUERY = "https://api.mch.weixin.qq.com/pay/orderquery";

	/**
	 * 申请退款
	 */
	public static final String ORDER_REFUND = "https://api.mch.weixin.qq.com/secapi/pay/refund";

	/**
	 * 申请退款查询
	 */
	public static final String ORDER_REFUND_QUERY = "https://api.mch.weixin.qq.com/pay/refundquery";

	/**
	 * 
	 * 方法用途: 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序），并且生成url参数串<br>
	 * 实现步骤: <br>
	 * 
	 * @param paraMap
	 *            要排序的Map对象
	 * @param urlEncode
	 *            是否需要URLENCODE
	 * @param keyToLower
	 *            是否需要将Key转换为全小写 true:key转化成小写，false:不转化
	 * @return
	 */
	public static String formatUrlMap(Map<String, String> paraMap,
			boolean urlEncode, boolean keyToLower) {

		String buff = "";
		Map<String, String> tempmap = paraMap;

		List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(
				tempmap.entrySet());

		Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
			@Override
			public int compare(Entry<String, String> o1,
					Entry<String, String> o2) {
				return (o1.getKey()).toString().compareTo(o2.getKey());
			}
		});
		// 构造URL 键值对的格式
		StringBuilder buf = new StringBuilder();
		for (Map.Entry<String, String> item : infoIds) {
			if (StringUtils.isNotBlank(item.getKey())) {
				String key = item.getKey();
				String val = item.getValue();

				if (urlEncode) {
					try {
						val = URLEncoder.encode(val, "utf-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
				if (keyToLower) {
					buf.append(key.toLowerCase() + "=" + val);
				} else {
					buf.append(key + "=" + val);
				}
				buf.append("&");
			}
		}
		buff = buf.toString();
		if (buff.isEmpty() == false) {
			buff = buff.substring(0, buff.length() - 1);
		}
		return buff;
	}
	
	
	  /**
     * @Description：将请求参数转换为xml格式的string
     * @param parameters  请求参数
     * @return
     */
    public static String getRequestXml(SortedMap<String,String> parameters){
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String key = (String)entry.getKey();
            String value = (String)entry.getValue();
            if ("attach".equalsIgnoreCase(key)||"body".equalsIgnoreCase(key)) {
                sb.append("<"+key+">"+"<![CDATA["+value+"]]></"+key+">");
            }else {
                sb.append("<"+key+">"+value+"</"+key+">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }
    
    /**
     * 发送https请求
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return 返回微信服务器响应的信息
     */
    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            //conn.setSSLSocketFactory(ssf);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded"); 
            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            return buffer.toString();
        } catch (ConnectException ce) {
        	logger.error("httpsRequest error ,具体原因："+ce.getMessage());
//          log.error("连接超时：{}", ce);
        } catch (Exception e) {
        	logger.error("httpsRequest1 error ,具体原因："+e.getMessage());
//          log.error("https请求异常：{}", e);
        }
        return null;
    }
    
	public static String getSignValue(Map<String,String> parm){
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
}
