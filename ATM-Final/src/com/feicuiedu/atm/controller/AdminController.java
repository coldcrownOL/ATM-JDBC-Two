package com.feicuiedu.atm.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.feicuiedu.atm.entity.User;
import com.feicuiedu.atm.service.UserService;
import com.feicuiedu.atm.util.DateUtil;
import com.feicuiedu.atm.util.PropUtil;
import com.feicuiedu.atm.view.AdminView;

/**
 * 管理员功能操作
 * @author 王东旭
 */
public class AdminController implements BaseController {

	private AdminView adminView;
	private User user;
	private UserService userService;
	
	@Override
	public String execute(Scanner scanner) {
		
		String result = null;
		
		while(true) {
			
			//显示管理员功能选择界面
			adminView = new AdminView();
			adminView.showView();

			userService = (UserService) PropUtil.getObjectFromProp("userService");
			
			String select = scanner.next();
			
				//1.开户
			if("1".equals(select)) {
				
				//接收用户信息
				user = new User();
				
//				String table = "users";
				//主键 自增
				int num = 1;
//				int num = 0;
//				try {
//					
//					num = userService.maxValue(table);
//					
//				} catch (Exception e1) {
//					e1.printStackTrace();
//				}
				user.setSerialNumber(num+1);
				
				//用户姓名 判断 name.length()>20 || name.length()<=0
				System.out.println("请输入姓名");
				String name = scanner.next();
				user.setUserName(name);
				
				//用户身份证号 判断idNumber.matches("[0-9]{18}")
				System.out.println("请输入身份证号");
				String idNumber = scanner.next();
				
				User rtnUser = null;
				try {
					
					rtnUser = userService.findByNumber(idNumber);
				
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				if(rtnUser != null) {
//					System.out.println("身份证号已开户,请重新输入");
					continue;
				}
				
				user.setIdNumber(idNumber);
				
				//用户性别
				System.out.println("请输入性别(1.男 2.女)");
				Integer gender = scanner.nextInt();
				user.setGender(gender);
				
				//用户出生日期
				System.out.println("请输入出生日期(yyyy-MM-dd)");
				String birthday = scanner.next();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					user.setBirthday(sdf.parse(birthday));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				//用户地址  判断 address.length()>50 || address.length()<=0
				System.out.println("请输入地址");
				String address = scanner.next();
				user.setAddress(address);
				
				//备注  判断 remarks.length()>200 || remarks.length()<=0
				System.out.println("请输入备注");
				String remarks = scanner.next();
				user.setAddress(remarks);
				
				//用户余额
				Double balance = 0.0;
				user.setBalance(balance);
				
				//用户状态
				Integer status = 1;
				user.setStatus(status);
				
				try {
					
					userService.openAccount(user);
					
					//账号
					String account = "BC180"+ gender + DateUtil.dateToStringOne() + PropUtil.getRandom();
//					user.setAccountNumber(account);
					
					//密码  判断password1.matches("^(?=.*[\\x00-\\xff].*)(?=.*[0-9].*)(?=.*[A-Z].*)(?=.*[a-z].*).{6}$")
					System.out.println("请输入密码");
					String password = scanner.next();
//					user.setPassword(password);
					
					userService.updateByIdNumber(account, password,idNumber);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				continue;
				
				//2.销户 修改用户状态为 已销户状态  
			} else if("2".equals(select)) {

				//接收用户账号
				System.out.println("请输入您要消除的账号");
				String account = scanner.next();
				
				try {
					
					userService.salesByAccount(account);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				continue;
				
				//3.检索普通用户 (能够正常登陆的) 主键|姓名|密码|身份证号|性别|卡号|余额
			} else if("3".equals(select)) {
				
				int status = 1;
				
				List<User> list = new ArrayList<>();
				
				//检索正常账户
				try {
					
					list = userService.queryByStatus(status);
					
					//遍历读取每一条用户信息
					for (User user : list) {
						
						System.out.println(user);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				continue;
				
				//4.检索已销户账户  主键|姓名|密码|身份证号|性别|卡号|余额|状态
			} else if("4".equals(select)) {
				
				int status = 2;
				
				List<User> list = new ArrayList<>();
				
				//检索已销户账户
				try {
					
					list = userService.queryByStatus(status);
					
					//遍历读取每一条已销户账户信息
					for (User user : list) {
						
						System.out.println(user);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				continue;
				
				// 5.检索锁定账户  主键|姓名|密码|身份证号|性别|卡号|余额|状态
			} else if("5".equals(select)){
				
				int status = 3;
				
				List<User> list = new ArrayList<>();
				
				//检索锁定账户
				try {
					
					list = userService.queryByStatus(status);
					
					//遍历读取每一条已锁定账户信息
					for (User user : list) {
						
						System.out.println(user);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				continue;
				
				//6.解除锁定账户    输入账户 ,解除锁定
			} else if("6".equals(select)) {
				
				//接收用户账号
				System.out.println("请输入您要解除锁定的账号");
				String account = scanner.next();
				
				//解除锁定
				try {
					
					userService.unlockByStatus(account);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				continue;
				
				//7.重置密码    输入账户 & 新密码
			} else if("7".equals(select)) {
				
				//接收用户账号
				System.out.println("请输入您要修改密码的账号");
				String account = scanner.next();
				
				//接收用户密码
				System.out.println("请输入新密码");
				String password = scanner.next();
				
				//重置密码
				try {
					
					userService.updateByAccount(account,password);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				continue;
				
				//8.返回上一级
			} else if("8".equals(select)) {
				
				result  = "systemController";
				break;
				
				//9.退卡
			}  else if("9".equals(select)) {
				
				result  = "exit";
				break;
				
			} else {
				
				System.out.println("请按照提示进行操作!");
				continue;
				
			}
			
//			result = "adminController";
//			break;
			
		}
		
		return result;
		
	}

}
