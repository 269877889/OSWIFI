package com.yixuan.oswifi.util;

public class AlipayConfig {

	// 合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://openhome.alipay.com/platform/keyManage.htm?keyType=partner
	public static String partner = "2088721353172135";

	// 商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
	public static String private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCmU5WevVHZP9tAijRIGzLNgrcXrK7Qd/zXSFwPdmGiJtuVRHq4YF1Kdsn3PhCAviO14m14Cy24/KyxVqnxERjR3WJA+rnoEwco1DlJUyOYhNtB3m9DEv2nxha62PcGJbz70I8r/+4oBEFuFfjlwitkcLWuF4Md1rmLLj/5+Pq/CR5cZwF3Ji9v4c3TAgt/sy8K67F5kY6SDAulZwE3KpBk2j5j9nOiJwdhvrYJhwN/e6NV8JdD+eaku9NoV2xYSspoMYFKIR/xqRlZoXMWrfLUyElgGcLdW/aB+IW0tvpOqtGF5w4Y92tUD4a7VvsF8mnB7P/2fGYoDPqCmtXbaTRFAgMBAAECggEAB3TTzkv6cVzVfW00rs7ZVjE3+A27wIjAgv4xclgCRHytd2Gchst7Iy10HGth3gOoIi1Gbw1BPXvGWiTidXYowK/GCdezod3IgmIRN2JMxDeghLFuF3mweWb9+d5kJJhognl1EanliVG3hTHi+JndzCDpZCaVsC7CHqUyJQiuZQX3BwJ890B6reTNowiT/3OTsF6LNlWVngbvS0uT6f1UR3NVeKGN9HWrLsRjgtBmK/DU4W+9LnTP8/ElBJYagPp7yIrIAZEq9vxSJVga6jskk3ukFQ/aWAUqa49WjSEkiZ+ZisGz8SerXK+lxGJr30KeN13bUPkogZ2YwlSMtRLAuQKBgQDkuhX2UtMIW8qbU3XEWyrUnG9dhCiJTU985huCWEeM3HiTBuz84fH5TAAzmngSATBDxPMU49wDtB9PRwgbiOi0kSXtBWuwoxGAwn03TwhzHr6VTykUF2IKa+V2QoT5eUR4t5sBj6ZRcC1gXOPb6PmE36D2HzAq/W3zXvfJRR+uPwKBgQC6KLg2YhBp9whs5G42wkhMJXJuXvKFvO768qDIGdr5W3Q8W42usI5Pvv726hl84NfDDNdKaxifbq+aHIHCKhdCA8hwKh4fpy7DJBpHE/iN8lDXutt5SLhb5zeln+FYmRdNRektkpY9lYyLcRR4tvuNwE7xBck6Da8HJUP7iqiEewKBgCjuZB/AkqNcSdWiCeEpr3B8zjEeBWMMwrzHMQUSZzt9+NXW13IpvPOv9hlF+1a9f7Wg+R8z55uCEFhPjdPfnEUz9GWGIs9pXyh7pYzQR+LSDakKkX9W7+SNinKvVLyQRwCTWtncqxbHzFcCSISCHKT6oANrcWNAFv0GcbFC/XhhAoGARoHhs4dU3C2aXVPvgoH3TA/q1W7UwF6czSNUTjWswEEpNysd8IoifIXEn9RuruOiFNgqOp9xhgEwNViStO2oPXKdWg45tpdlmLd11HH19B1RDURSmA849GYQp4R0n0/siXHvLTmV+eFIiA/eRxJXGIs72wHsAzbnkfdFVmJgDT0CgYEAwWatpgfDLE7hL+Ph16nggV4fqhi1/u9UZ4JTDJYbL7QCbKUgmyf4oywO/HDXpGpCCGCPEVzoB/wmy4AYin8mlVXqiz/NWYTLBENT+Te4/z8vB6smoMn5/3z7/9g3n24Li/Gss+/iWnNurvep1BSv47KH7wqzFc8aA5CQN8znPZU=";

	// 支付宝的公钥，查看地址：https://openhome.alipay.com/platform/keyManage.htm?keyType=partner
	public static String alipay_public_key= "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
	// 签名方式
	public static String sign_type = "RSA";

	// 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。
	public static String log_path = "C://";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";

	// 接收通知的接口名
	public static String service = "http://47.93.218.98:8080/oswifi-parentweb/alipaycallback.action";

	// APPID
	public static String app_id = "2017062407560185";
	
	public static String alipay_trade_app_pay = "alipay.trade.app.pay";

}
