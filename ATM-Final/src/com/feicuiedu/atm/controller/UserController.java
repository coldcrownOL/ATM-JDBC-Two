package com.feicuiedu.atm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.feicuiedu.atm.entity.Trade;
import com.feicuiedu.atm.entity.User;
import com.feicuiedu.atm.service.SystemService;
import com.feicuiedu.atm.service.UserService;
import com.feicuiedu.atm.util.DateUtil;
import com.feicuiedu.atm.util.PropUtil;
import com.feicuiedu.atm.view.UserView;

/**
 * 用户个人账户操作
 * @author 王东旭
 */
public class UserController implements BaseController {

	private SystemService systemService;
	private UserView userView;
	private UserService userService;
	private User user;
	private Trade trade;
	
	@Override
	public String execute(Scanner scanner) {
		
		systemService = (SystemService) PropUtil.getObjectFromProp("systemService");
		
		String result = null;
		
		user = new User();
		//用户登录
		try {
			
			user = systemService.userLogin(scanner);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//如果为null,重新选择登录
		if(user == null){
			
			result = "systemController";
			
		} else {
			
			//获取当前登录账户信息
			List<String> list = new ArrayList<>();
			list.add(user.toString());
			
			//源账户的个人信息
			User userOwn = new User();
			
			//获取当前登录账户的账号和余额
			for (int i = 0; i < list.size(); i++) {
				
				String str = list.get(i);
				
				//分割用户信息
				String[] aArray = str.split("\\|");
				
				//账号
				String acccount = aArray[1];
				String[] acccounts = acccount.split("=");
				userOwn.setAccountNumber(acccounts[1]);
				
				//余额
				String banlance = aArray[6];
				String[] banlances = banlance.split("=");
				userOwn.setBalance(Double.parseDouble(banlances[1]));
				
			}
			
			while(true) {
				
				//显示用户功能选择界面
				userView = new UserView();
				userView.showView();
				
				userService = (UserService) PropUtil.getObjectFromProp("userService");

				String select = scanner.next();
				
				//源账户的交易记录
				trade = new Trade();
				
					//存款
				if("1".equals(select)) {
					
					//判断存款金额是否符号条件
					double money;
					while(true){
						
						System.out.println("您的余额为:"+userOwn.getBalance());
						System.out.println("请输入存款金额(100元的倍数，不得为0):");
						double selectMoney = scanner.nextDouble();

						//100整数，且不为0
						if(selectMoney%100 == 0 && selectMoney>0){
							
							money = selectMoney;
							break;	
							
						} else{
							
							System.out.println("请按照提示重新输入!");
							continue;
						}
						
					}
					
					//存款后的余额
					userOwn.setBalance(userOwn.getBalance()+money);
					
					//源账户信息
					trade.setSerialNumber(1);
					trade.setAmount(money);
					trade.setBalanceAfter(userOwn.getBalance());
					trade.setAccountOwn(userOwn.getAccountNumber());
					trade.setAccountOther(userOwn.getAccountNumber());
					trade.setType(1);
					trade.setTime(DateUtil.date());
					
					//修改账户余额,添加交易记录
					try {
						
						userService.updateByBalance(userOwn);
						userService.tradeInfo(trade);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					System.out.println("存款成功，现在的余额为:"+userOwn.getBalance());
					continue;
					
					//2.取款  
				} else if("2".equals(select)) {

					//判断原账户余额是否足够
					if(userOwn.getBalance()<100){
						System.out.println("余额为0,请先存款");
						continue;
					}
					
					//判断取款金额是否符号条件
					double money;
					while(true){
						
						System.out.println("您的余额为:"+userOwn.getBalance());
						System.out.println("请输入取款金额(100元的倍数，不得为0,不能大于余额):");
						double selectMoney = scanner.nextDouble();

						//100整数，且不为0  取款金额不得大于余额
						if((selectMoney%100 == 0 && selectMoney>0) && (selectMoney<=userOwn.getBalance())){
							money = selectMoney;
							break;
							
						} else{
							System.out.println("请按照提示重新输入!");
							continue;
						}
						
					}
					
					//取款后的余额
					userOwn.setBalance(userOwn.getBalance()-money);
					
					//源账户信息
					trade.setSerialNumber(1);
					trade.setAmount(-money);
					trade.setBalanceAfter(userOwn.getBalance());
					trade.setAccountOwn(userOwn.getAccountNumber());
					trade.setAccountOther(userOwn.getAccountNumber());
					trade.setType(2);
					trade.setTime(DateUtil.date());
					
					//修改账户余额,添加交易记录
					try {
						
						userService.updateByBalance(userOwn);
						userService.tradeInfo(trade);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					System.out.println("取款成功，现在的余额为:"+userOwn.getBalance());
					continue;
					
					//3.转账
				} else if("3".equals(select)) {
					
					//判断原账户余额是否足够
					if(userOwn.getBalance()<100){
						System.out.println("余额为0,请先存款");
						continue;
					}
					
					User userOther = new User();
					
					System.out.println("请输入对方账户");
					String accountOther = scanner.next();
					userOther.setAccountNumber(accountOther);
					
					//查询目标账户余额
					User rtnUser = null;
					try {
						
						rtnUser = userService.findByBalance(userOther);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					if(rtnUser == null) {
						continue;
					}
					
					//获取当前登录账户信息
					List<String> listOther = new ArrayList<>();
					listOther.add(rtnUser.toString());
					
					for (int i = 0; i < listOther.size(); i++) {
						
						String str = listOther.get(i);
						
						//分割用户信息
						String[] aArray = str.split("\\|");
						
						//余额
						String banlance = aArray[6];
						String[] banlances = banlance.split("=");
						userOther.setBalance(Double.parseDouble(banlances[1]));
						
					}
					
					//判断转账金额是否符号条件
					double money;
					while(true){
						
						System.out.println("您的余额为:"+userOwn.getBalance());
						System.out.println("请输入转账金额(100元的倍数，不得为0,不能大于余额):");
						double selectMoney = scanner.nextDouble();

						//100整数，且不为0  转账金额不得大于余额
						if((selectMoney%100 == 0 && selectMoney>0) && (selectMoney<userOwn.getBalance())){
							money = selectMoney;
							break;
							
						} else{
							System.out.println("请按照提示重新输入!");
							continue;
						}
						
					}
					
					//转账后的余额
					userOwn.setBalance(userOwn.getBalance()-money);
					userOther.setBalance(userOther.getBalance()+money);
					
					//原账户交易记录
					trade.setSerialNumber(1);
					trade.setAmount(-money);
					trade.setBalanceAfter(userOwn.getBalance());
					trade.setAccountOwn(userOwn.getAccountNumber());
					trade.setAccountOther(userOther.getAccountNumber());
					trade.setType(3);
					trade.setTime(DateUtil.date());
					
					//对方账户交易记录
					Trade tradeOther = new Trade();
					tradeOther.setSerialNumber(1);
					tradeOther.setAmount(money);
					tradeOther.setBalanceAfter(userOther.getBalance());
					tradeOther.setAccountOwn(userOwn.getAccountNumber());
					tradeOther.setAccountOther(userOther.getAccountNumber());
					tradeOther.setType(4);
					tradeOther.setTime(DateUtil.date());
					
					//修改源账户余额,修改目标账户余额,添加源账户转出交易记录,添加目标账户转让记录
					try {
						
						userService.updateByBalance(userOwn);
						userService.updateByBalance(userOther);
						userService.tradeInfo(trade);
						userService.tradeInfo(tradeOther);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					System.out.println("转账成功，现在的余额为:"+userOwn.getBalance());
					continue;
					
					//4.个人信息查看
				} else if("4".equals(select)) {
					
					//查询个人信息
					System.out.println("个人开户信息:");
					
					String str = null;
					try {
						
						str = userService.findByAccount(userOwn);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					System.out.println(str);
					
					//查询个人账务
					System.out.println("个人账务查询:");
					
					List<Object> rtnList = null;
					try {
						
						rtnList = userService.queryRecordByAccount(userOwn.getAccountNumber());
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					//遍历获取个人账务信息
					for (int i = 0; i < rtnList.size(); i++) {
						
						System.out.println(rtnList.get(i));
						
					}
					
					continue;
					
					//5.返回上一级
				} else if("5".equals(select)) {
					
					result  = "systemController";
					break;
					
					//6.退卡
				} else if("6".equals(select)) {
					
					result = "exit";
					break;
					
				} else {
					
					System.out.println("请按照提示进行操作!");
					continue;
				}
			}
		}
				
		return result;
				
	}

}
