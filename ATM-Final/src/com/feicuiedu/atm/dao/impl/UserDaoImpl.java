package com.feicuiedu.atm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.feicuiedu.atm.dao.UserDao;
import com.feicuiedu.atm.entity.Trade;
import com.feicuiedu.atm.entity.User;
import com.feicuiedu.atm.util.JDBCUtil;

/**
 * 用户信息数据库操作
 * @author 王东旭
 */
public class UserDaoImpl implements UserDao {
	
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	private User user;
	private Trade trade;

	/**
	 * 开户
	 */
	@Override
	public void openAccount(User user) throws Exception {

		conn = JDBCUtil.getConnection();
		
		//账号  密码  姓名  性别  身份证号  学历  余额  地址
		
//		String sql = "insert into users(accountNumber,password,userName,gender,idNumber,birthday,balance,address,status,remarks) 
//		                          values(?,?,?,?,?,?,?,?,?,?)";
		String sql = "insert into users(serialNumber,userName,gender,idNumber,birthday,balance,address,status,remarks) values(?,?,?,?,?,?,?,?,?)";
		
		// 创建语句执行
		ps = conn.prepareStatement(sql);
		
		//设置参数
		ps.setInt(1, user.getSerialNumber());
		ps.setString(2, user.getUserName());
		ps.setInt(3, user.getGender());
		ps.setString(4, user.getIdNumber());
		ps.setDate(5, new java.sql.Date(user.getBirthday().getTime()));
		ps.setString(6, user.getBalance().toString());
		ps.setString(7, user.getAddress());
		ps.setInt(8, user.getStatus());
		ps.setString(9, user.getRemarks());
		
//		ps.setString(1, "'"+user.getUserName()+"'");
//		ps.setInt(2, user.getGender());
//		ps.setString(3, "'"+user.getIdNumber()+"'");
//		ps.setDate(4, new java.sql.Date(user.getBirthday().getTime()));
//		ps.setString(5, user.getBalance().toString());
//		ps.setString(6, "'"+user.getAddress()+"'");
//		ps.setInt(7, user.getStatus());
//		ps.setString(8, "'"+user.getRemarks()+"'");
		
		//执行更新
		int i = ps.executeUpdate();
		
		if(i==1) {
            System.out.println("开户成功！");
        }else {
            System.out.println("开户失败！");
        }
		
		JDBCUtil.closePreparedStatement(ps);
		JDBCUtil.closeConnection(conn);
	}
	
	/**
	 * 通用查询用户信息
	 */
	@Override
	public User findByAll(String key,String value,int status) throws Exception {
		
		conn = JDBCUtil.getConnection();
		
		String sql = "select serialNumber,accountNumber,password,userName,"
				+ "gender,idNumber,birthday,balance,address,remarks,"
				+ "status from users where " + key + " = ? and status = ?";
//		String sql = "select * from users where " + key + " = ? and status = ?";
		
		// 预创建语句执行
		ps = conn.prepareStatement(sql);
		
		ps.setString(1, value);
		ps.setInt(2, status);
		
		rs = ps.executeQuery();
		
		while(rs.next()) {
			
			user = new User();
			user.setSerialNumber(rs.getInt("serialNumber"));
			user.setAccountNumber(rs.getString("accountNumber"));
			user.setPassword(rs.getString("password"));
			user.setUserName(rs.getString("userName"));
			user.setGender(rs.getInt("gender"));
			user.setIdNumber(rs.getString("idNumber"));
			user.setBirthday(rs.getDate("birthday"));
			user.setBalance(rs.getDouble("balance"));
			user.setAddress(rs.getString("address"));
			user.setRemarks(rs.getString("remarks"));
			user.setStatus(rs.getInt("status"));
			
		}
		
		JDBCUtil.closeResultSet(rs);
		JDBCUtil.closePreparedStatement(ps);
		JDBCUtil.closeConnection(conn);
		
		return user;
		
	}

	/**
	 * 将账号和密码添加进去
	 */
	@Override
	public void updateByIdNumber(String accountNumber, String password,String idNumber) throws Exception {

		conn = JDBCUtil.getConnection();
		
		String sql = "update users set accountNumber= ? ,password= ? where idNumber = ? and status = ?;";
		
		// 预创建语句执行
		ps = conn.prepareStatement(sql);
		
		ps.setString(1, accountNumber);
		ps.setString(2, password);
		ps.setString(3, idNumber);
		ps.setInt(4, 1);
		
		//执行更新
		int i = ps.executeUpdate();
		
		if(i==1) {
            System.out.println("更新成功！");
            System.out.println("账号="+accountNumber+"密码="+password);
        }else {
            System.out.println("更新失败！");
        }
		
		JDBCUtil.closePreparedStatement(ps);
		JDBCUtil.closeConnection(conn);
		
	}

	/**
	 * 通用改变状态(销户,解除锁定)
	 */
	@Override
	public void modifyByAccount(int status,String accountNumber) throws Exception {

		conn = JDBCUtil.getConnection();
		
		String sql = "update users set status = ? where accountNumber = ?;";
		
		// 预创建语句执行
		ps = conn.prepareStatement(sql);
		
		ps.setInt(1, status);
		ps.setString(2, accountNumber);
		
		//执行更新
		int i = ps.executeUpdate();
		
		if(i==1) {
            System.out.println("成功！");
        }else {
            System.out.println("失败！");
        }
		
		JDBCUtil.closePreparedStatement(ps);
		JDBCUtil.closeConnection(conn);
		
	}

	/**
	 * 根据status查询用户信息
	 */
	@Override
	public List<User> queryByStatus(int status) throws Exception {
		
		conn = JDBCUtil.getConnection();
		
//		String sql = "select accountNumber,password,userName,"
//				+ "gender,idNumber,education,"
//				+ "balance,address from users"
//				+ "where accountNumber = "+user.getAccountNumber()+";";
		String sql = "select serialNumber,accountNumber,password,userName,gender,idNumber,balance,status from users where status = ?;";
		
		// 预创建语句执行
		ps = conn.prepareStatement(sql);
		
		ps.setInt(1, status);
		
		rs = ps.executeQuery();
		
		List<User> list = new ArrayList<>();
		
		while(rs.next()) {
			
			user = new User();
			user.setSerialNumber(rs.getInt("serialNumber"));
			user.setAccountNumber(rs.getString("accountNumber"));
			user.setPassword(rs.getString("password"));
			user.setUserName(rs.getString("userName"));
			user.setGender(rs.getInt("gender"));
			user.setIdNumber(rs.getString("idNumber"));
			user.setBalance(rs.getDouble("balance"));
			user.setStatus(rs.getInt("status"));
			list.add(user);
		}
		
		JDBCUtil.closeResultSet(rs);
		JDBCUtil.closePreparedStatement(ps);
		JDBCUtil.closeConnection(conn);
		
		return list;
		
	}

	/**
	 * 修改密码
	 */
	@Override
	public void updateByAccount(String accountNumber,String password) throws Exception {

		conn = JDBCUtil.getConnection();
		
		String sql = "update users set password = ? where accountNumber = ?;";
		
		// 预创建语句执行
		ps = conn.prepareStatement(sql);
		
		ps.setString(1, password);
		ps.setString(2, accountNumber);
		
		//执行更新
		int i = ps.executeUpdate();
		
		if(i==1) {
            System.out.println("密码重置成功！");
        }else {
            System.out.println("密码重置失败！");
        }
		
		JDBCUtil.closePreparedStatement(ps);
		JDBCUtil.closeConnection(conn);
		
	}

	/**
	 * 根据身份证号或者账号查询
	 */
	@Override
	public User findByNumber(String number) throws Exception {
		
		conn = JDBCUtil.getConnection();
		
		String sql = "select serialNumber,accountNumber,password,userName,"
				+ "gender,idNumber,birthday,balance,address,"
				+ "remarks,status from users "
				+ "where (accountNumber = ? or idNumber = ?) and status = ?";
//		String sql = "select * from users where accountNumber = ? or idNumber = ? and status = ?";
		
		// 预创建语句执行
		ps = conn.prepareStatement(sql);
		
		ps.setString(1, number);
		ps.setString(2, number);
		ps.setInt(3, 1);
		
		rs = ps.executeQuery();
		
		while(rs.next()) {
			
			user = new User();
			user.setSerialNumber(rs.getInt("serialNumber"));
			user.setAccountNumber(rs.getString("accountNumber"));
			user.setPassword(rs.getString("password"));
			user.setUserName(rs.getString("userName"));
			user.setGender(rs.getInt("gender"));
			user.setIdNumber(rs.getString("idNumber"));
			user.setBirthday(rs.getDate("birthday"));
			user.setBalance(rs.getDouble("balance"));
			user.setAddress(rs.getString("address"));
			user.setRemarks(rs.getString("remarks"));
			user.setStatus(rs.getInt("status"));
			
		}
		
		JDBCUtil.closeResultSet(rs);
		JDBCUtil.closePreparedStatement(ps);
		JDBCUtil.closeConnection(conn);
		
		return user;
		
	}

	/**
	 * 根据账号修改余额
	 */
	@Override
	public void updateByBalance(User userOwn) throws Exception {
		
		conn = JDBCUtil.getConnection();
		
		String sql = "update users set balance = ? where accountNumber = ?;";
		
		// 预创建语句执行
		ps = conn.prepareStatement(sql);
		
		ps.setDouble(1, userOwn.getBalance());
		ps.setString(2, userOwn.getAccountNumber());
		
		//执行更新
		int i = ps.executeUpdate();
		
		if(i==1) {
            System.out.println("余额修改成功！");
        }else {
            System.out.println("余额修改失败！");
        }
		
		JDBCUtil.closePreparedStatement(ps);
		JDBCUtil.closeConnection(conn);
		
	}

	/**
	 * 添加交易记录
	 */
	@Override
	public void tradeInfo(Trade trade) throws Exception {

		conn = JDBCUtil.getConnection();
		
		String sql = "insert into trade(serialNumber,accountOwn,accountOther,type,time,amount,balanceAfter) values(?,?,?,?,?,?,?)";
		
		// 创建语句执行
		ps = conn.prepareStatement(sql);
		
		//设置参数
		ps.setInt(1, trade.getSerialNumber());
		ps.setString(2, trade.getAccountOwn());
		ps.setString(3, trade.getAccountOther());
		ps.setInt(4, trade.getType());
		ps.setTimestamp(5,(Timestamp) trade.getTime());
		ps.setDouble(6, trade.getAmount());
		ps.setDouble(7, trade.getBalanceAfter());
		
		//执行更新
		int i = ps.executeUpdate();
		
		if(i==1) {
            System.out.println("交易记录添加成功！");
        }else {
            System.out.println("交易记录添加失败！");
        }
		
		JDBCUtil.closePreparedStatement(ps);
		JDBCUtil.closeConnection(conn);
		
	}

	/**
	 * 根据账号查询余额
	 */
	@Override
	public User findByBalance(User userOther) throws Exception {
		
		conn = JDBCUtil.getConnection();
		
		String sql = "select serialNumber,accountNumber,password,userName,"
				+ "gender,idNumber,birthday,balance,address,"
				+ "remarks,status from users "
				+ "where accountNumber = ?  and status = ?";
		
		// 预创建语句执行
		ps = conn.prepareStatement(sql);
		
		ps.setString(1, userOther.getAccountNumber());
		ps.setInt(2, 1);
		
		rs = ps.executeQuery();
		
		while(rs.next()) {
			
			user = new User();
			user.setSerialNumber(rs.getInt("serialNumber"));
			user.setAccountNumber(rs.getString("accountNumber"));
			user.setPassword(rs.getString("password"));
			user.setUserName(rs.getString("userName"));
			user.setGender(rs.getInt("gender"));
			user.setIdNumber(rs.getString("idNumber"));
			user.setBirthday(rs.getDate("birthday"));
			user.setBalance(rs.getDouble("balance"));
			user.setAddress(rs.getString("address"));
			user.setRemarks(rs.getString("remarks"));
			user.setStatus(rs.getInt("status"));
			
		}
		
		JDBCUtil.closeResultSet(rs);
		JDBCUtil.closePreparedStatement(ps);
		JDBCUtil.closeConnection(conn);
		
		return user;
		
	}

	/**
	 * 根据账号查询用户信息
	 */
	@Override
	public String findByAccount(User user) throws Exception {
		
		conn = JDBCUtil.getConnection();
		
		String sql = "select serialNumber,accountNumber,password,userName,"
				+ "gender,idNumber,birthday,balance,address,"
				+ "remarks,status from users "
				+ "where accountNumber = ?  and status = ?";
		
		// 预创建语句执行
		ps = conn.prepareStatement(sql);
		
		ps.setString(1, user.getAccountNumber());
		ps.setInt(2, 1);
		
		rs = ps.executeQuery();
		
		String str = null;
		
		while(rs.next()) {
			
			user = new User();
			user.setSerialNumber(rs.getInt("serialNumber"));
			user.setAccountNumber(rs.getString("accountNumber"));
			user.setPassword(rs.getString("password"));
			user.setUserName(rs.getString("userName"));
			user.setGender(rs.getInt("gender"));
			user.setIdNumber(rs.getString("idNumber"));
			user.setBirthday(rs.getDate("birthday"));
			user.setBalance(rs.getDouble("balance"));
			user.setAddress(rs.getString("address"));
			user.setRemarks(rs.getString("remarks"));
			user.setStatus(rs.getInt("status"));
			
			str = "主键=" + user.getSerialNumber() + ",账号=" + user.getAccountNumber() + ",密码=" + user.getPassword() + ",姓名=" + user.getUserName() + ",性别="
				+ user.getGender() + ",身份证号=" + user.getIdNumber() + ",出生日期=" + user.getBirthday() + ",余额=" + user.getBalance() + ",地址="
				+ user.getAddress() + ",状态=" + user.getStatus() + ",备注=" + user.getRemarks();
		}
		
		JDBCUtil.closeResultSet(rs);
		JDBCUtil.closePreparedStatement(ps);
		JDBCUtil.closeConnection(conn);
		
		return str;
		
	}

	/**
	 * 根据账号查询用户交易记录
	 */
	@Override
	public List<Object> queryRecordByAccount(String accountNumber) throws Exception {
		
		conn = JDBCUtil.getConnection();
		
		String sql = "select serialNumber,accountOwn,accountOther,type,time,amount,balanceAfter "
				+ "from trade where accountOwn = ? or accountOther = ?";
		
		// 预创建语句执行
		ps = conn.prepareStatement(sql);

		ps.setString(1, accountNumber);
		ps.setString(2, accountNumber);
		
		rs = ps.executeQuery();
		
		List<Object> list = new ArrayList<>();
		
		while(rs.next()) {
			
			trade = new Trade();
			trade.setSerialNumber(rs.getInt("serialNumber"));
			trade.setAccountOwn(rs.getString("accountOwn"));
			trade.setAccountOther(rs.getString("accountOther"));
			trade.setType(rs.getInt("type"));
			trade.setTime(rs.getDate("time"));
			trade.setAmount(rs.getDouble("amount"));
			trade.setBalanceAfter(rs.getDouble("balanceAfter"));
			list.add(trade);
		}

		JDBCUtil.closeResultSet(rs);
		JDBCUtil.closePreparedStatement(ps);
		JDBCUtil.closeConnection(conn);
		
		return list;
		
	}

	/**
	 * 根据账户或者身份证号,锁定用户
	 */
	@Override
	public void unlockByNumber(int status, String number) throws Exception {
		
		conn = JDBCUtil.getConnection();
		
		String sql = "update users set status = ? where accountNumber = ? or idNumber = ?;";
		
		// 预创建语句执行
		ps = conn.prepareStatement(sql);
		
		ps.setInt(1, status);
		ps.setString(2, number);
		ps.setString(3, number);
		
		//执行更新
		int i = ps.executeUpdate();
		
		if(i==1) {
            System.out.println("锁定成功！");
        }else {
            System.out.println("锁定失败！");
        }
		
		JDBCUtil.closePreparedStatement(ps);
		JDBCUtil.closeConnection(conn);
		
	}

	/**
	 * 查找主键最大值
	 */
	@Override
	public Integer maxValue(String table) throws Exception {
		
		conn = JDBCUtil.getConnection();
		
		String sql = "select max(serialNumber) from "+table;
		
		// 预创建语句执行
		ps = conn.prepareStatement(sql);
		
		rs = ps.executeQuery();
		
		while(rs.next()) {
			
			user = new User();
			user.setSerialNumber(rs.getInt("serialNumber"));
			System.out.println(user);
		}
		
		return null;
		
	}
	
}
