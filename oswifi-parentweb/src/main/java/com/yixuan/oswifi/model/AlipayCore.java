package com.yixuan.oswifi.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class AlipayCore {

	/**
	 * 除去数组中的空值和签名参数
	 * 
	 * @param map
	 * @return
	 */
	public static Map paraFilter(Map map) {

		Iterator iterator = map.entrySet().iterator();
		if (iterator.hasNext()) {
			Object object = (Object) iterator.next();
			Object value = (Object) map.get(object);
			remove(object,value, iterator);
		}

		return map;
	}

	private static void remove(Object objectkey,Object obj, Iterator iterator) {
		String key = (String) objectkey;
		if(key.equalsIgnoreCase("sign")  || key.equalsIgnoreCase("sign_type")){
			iterator.remove();
			return;
		}
		if (obj instanceof String) {
			String str = (String) obj;
			if (isEmpty(str)) { // 过滤掉为null和""的值 主函数输出结果map：{2=BB, 1=AA, 5=CC,
								// 8= }
			// if("".equals(str.trim())){ //过滤掉为null、""和" "的值 主函数输出结果map：{2=BB,
			// 1=AA, 5=CC}
				iterator.remove();
			}

		} else if (obj instanceof Collection) {
			Collection col = (Collection) obj;
			if (col == null || col.isEmpty()) {
				iterator.remove();
			}

		} else if (obj instanceof Map) {
			Map temp = (Map) obj;
			if (temp == null || temp.isEmpty()) {
				iterator.remove();
			}

		} else if (obj instanceof Object[]) {
			Object[] array = (Object[]) obj;
			if (array == null || array.length <= 0) {
				iterator.remove();
			}
		} else {
			if (obj == null) {
				iterator.remove();
			}
		}
	}

	public static boolean isEmpty(Object obj) {
		return obj == null || obj.toString().length() == 0;
	}

	public static String createLinkString(Map map) {
		StringBuffer sb = new StringBuffer();
		Iterator<Entry<String, String>> entries = map.entrySet().iterator();

		while (entries.hasNext()) {
			Map.Entry entry = (Map.Entry) entries.next();
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			sb.append(key + "=" + value + "&");
		}
		return sb.substring(0, sb.length() - 1);
	}

	private void signAllString(String[] array) {
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < array.length; i++) {
			if (i == (array.length - 1)) {
				sb.append(array[i]);
			} else {
				sb.append(array[i] + "&");
			}
		}
	}
}
