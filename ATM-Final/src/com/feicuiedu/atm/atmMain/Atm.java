package com.feicuiedu.atm.atmMain;

import java.util.Scanner;

import com.feicuiedu.atm.controller.BaseController;
import com.feicuiedu.atm.util.PropUtil;

/**
 * 程序运行
 * @author 王东旭
 */
public class Atm {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		String result = "systemController";
		
		//根据result的值运行相应的Controller,如果为exit,退出程序!
		while(true){
			
			BaseController baseController = (BaseController) PropUtil.getObjectFromProp(result);
			result = baseController.execute(scanner);
			
			if("exit".equals(result)){
				break;
			}
		}
		
		scanner.close();
		System.exit(0);
		
	}
	
}
