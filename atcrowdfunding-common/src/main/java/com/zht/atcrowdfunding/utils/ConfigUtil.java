package com.zht.atcrowdfunding.utils;

import java.util.ResourceBundle;

public class ConfigUtil {

	//专门用来做配置文件读取
	private static ResourceBundle rb = ResourceBundle.getBundle("config");
	
	public static String getValueByKey(String key) {
		return rb.getString(key);
	}
}
