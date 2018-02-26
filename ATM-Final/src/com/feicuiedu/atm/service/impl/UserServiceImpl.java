package com.feicuiedu.atm.service.impl;

import java.util.List;

import com.feicuiedu.atm.dao.UserDao;
import com.feicuiedu.atm.entity.Trade;
import com.feicuiedu.atm.entity.User;
import com.feicuiedu.atm.service.UserService;
import com.feicuiedu.atm.util.PropUtil;

/**
 * 管理员业务操作
 * @author 王东旭
 */
public class UserServiceImpl implements UserService {
	
	private UserDao userDao;
	
	public UserServiceImpl() {
		super();
		userDao = (UserDao) PropUtil.getObjectFromProp("userDao");
	}

	/** 
	 * 开户
	 */
	@Override
	public void openAccount(User user) throws Exception {

		//根据身份证号判断是否存在
		User rtnUser = findByAll("idNumber",user.getIdNumber(),1);
		
		if (rtnUser == null) {
			
			//开户
			try {
				
				userDao.openAccount(user);
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("保存失败");
			}
			
		} else {
//			throw new Exception("已经存在，不能够添加");
			System.out.println("已经存在，不能够添加");
		}
		
	}
	
	/**
	 * 根据身份证号修改账号和密码
	 */
	@Override
	public void updateByIdNumber(String accountNumber, String password, String idNumber) throws Exception {

		userDao.updateByIdNumber(accountNumber, password,idNumber);
		
	}

	/**
	 * 根据输入的内容判断是否存在
	 */
	public User findByAll(String key,String value,int status) throws Exception {

		User rtnUser = null;
		
		try {
			
			rtnUser = userDao.findByAll(key,value,status);
			
		} catch (Exception e) {
			e.printStackTrace();
//			throw new Exception("查询失败");
			System.out.println("查询失败");
		}

		return rtnUser;
		
	}

	/**
	 * 销户(根据账号修改用户状态)
	 */
	@Override
	public void salesByAccount(String accountNumber) throws Exception {

		//判断账户是否存在
		User rtnUser = findByAll("accountNumber",accountNumber,1);
		
		if (rtnUser != null) {
			
			//销户
			try {
				
				userDao.modifyByAccount(2,accountNumber);
				
			} catch (Exception e) {
				e.printStackTrace();
//				throw new Exception("销户失败");
				System.out.println("销户失败");
			}
			
		} else {
//			throw new Exception("账号不存在,无法销户");
			System.out.println("账号不存在,无法销户");
		}
		
	}

	/**
	 * 根据status查询用户信息
	 */
	@Override
	public List<User> queryByStatus(int status) throws Exception {
		
		List<User> list = userDao.queryByStatus(status);
		
		if(list.isEmpty()){
			System.out.println("此类用户信息不存在");
		}
		
		return list;
		
	}

	/**
	 * 解除账户锁定
	 */
	@Override
	public void unlockByStatus(String accountNumber) throws Exception {

		//判断账户是否存在
		User rtnUser = findByAll("accountNumber",accountNumber,3);

		if (rtnUser != null) {
			
			//解锁
			try {
				
				userDao.modifyByAccount(1,accountNumber);
				
			} catch (Exception e) {
				e.printStackTrace();
//				throw new Exception("解除锁定失败");
				System.out.println("解除锁定失败");
			}
			
		} else {
//			throw new Exception("账号不存在,无法解除锁定");
			System.out.println("账号不存在,无法解除锁定");
		}
		
	}

	/**
	 * 修改密码
	 */
	@Override
	public void updateByAccount(String accountNumber,String password) throws Exception {

		//判断账户是否存在
		User rtnUser = findByAll("accountNumber",accountNumber,1);
		
		if (rtnUser != null) {
			
			//修改密码
			try {
				
				userDao.updateByAccount(accountNumber,password);
				
			} catch (Exception e) {
				e.printStackTrace();
//				throw new Exception("修改密码失败");
				System.out.println("修改密码失败");
			}
			
		} else {
//			throw new Exception("账号不存在");
			System.out.println("账号不存在");
		}

	}

	/**
	 * 根据账号修改余额
	 */
	@Override
	public void updateByBalance(User userOwn) throws Exception {
		
		//判断账户是否存在
		User rtnUser = findByAll("accountNumber",userOwn.getAccountNumber(),1);
		
		if (rtnUser != null) {
			
			//修改余额
			try {
				
				userDao.updateByBalance(userOwn);
				
			} catch (Exception e) {
				e.printStackTrace();
//				throw new Exception("修改余额失败");
				System.out.println("修改余额失败");
			}
			
		} else {
//			throw new Exception("账号不存在");
			System.out.println("账号不存在");
		}
		
	}

	/**
	 * 添加交易记录
	 */
	@Override
	public void tradeInfo(Trade trade) throws Exception {

		userDao.tradeInfo(trade);
		
	}

	/**
	 * 根据账号查询余额
	 */
	@Override
	public User findByBalance(User userOther) throws Exception {
		
		//判断账户是否存在
		User rtnUser = userDao.findByBalance(userOther);
		
		if (rtnUser == null) {
			
			System.out.println("账号不存在");
			
		}

		return rtnUser;
		
	}

	/**
	 * 根据账号查询用户信息
	 */
	@Override
	public String findByAccount(User user) throws Exception {
		
		//判断账户是否存在
		String rtnStr = userDao.findByAccount(user);
		
		if (rtnStr == null) {
			
			System.out.println("账号不存在");
			
		}

		return rtnStr;
	}

	/**
	 * 根据账号查询用户交易记录
	 */
	@Override
	public List<Object> queryRecordByAccount(String accountNumber) throws Exception {
		
		//判断账户是否存在
		List<Object> rtnList = userDao.queryRecordByAccount(accountNumber);
		
		if (rtnList == null || rtnList.isEmpty()) {
			
			System.out.println("没有交易信息");
			
		}

		return rtnList;
				
	}

	/**
	 * 根据身份证号或者账号查询
	 */
	@Override
	public User findByNumber(String number) throws Exception {
		
		//判断账户是否存在
		User rtnUser = userDao.findByNumber(number);
		
		if (rtnUser != null) {
			
			System.out.println("已存在");
			
		}

		return rtnUser;
	}

	/**
	 * 查找主键最大值
	 */
	@Override
	public Integer maxValue(String table) throws Exception {

		int maxValue = userDao.maxValue(table);
				
		return maxValue;
	}

}
