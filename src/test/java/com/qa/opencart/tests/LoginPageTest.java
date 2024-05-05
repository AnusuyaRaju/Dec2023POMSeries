package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

//@Listeners(ExtentReportListener.class)  if u want extent report for specific test we can use this but generally we should avoid this need to run via testng.regression.xml
@Epic("Epic 100: Design open cart login page")
@Story("US 101: Design login page features for open cart application")
@Feature("Feature 201: Adding login features")
public class LoginPageTest extends BaseTest {
	
	//Test shouldn't have any selenium code
	//Allure report: we can add @Description n @Severity in LoginPageTest also we can add @Step() in page class n eleUtil 
	
	
    @Description("Checking login page title....")
    @Severity(SeverityLevel.MINOR)
	@Test(priority = 1)
	public void loginPageTitleTest() {
		String actTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actTitle, AppConstants.LOGIN_PAGE_TITLE,AppError.TITLE_NOT_FOUND);//bcus inside AppConstants all r static in nature
	}
    
    @Description("Checking login page URL....")
    @Severity(SeverityLevel.MINOR)
	@Test(priority = 2)
	public void loginPageURLTest(){
		String actURL = loginPage.getLoginPageURL();
		Assert.assertTrue(actURL.contains(AppConstants.LOGIN_PAGE_URL_FRACTION),AppError.URL_NOT_FOUND);
	}

    @Description("Checking forgot pwd link is on login page....")
    @Severity(SeverityLevel.CRITICAL)
	@Test(priority = 3)
	public void forgotPwdLinkExistTest() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExist());

	}
	
    @Description("Checking user is able to login....")
    @Severity(SeverityLevel.BLOCKER)
	@Test(priority = 4)
	public void loginTest() {
		accPage = loginPage.doLogin("dece2023@opencart.com", "Selenium@12345");
		Assert.assertEquals(accPage.getAccPageTitle(), AppConstants.ACCOUNTS_PAGE_TITLE);
	}

}
