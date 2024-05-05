package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

public class ProductPageInfoTest extends BaseTest{
	//AAA -->
	//TestCase -- only one hard assertion or can have multiple soft assertions
	
	@BeforeClass
	public void infoPageSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@DataProvider
	public Object[][] getProductSearchData() {
		return new Object [] [] {
			{"macbook", "MacBook Pro"},
			{"imac", "iMac"},
			{"samsung", "Samsung SyncMaster 941BW"},
			{"samsung", "Samsung Galaxy Tab 10.1"}
			
		};
		
	}
	
	@Test(dataProvider = "getProductSearchData")
	public void productHeaderTest(String searchKey, String productName) {
		searchResultsPage = accPage.doSearch(searchKey);
		productInfoPage = searchResultsPage.selectProduct(productName);
		Assert.assertEquals(productInfoPage.getProductHeader(), productName); 
	}
	
	//same test is running multiple times with different set of data Test Parameterization also this is 2D array
	@DataProvider  
	public Object[][] getProductImagesData() {
		return new Object [] [] {
			{"macbook", "MacBook Pro", 4},
			{"imac", "iMac", 3},
			{"samsung", "Samsung SyncMaster 941BW", 1},
			{"samsung", "Samsung Galaxy Tab 10.1", 7}
			
		};
		
	}
	
	
	@DataProvider  
	public Object[][] getProductImagesDataFromExcel() {
		return ExcelUtil.getTestData(AppConstants.PRODUCT_SHEET_NAME);
		
	}
	
	
	@Test(dataProvider = "getProductImagesDataFromExcel")
	public void producImagesCountTest(String searchKey, String productName, String imagesCount) {//here converted imagesCount to String
		searchResultsPage = accPage.doSearch(searchKey);
		productInfoPage = searchResultsPage.selectProduct(productName);
		Assert.assertEquals(productInfoPage.getProductImagesCount(), Integer.parseInt(imagesCount)); //hence converted String to integer by using Integer.parseInt(imagesCount)
	}
	
	@Test
	public void productInfoTest() {
		searchResultsPage = accPage.doSearch("macbook");
		productInfoPage = searchResultsPage.selectProduct("MacBook Pro");
		Map<String, String> productActDetailsMap = productInfoPage.getProductDetailsMap();
		softAssert.assertEquals(productActDetailsMap.get("Brand"), "Apple11");//it will give assertions error log not an exception
		softAssert.assertEquals(productActDetailsMap.get("Product Code"), "Product 18");
		softAssert.assertEquals(productActDetailsMap.get("Availability"), "In Stock");
		softAssert.assertEquals(productActDetailsMap.get("productprice"), "$2,000.00");
		softAssert.assertEquals(productActDetailsMap.get("extaxprice"), "$2,000.00");
		softAssert.assertAll();
	}
	
	//IQ: HardAssert: Assert is a class inside the method all r in static in nature but SoftAssert inside the method all r in non static
	//in SoftAssert out of 5 validation anything is fail it will execute n validate all will give the failed status n reason as well but in 
	//normal Assert(hard assert) class if anything is fail immediately it got fail it will not execute other
    //for single assertion go with Hard Assert
	//for multiple assertions with same functionality with multiple check go with SoftAssert
	
	//HashMap won't follow any order
	//LinkedHashMap follow the order which we maintained
	//TreeMap follow alphabetical order for only keys not values 1st will print caps letter n then print small letters
	
	// We are using Hybrid Framework with Data Driven approach design pattern is Page Object Model with diff utilities with one of the testing framework is testNG
	//Data driven is approach for specific use case: below are the Data Driven approach flow
	//whenever is required maintaining data in excel or csv fetching data in the excelUtill same data will be given to Data provider, Data provider giving data to Test Annotation 
	    //Test Annotation is performing those actions on Page actions
	
	
	
}
