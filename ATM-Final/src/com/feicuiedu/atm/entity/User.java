package com.feicuiedu.atm.entity;

import java.util.Date;

/**
 * 用户属性
 * @author 王东旭
 */
public class User {

	private Integer serialNumber;//主键
	private String accountNumber;//账户
	private String password;//密码
	private String userName;//姓名
	private Integer gender;//性别 1.男 2.女
	private String idNumber;//身份证号
	private Date birthday;//出生日期
	private Double balance;//余额
	private String address;//地址 1.正常 2.已销户 3.已锁定
	private Integer status;//状态 
	private String remarks;//备注
	
	public User() {
		super();
	}
	
	public User(Integer serialNumber,String accountNumber,String userName, Integer gender, String password,
			    String idNumber,Double balance,Integer status) {
		super();
		this.serialNumber = serialNumber;
		this.accountNumber = accountNumber;
		this.password = password;
		this.userName = userName;
		this.gender = gender;
		this.idNumber = idNumber;
		this.balance = balance;
		this.status = status;
	}
	
	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		
		String showGender = null;
		if(gender == 1){
			showGender = "男";
		}
		if(gender == 2){
			showGender = "女";
		}
		
		String showStatus = null;
		if(status == 1){
			showStatus = "正常";
		}
		if(status == 2){
			showStatus = "已销户";
		}
		if(status == 3){
			showStatus = "已锁定";
		}
		
		return "主键=" + serialNumber + "|账号=" + accountNumber + "|密码=" + password +"|姓名=" + userName + "|性别="
				+ showGender + "|身份证号=" + idNumber + "|余额=" + balance + "|状态=" + showStatus;
	}
	
//	@Override
//	public String toString() {
//		return "账号=" + accountNumber + "|姓名=" + userName + "|性别="
//				+ gender + "|身份证号=" + idNumber + "|余额=" + balance + "|状态=" + status;
//	}

//	public String rtnGender(Integer gender) {
//		User user = new User();
//		rtnGender = null;
//		
//		if(gender==1) {		
//			rtnGender = "男";
//			user.setRtnGender(rtnGender);
//			return rtnGender;
//		} else {	
//			rtnGender = "女";
//			user.setRtnGender(rtnGender);
//			return rtnGender;
//		}
//		
//	}
//	
//	public String getRtnGender() {
//		return rtnGender;
//	}
//
//	public void setRtnGender(String rtnGender) {
//		this.rtnGender = rtnGender;
//	}

//	public User(Integer serialNumber,String accountNumber,String userName, String rtnGender, 
//		    String idNumber,Double balance,Integer status) {
//	super();
//	this.serialNumber = serialNumber;
//	this.accountNumber = accountNumber;
//	this.userName = userName;
//	this.rtnGender = rtnGender;
//	this.idNumber = idNumber;
//	this.balance = balance;
//	this.status = status;
//	}
	
}
