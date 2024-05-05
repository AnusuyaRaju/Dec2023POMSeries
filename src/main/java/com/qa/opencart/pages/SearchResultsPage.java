package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.ElementUtil;

public class SearchResultsPage {
	
	static WebDriver driver;
	private ElementUtil eleUtil;
	
	//1.Private By Locators
	private By searchProducts = By.cssSelector("div.product-thumb");
	
	
	//2.Public Page Class Constructor
	public SearchResultsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
		
	}
	
	public int getSearchProductCount() {
		return eleUtil.waitForElementsVisible(searchProducts, 10).size();
	}
	
	public ProductInfoPage selectProduct(String productName) {
		System.out.println("searching for product: " + productName);
		eleUtil.waitForElementVisible(By.linkText(productName), 10).click();
		return new ProductInfoPage(driver);
	}

}
