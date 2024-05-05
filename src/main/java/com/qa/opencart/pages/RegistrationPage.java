package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class RegistrationPage {
	
	static WebDriver driver;
	private ElementUtil eleUtil;
	
	//1.Private By Locators
	private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By email = By.id("input-email");
	private By telephone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By confirmpassword = By.id("input-confirm");
	private By subscribeYes = By.xpath("//label[@class='radio-inline']//input[@type='radio' and @value = '1']");
	private By subscribeNo = By.xpath("//label[@class='radio-inline']//input[@type='radio' and @value = '0']");
	private By agreeCheckBox = By.name("agree");
	private By continueBtn = By.xpath("//input[@type='submit' and @value='Continue']");
	
	private By sucessMessg = By.cssSelector("div#content h1");
	
	private By logoutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");
	
	//2.Public Page Class Constructor
	public RegistrationPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
		
	}
	
	@Step("user registration")
	public boolean userRegister(String firstName, String lastName, String email, String telephone, String password,
			String subscribe) {
		//y w r using this means locators n value we passed in method all r same that's y for getting class lacators used this keyword
		eleUtil.waitForElementVisible(this.firstName, 10).sendKeys(firstName);
		eleUtil.doSendKeys(this.lastName, lastName);	
		eleUtil.doSendKeys(this.email, email);
		eleUtil.doSendKeys(this.telephone, telephone);
		eleUtil.doSendKeys(this.password, password);
		eleUtil.doSendKeys(this.confirmpassword, password);
		
		if(subscribe.equalsIgnoreCase("yes")) {
			eleUtil.doClick(subscribeYes);
		}
		else {
			eleUtil.doClick(subscribeNo);
		}
		eleUtil.doClick(agreeCheckBox);
		eleUtil.doClick(continueBtn);
		
		String regSuccessMessg = eleUtil.waitForElementVisible(sucessMessg, 5).getText();
		System.out.println(regSuccessMessg);
		
		if(regSuccessMessg.equals(AppConstants.USER_REG_SUCCESS_MESSG)) {
			eleUtil.doClick(logoutLink);
			eleUtil.doClick(registerLink);
			return true;
		}
		return false;
		
	}
	
}