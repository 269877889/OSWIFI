package com.yixuan.oswifi.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtil {

	public static String readInputStream2String(InputStream is){
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		 
		try {
		 
		while ((line = in.readLine()) != null) {
		 
		buffer.append(line);
		 
		}
		 
		} catch (IOException e) {
		 
		e.printStackTrace();
		 
		}
		return buffer.toString();
	}
}
