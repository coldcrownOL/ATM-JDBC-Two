package com.feicuiedu.atm.controller;

import java.util.Scanner;

import com.feicuiedu.atm.view.SystemView;

/**
 * 选择登录对象
 * @author 王东旭
 */
public class SystemController implements BaseController {

	private SystemView systemView;
	
	@Override
	public String execute(Scanner scanner) {
		
		String result = null;
		
		//1.管理员登录 2.用户登录 3.退卡
		while(true){
			
			//显示登录选项 1.管理员  2.用户
			systemView = new SystemView();
			systemView.showView();
			
			String select = scanner.next();
			
				// 1.管理员登录
			if("1".equals(select)) {
				
				//判断账号和密码
				while(true){
					
					System.out.println("请输入管理员账户(admin):");
					String admin = scanner.next();
					
					if(!"admin".equals(admin)) {
						System.out.println("账户错误,请重新输入!");
						continue;
					}
					
					System.out.println("请输入账户密码(123456):");
					String password = scanner.next();
					
					if(!"123456".equals(password)) {
						System.out.println("密码错误,请重新输入!");
						continue;
					}
					
					System.out.println("登录成功!");
					break;
					
				}
				
				result = "adminController";
				
				// 2.普通用户登录
			} else if("2".equals(select)) {
				
//				System.out.println("请输入您的账号或身份证号");
//				String number = scanner.next();
//				
//				//查询是否存在
//				User user = null;
//				try {
//					
//					user = userService.findByNumber(number);
//					
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				
//				if(user != null){
//					
//					int i = 1;
//					while(true){
//						
//						System.out.println("请输入密码");
//						String password = scanner.next();
//						
//						System.out.println("请再次输入密码");
//						String passwordTwo = scanner.next();
//						
//						if(!password.equals(passwordTwo)){
//							
//							System.out.println("两次输入不一样,请重新输入");
//							
//							int num = i++;
//
//							//如果3次密码错误,锁定账户
//							if(num == 3){
//								
//								int status = 3;
//								try {
//									
//									systemService.unlockByNumber(status, number);
//									
//								} catch (Exception e) {
//									e.printStackTrace();
//								}
//								
//								System.out.println("账户已锁定");
//								result = "systemController";
//								break;
//							}
//							continue;
//							
//						} else {
//							System.out.println("登录成功");
//							result = "userController";
//							break;
//						}
//					}
//					
//				} else {
//					result = "systemController";
//				}
				
				result = "userController";
				
				// 3.退卡
			} else if("3".equals(select)) {
				
				result = "exit";
				
			} else {
				
				System.out.println("请按照提示进行操作!");
				continue;
				
			}
			
			break;
			
		}
		
		return result;
		
	}

}
