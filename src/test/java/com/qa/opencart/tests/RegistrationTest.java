package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.CSVUtil;
import com.qa.opencart.utils.ExcelUtil;
import com.qa.opencart.utils.StringUtils;

import io.qameta.allure.Step;

public class RegistrationTest extends BaseTest{
	
	@BeforeClass
	public void regSetup() {
		registrationPage = loginPage.navigateToRegisterPage();
	}
	
	@DataProvider
	public Object[][] getUserRegTestData() {
		return new Object [] [] {
			{"kani","khan","1209938475","Kani@123","yes"},
			{"lava","lev","7685493021","Lav@135","no"},
			{"joe","jass","0987987098","Jao@124","yes"}
			//if we pass StringUtils.getRandomEmailId() in data provider it will give same email id's that's y added under @Test
			//once execution done we can check in database where we can see all teh entries if we want to delet use delt query
			//select * from 'table name' where email like '%testautomation%'; - check entries
			//delete * from 'table name' where email like '%testautomation%';  - delete
			
		};
	}
	
	
	//IQ: return type of data provider is 2 D array Object[][]
	@DataProvider
	public Object[][] getUserRegTestDataFromExcel() {
		return ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);
		
	}
	
	@DataProvider
	public Object[][] getUserRegTestDataFromCSV() {
		return CSVUtil.csvData(AppConstants.REGISTER_SHEET_NAME);
		
	}
	
	@Step("checking user registration...")
	@Test(dataProvider = "getUserRegTestData")
	public void userRegTest(String firstName, String lastName, String telephone, String password, String subscribe) { // sequence should be same as per excel sheet
		Assert.assertTrue(registrationPage.userRegister(firstName, lastName, StringUtils.getRandomEmailId(), telephone, password, subscribe));
	}
	
	
	
	//IQ: Disadvantage of excel sheet..?
		//1. We've to be depend on 3rd party api apache POI api big problem
		//2. excel sheet corrupted don't have any license 
		//3. anyone can open excel sheet somebody can delete it
		
		//Advantage of excel sheet:
		//if we have huge data then we can maintain excel sheet if we have less data 5 rows 3 col then we use @data provider
		
		//Always save excel in .xlsx format
		//excel column names can be anything no need to match with test or page files
	    
	    //in excel telephone cell if numbers saved with any space in front then o/p will be decimal values hence jus add single quite infront of number clk enter then run it will work fine
	
	    //we have 3 ways to store the data .csv,.xlsx n via data provider
	    //.csv is light weight compared to excel.xlsx also not corrupted 
	    //in @Test we can pass .csv, .xlsx method name or dataprovide name also
}