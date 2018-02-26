package com.feicuiedu.atm.service;

import java.util.Scanner;

import com.feicuiedu.atm.entity.User;

/**
 * 系统登录判断接口
 * @author 王东旭
 */
public interface SystemService {
	
	/**
	 * 普通用户登录判断
	 */
	public User userLogin(Scanner scanner) throws Exception;

	
	/**
	 * 根据账户或者身份证号,锁定用户
	 */
	public void unlockByNumber(int status,String number) throws Exception;
	
}
