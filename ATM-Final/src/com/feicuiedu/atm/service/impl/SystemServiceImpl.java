package com.feicuiedu.atm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.feicuiedu.atm.dao.UserDao;
import com.feicuiedu.atm.entity.User;
import com.feicuiedu.atm.service.SystemService;
import com.feicuiedu.atm.util.PropUtil;

/**
 * 登录判断
 * @author 王东旭
 */
public class SystemServiceImpl implements SystemService {

	private UserDao userDao;
	
	public SystemServiceImpl() {
		super();
		userDao = (UserDao) PropUtil.getObjectFromProp("userDao");
	}
	
	/**
	 * 用户登录控制
	 */
	@Override
	public User userLogin(Scanner scanner) throws Exception {

		System.out.println("请输入您的账号或身份证号");
		String number = scanner.next();
		
		//查询账号是否存在
		User rtnUser = userDao.findByNumber(number);
		
		if (rtnUser == null) {
			
			System.out.println("该用户不存在");
			
			//存在继续输入密码
		} else {
			
			//三次不同,直接锁定账号
			int i = 1;
			while(true){
				
				List<String> list = new ArrayList<>();
				list.add(rtnUser.toString());
				
				String passwords = null;
				//获取当前账户的密码
				for (int j = 0; j < list.size(); j++) {
					
					String str = list.get(j);
					
					//分割用户信息
					String[] aArray = str.split("\\|");
					
					//密码
					String word = aArray[2];
					String[] words = word.split("=");
					passwords = words[1];
					
				}
				
				System.out.println("请输入密码");
				String password = scanner.next();
				
				System.out.println("请再次输入密码");
				String passwordTwo = scanner.next();
				
				if((!password.equals(passwordTwo)) || (!password.equals(passwords))){
					
					int num = i++;

					//如果3次密码错误,锁定账户
					if(num == 3){
						
						int status = 3;
						userDao.unlockByNumber(status, number);
//						System.out.println("账户已锁定");
						rtnUser = null;
						break;
					}
					
					System.out.println("密码错误,请重新输入");
					continue;
					
				} else {
					
					System.out.println("登录成功");
					break;
				}
			}
		}
		
		return rtnUser;
	}

	/**
	 * 根据账户或者身份证号,锁定用户
	 */
	@Override
	public void unlockByNumber(int status, String number) throws Exception {
		
		userDao.unlockByNumber(status, number);
		
	}

}
