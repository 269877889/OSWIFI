package com.yixuan.oswifi.util;

import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.DefaultAlipayClient;
public class AlipayUtil {

    // 统一收单交易创建接口
    private static AlipayClient alipayClient = null;

    public static AlipayClient getAlipayClient() {
        if (alipayClient == null) {
            synchronized (AlipayUtil.class) {
                if (null == alipayClient) {
                    alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", AlipayConfig.app_id,
                    		AlipayConfig.private_key, AlipayConstants.FORMAT_JSON, AlipayConstants.CHARSET_UTF8,
                    		AlipayConfig.alipay_public_key);
                }
            }
        }
        return alipayClient;
    }
}
