package com.feicuiedu.atm.entity;

import java.util.Date;

/**
 * 交易属性
 * @author 王东旭
 */
public class Trade {

	private Integer serialNumber;//主键
	private String accountOwn;//源账户
	private String accountOther;//目标账户
	private Integer type;//账务类型  1:存款 2:取款 3: 转账-支出  4:转账-收入
	private Date time;//账务时间   yyyy-MM-dd hh:mm:ss
	private Double amount;//交易金额
	private Double balanceAfter;//交易后账户金额
	
	public Trade() {
		super();
	}
	
	public Trade(Integer serialNumber,String accountOwn, String accountOther, Integer type, Date time, Double amount, Double balanceAfter) {
		super();
		this.serialNumber = serialNumber;
		this.accountOwn = accountOwn;
		this.accountOther = accountOther;
		this.type = type;
		this.time = time;
		this.amount = amount;
		this.balanceAfter = balanceAfter;
	}

	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getAccountOwn() {
		return accountOwn;
	}

	public void setAccountOwn(String accountOwn) {
		this.accountOwn = accountOwn;
	}

	public String getAccountOther() {
		return accountOther;
	}

	public void setAccountOther(String accountOther) {
		this.accountOther = accountOther;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getBalanceAfter() {
		return balanceAfter;
	}

	public void setBalanceAfter(Double balanceAfter) {
		this.balanceAfter = balanceAfter;
	}

	@Override
	public String toString() {
		
		String showType = null;
		if(type == 1){
			showType = "存款";
		}
		if(type == 2){
			showType = "取款";
		}
		if(type == 3){
			showType = "转账-支出";
		}
		if(type == 4){
			showType = "转账-收入";
		}
		
		return "账务流水号=" + serialNumber + "|源账户=" + accountOwn + "|目标账户=" + accountOther + "|账务类型=" + showType + "|账务时间="
				+ time + "|交易金额=" + amount + "|交易后账户金额=" + balanceAfter;
	}
	
}
