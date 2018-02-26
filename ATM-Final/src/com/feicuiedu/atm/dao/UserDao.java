package com.feicuiedu.atm.dao;

import java.util.List;

import com.feicuiedu.atm.entity.Trade;
import com.feicuiedu.atm.entity.User;

/**
 * Dao接口
 * @author 王东旭
 */
public interface UserDao {

	/**
	 * 添加交易记录
	 */
	public void tradeInfo(Trade trade) throws Exception;
	
	/**
	 * 开户
	 */
	public void openAccount(User user) throws Exception;
	
	/**
	 * 通用查询
	 */
	public User findByAll(String key,String value,int status) throws Exception;
	
	/**
	 * 将账号和密码添加进去
	 */
	public void updateByIdNumber(String accountNumber,String password,String idNumber) throws Exception;
	
	/**
	 * 通用改变状态(销户,解除锁定)
	 */
	public void modifyByAccount(int status,String accountNumber) throws Exception;
	
	/**
	 * 根据status查询用户信息
	 */
	public List<User> queryByStatus(int status) throws Exception;
	
	/**
	 * 修改密码
	 */
	public void updateByAccount(String accountNumber,String password) throws Exception;
	
	/**
	 * 根据账号修改余额
	 */
	public void updateByBalance(User userOwn) throws Exception;
	
	/**
	 * 根据身份证号或者账号查询
	 */
	public User findByNumber(String number) throws Exception;
	
	/**
	 * 根据账号查询余额
	 */
	public User findByBalance(User userOther) throws Exception;
	
	/**
	 * 根据账号查询用户信息
	 */
	public String findByAccount(User user) throws Exception;
	
	/**
	 * 根据账号查询用户交易记录
	 */
	public List<Object> queryRecordByAccount(String accountNumber) throws Exception;
	
	/**
	 * 根据账户或者身份证号,锁定用户
	 */
	public void unlockByNumber(int status,String number) throws Exception;

	/**
	 * 查找主键最大值
	 */
	public Integer maxValue(String table) throws Exception;
	
}
