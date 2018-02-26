package com.feicuiedu.atm.view;

/**
 * 管理员视图界面
 * @author 王东旭
 */
public class AdminView {

	/**
	 * 管理员功能选择界面
	 */
	public void showView() {
		
		System.out.println("尊敬的管理员您好,请选择要进行的操作!");
		System.out.println("1.开户 2.销户 3.检索普通用户 4.检索已销户用户 ");
		System.out.println("5.检索已锁定用户 6.解除锁定账户 7.重置密码 8.返回上一级 9.退卡");
		
	}
	
}
