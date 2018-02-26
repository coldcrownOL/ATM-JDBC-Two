package com.feicuiedu.atm.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * 反射工具类
 * @author 王
 */
public class PropUtil {

	/**
	 * 根据key获取对应的值
	 * @param key
	 * @return
	 */
	public static String getPropValue(String key) {
		
		Properties prop = new Properties();
		
		try {
			
			BufferedReader br = new BufferedReader(new FileReader("src"+File.separator+"config.properties"));
			prop.load(br);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return prop.getProperty(key);
		
	}
	
	/**
	 * 根据key获取值返回对象
	 * @param key
	 * @return
	 */
	public static Object getObjectFromProp(String key) {
		
		String className = getPropValue(key);
		Object obj = null;
		
		try {
			
			obj = Class.forName(className).newInstance();
			
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return obj;
		
	}
	
	/**
	 * 随机生成4位数
	 * @return
	 */
	public static int getRandom() {
		
		return (int)((Math.random()*9+1)*1000);
		
	}
}
