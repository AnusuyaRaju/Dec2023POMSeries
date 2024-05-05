package com.qa.opencart.utils;

public class StringUtils {
	
	public static String getRandomEmailId() {
		
		//System.currentTimeMillis() always give the unique number
		String emailId = "testautomation" + System.currentTimeMillis()+ "@opencart.com";
		return emailId;
		
	}

}
