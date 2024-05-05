package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class AccountsPage {
	
	static WebDriver driver;
	private ElementUtil eleUtil;
	
	//1.Private By Locators
	private By logoutLink = By.linkText("My Account");
	private By myAccountLink = By.linkText("Logout");
	private By headers = By.cssSelector("div#content h2");
	private By search = By.name("search");
	private By searchIcon = By.cssSelector("div#search button");
	
	
	//2.Public Page Class Constructor
	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
		
	}
	
	public String getAccPageTitle() {
		String title = eleUtil.waitForTitleIs(AppConstants.ACCOUNTS_PAGE_TITLE, 5);
		System.out.println("Acc page title : " + title);
		return title;
		
	}
	
	public String getAccPageURL() {
		String url = eleUtil.waitForURLContains(AppConstants.ACCOUNTS_PAGE_URL_FRACTION, 5);
		System.out.println("Acc page url : " + url);
		return url;	
	}
	
	public boolean isLogoutLinkExist() {
		return eleUtil.waitForElementVisible(logoutLink, 10).isDisplayed();
	}
	
	public boolean MyAccountLinkExist() {
		return eleUtil.waitForElementVisible(myAccountLink, 10).isDisplayed();
	}
	
	public List<String> getAccountsPageHeadersList() {
		List<WebElement> headersEleList = eleUtil.getElements(headers);
		List<String> headerList = new ArrayList<String>();
		for(WebElement e : headersEleList) {
			String header = e.getText();
			headerList.add(header);
		}
		return headerList;
	}
	
	public SearchResultsPage doSearch(String searchKey) {
		System.out.println("searching for : " + searchKey);
		eleUtil.doSendKeys(search, searchKey);
		eleUtil.doClick(searchIcon);
		return new SearchResultsPage(driver);
	}

}
