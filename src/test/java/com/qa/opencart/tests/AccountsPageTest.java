package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

public class AccountsPageTest extends BaseTest{
	
	@BeforeClass
	public void accSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Test
	public void accPageTitleTest() {
		String actTitle = accPage.getAccPageTitle();
		Assert.assertEquals(actTitle, AppConstants.ACCOUNTS_PAGE_TITLE);
	}
	
	@Test
	public void accPageURLTest() {
		String actUrl = accPage.getAccPageURL();
		Assert.assertTrue(actUrl.contains(AppConstants.ACCOUNTS_PAGE_URL_FRACTION));
	}
	
	@Test
	public void isLogoutLinkExistTest() {
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}
	
	@Test
	public void isMyAccLinkExistTest() {
		Assert.assertTrue(accPage.MyAccountLinkExist());
	}
	
	@Test
	public void accPageHeadersTest() {
		List<String> actHeaderList = accPage.getAccountsPageHeadersList();
		System.out.println(actHeaderList);
	}
	
	@Test
	public void searchTest() {
		accPage.doSearch("macbook");
	}

}
