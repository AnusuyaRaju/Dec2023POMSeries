package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.exceptions.ElementException;
import com.qa.opencart.factory.DriverFactory;

import io.qameta.allure.Step;

//SRP: Single Responsible Principle -  only responsible for element related work
public class ElementUtil {

	private WebDriver driver; // current value of non primitive value is null
	private JavaScriptUtil jsUtil;
	
	private final String DEFAULT_ELEMENT_TIME_OUT_MESSAGE = "Time out...Element is not found";
	private final String DEFAULT_ALERT_TIME_OUT_MESSAGE = "Time out...Alert is not found";

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		jsUtil = new JavaScriptUtil(driver);
	}

	private void nullBlanckCheck(String value) {
		if (value == null || value.length() == 0) {
			throw new ElementException(value + " -----value text can not be null or blank");
		}

	}

	public By getBy(String locatorType, String locatorValue) {
		By locator = null;
		switch (locatorType.toLowerCase().trim()) {
		case "id":
			locator = By.id(locatorValue);
			break;
		case "name":
			locator = By.name(locatorValue);
			break;
		case "classname":
			locator = By.className(locatorValue);
			break;
		case "xpath":
			locator = By.xpath(locatorValue);
			break;
		case "css":
			locator = By.cssSelector(locatorValue);
			break;
		case "linkText":
			locator = By.linkText(locatorValue);
			break;
		case "partialLinkText":
			locator = By.partialLinkText(locatorValue);
			break;
		case "tagName":
			locator = By.tagName(locatorValue);
			break;

		default:
			break;
		}
		return locator;

	}
    
	private void checkHighlight(WebElement element) {
		if(Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(element);	
		}
	}
	
	
	public WebElement getElement(String locatorType, String locatorValue) {
		WebElement element = driver.findElement(getBy(locatorType, locatorValue));
		checkHighlight(element);
		return element;

	}
    
	@Step("getting web element using locator {0}")
	public WebElement getElement(By locator) {
		WebElement element = null;
		try {
			element = driver.findElement(locator);
			checkHighlight(element);
		} catch (NoSuchElementException e) {
			System.out.println("Element is no present on the page");
			e.printStackTrace();
		}
		return element;

	}
    
	@Step("entering value: {1} in element: {0}")
	public void doSendKeys(By locator, String value) { //without wait
		nullBlanckCheck(value);
		getElement(locator).clear();
		getElement(locator).sendKeys(value);

	}
	
	public void doSendKeys(By locator, String value, int timeOut) { //with wait
		nullBlanckCheck(value);
		waitForElementVisible(locator, timeOut).sendKeys(value);

	}

	public void doSendKeys(String locatorType, String locatorValue, String value) {
		getElement(locatorType, locatorValue).sendKeys(value);
		

	}
    
	@Step("clicking on element using locator: {0}")
	public void doClick(By locator) { //without wait
		getElement(locator).click();
	}
	
	public void doClick(By locator, int timeOut) { //with wait
		waitForElementVisible(locator, timeOut).click();
	}

	public String doGetElementText(By locator) {
		return getElement(locator).getText();
	}

	public String doElementGetAttribute(By locator, String attrName) {
		return getElement(locator).getAttribute(attrName);
	}
    
	@Step("checking element {0} is displayed....")
	public boolean isElementDisplayed(By locator) {
		return getElement(locator).isDisplayed();
	}

	public boolean isElementExist(By locator) {
		if (getElements(locator).size() == 1) {
			return true;
		}
		return false;
	}

	public boolean multipleElementsExist(By locator) {
		if (getElements(locator).size() > 0) {
			return true;
		}
		return false;
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	public int getElementsCount(By locator) {
		return getElements(locator).size();
	}

	public ArrayList<String> getElementsTextList(By locator) {
		List<WebElement> eList = getElements(locator);
		ArrayList<String> eleTextList = new ArrayList<String>();// pc = 0

		for (WebElement e : eList) {
			String text = e.getText();
			if (text.length() != 0) {
				eleTextList.add(text);

			}

		}
		return eleTextList;
	}

	public ArrayList<String> getelementAttributeList(By locator, String attrName) {
		List<WebElement> eList = getElements(locator);
		ArrayList<String> eleAttrList = new ArrayList<String>();

		for (WebElement e : eList) {
			String attrValue = e.getAttribute(attrName);
			if (attrValue.length() != 0) {
				eleAttrList.add(attrValue);

			}
		}

		return eleAttrList;

	}

	// ********************Select based Drop Down Utils
	// ****************************//

	public void doSelectByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);

	}

	public void doSelectByVisibleText(By locator, String visibleText) {
		nullBlanckCheck(visibleText);
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(visibleText);

	}

	public void doSelectByValue(By locator, String value) {// "" blank means = 0
		nullBlanckCheck(value);
		Select select = new Select(getElement(locator));
		select.selectByValue(value);

	}

	public List<String> getDropDownOptionsTextList(By locator) {
		List<WebElement> optionsList = getDropDownOptionsList(locator);
		// target is that have to convert List<WebElement> to List<String>

		List<String> optionsTextList = new ArrayList<String>();
		for (WebElement e : optionsList) {
			String optionText = e.getText();
			optionsTextList.add(optionText);

		}
		return optionsTextList;

	}

	public List<WebElement> getDropDownOptionsList(By locator) {
		Select select = new Select(getElement(locator));
		return select.getOptions();
	}

	public int getDropDownValuesCount(By locator) {
		return getDropDownOptionsList(locator).size();

	}

	public void doSelectDropDownValues(By locator, String value) {
		nullBlanckCheck(value);
		List<WebElement> optionsList = getDropDownOptionsList(locator);
		System.out.println(optionsList.size());

		for (WebElement e : optionsList) {
			String text = e.getText();
			System.out.println(text);

			if (text.equals(value)) {
				e.click();
				break; // it will break entire for each loop
			}

		}

	}

	public void printSelectDropDownValue(By locator, String value) {
		List<WebElement> optionsList = getDropDownOptionsList(locator);
		for (WebElement e : optionsList) {
			String text = e.getText();
			System.out.println(text);
		}

	}

	// ***************** Without select class, select the drop down value
	// *************************//

	public void doSelectValuesFromDropDown(By locator, String value) {
		List<WebElement> optionsList = getElements(locator); // shortcut for assigning local variable cntl+2 then it
																// will popup List<> at bottom right corner common for
																// all methods
		System.out.println(optionsList.size());
		for (WebElement e : optionsList) {
			String text = e.getText();
			if (text.equals(value)) {
				e.click();
				break;
			}
		}

	}

	public void doSearch(By searchlocator, By searchSuggestions, String searchKey, String value)
			throws InterruptedException {
		doSendKeys(searchlocator, searchKey);
		Thread.sleep(3000);
		List<WebElement> suggList = getElements(searchSuggestions);
		System.out.println(suggList.size());

		for (WebElement e : suggList) {
			String text = e.getText();
			System.out.println(text);
			if (text.contains(value)) {
				e.click();
				break;

			}
		}

	}

	//********************** Actions Utils *******************************//
	
	public void handleMenuSubMenuLevel2(By parentMenuLocator, By SubMenuLocator) throws InterruptedException {
		Actions act = new Actions(driver);
		act.moveToElement(getElement(parentMenuLocator)).perform();
		Thread.sleep(1500);
		doClick(SubMenuLocator);

	}

	public void handleMenuSubMenuLevel4(By level1Menu, By level2Menu, By level3Menu, By level4Menu)
			throws InterruptedException {

		doClick(level1Menu);
		Thread.sleep(1500);
		Actions act = new Actions(driver);
		act.moveToElement(getElement(level2Menu)).perform();
		Thread.sleep(1500);
		act.moveToElement(getElement(level3Menu)).perform();
		Thread.sleep(1500);
		doClick(level4Menu);

	}
	
	public void doActionsClick(By locator) {
		Actions act = new Actions(driver);
		act.click(getElement(locator)).perform();
	}
	
	public void doActionsSendKeys(By locator, String value) {
		Actions act = new Actions(driver);
		act.sendKeys(getElement(locator), value).perform();
	}
	
	//******************** Wait Utils *************************//
	
	/**
	 * An expectation for checking an element is visible and enabled such that you can click it.
	 * @param locator
	 * @param timeOut
	 */
	public void clickWhenReady(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
		checkHighlight(element);
		element.click();
	}
	
	/**
	 * An expectation for checking that an element is present on the DOM of a page. 
	 * This does not necessarily mean that the element is visible.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	
	public WebElement waitForElementPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		checkHighlight(element);
		return element;
		
	}
	
	/**
     * An expectation for checking that an element is present on the DOM of a page and visible.
     * Visibility means that the element is not only displayed but also has a height and width that is greater than 0.
     * @param locator
	 * @param timeOut
	 * default interval time = 500 ms
	 * @return
	 */
	
	@Step("waiting for element: {0} within timeout of {1}")
	public WebElement waitForElementVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		checkHighlight(element);
		return element;
		
	}
	
	public WebElement waitForElementVisible(By locator, int timeOut, int intervalTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(intervalTime));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		checkHighlight(element);
		return element;
	}
	
	/**
	 * An expectation for checking that there is at least one element present on a web page.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public List<WebElement> waitForElementsPresence(By locator, int timeOut) {//WithoutFluentWait
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
		
	}
	
	
	//WithFluentWait:
	public List<WebElement> waitForElementsPresenceWithFluentWait(By locator, int timeOut) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				                   .withTimeout(Duration.ofSeconds(10))
				                   .pollingEvery(Duration.ofSeconds(2))
				                   .ignoring(NoSuchElementException.class)
				                   .withMessage(DEFAULT_ELEMENT_TIME_OUT_MESSAGE);
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
		
	}
	
	/**
	 * An expectation for checking that all elements present on the web page that match the locatorare visible. 
	 * Visibility means that the elements are not only displayed but also have a heightand width that is greater than 0.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public List<WebElement> waitForElementsVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		
	}
	
	//************************* Non WebElements *******************************//
	//IQ: Can we handle Non WebElements title,url,alerts,windows via ExplicitWait...?
	//If we have a long title then we can pass titleFraction n used titleContains:
	public String waitForTitleContains(String titleFraction, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
		if(wait.until(ExpectedConditions.titleContains(titleFraction))) {
			return driver.getTitle();
		}
		} catch (Exception e) {
			System.out.println("title is not found within : " + timeOut);
		}
		return null;	
		
	}
	
	//If we give an exact title then we can pass title alone n used titleIs:
	@Step("waiting for expected title....")
	public String waitForTitleIs(String title, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
		if(wait.until(ExpectedConditions.titleIs(title))) {
			return driver.getTitle();
		}
		} catch (Exception e) {
			System.out.println("title is not found within : " + timeOut);
		}
		return driver.getTitle();	
		
	}
	
	//If we have a long URL then we can pass urlFraction n used urlContains:
	    @Step("waiting for expected URL....")
		public String waitForURLContains(String urlFraction, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			try {
			if(wait.until(ExpectedConditions.urlContains(urlFraction))) {
				return driver.getCurrentUrl();
			}
			} catch (Exception e) {
				System.out.println("URL fraction is not found within : " + timeOut);
			}
			return driver.getCurrentUrl();	
			
		}
		
		//If we give an exact URL then we can pass full url n used urlToBe:
		public String waitForURLIs(String url, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			try {
			if(wait.until(ExpectedConditions.urlToBe(url))) {
				return driver.getCurrentUrl();
			}
			} catch (Exception e) {
				System.out.println("URL is not found within : " + timeOut);
			}
			return driver.getCurrentUrl();	
			
		}
		
		public Alert waitForJSAlertWithFluentWait(int timeOut, int pollingTime) {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
					                   .withTimeout(Duration.ofSeconds(10))
					                   .pollingEvery(Duration.ofSeconds(2))
					                   .ignoring(NoAlertPresentException.class)
					                   .withMessage(DEFAULT_ALERT_TIME_OUT_MESSAGE);
			return wait.until(ExpectedConditions.alertIsPresent());
			
		}
		
		public Alert waitForJSAlert(int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.alertIsPresent());
				
		}
		
		public String getAlertText(int timeOut) {
			return waitForJSAlert(timeOut).getText();
				
		}
		
		public void acceptAlert(int timeOut) {
		    waitForJSAlert(timeOut).accept();
				
		}
		
		public void dismissAlert(int timeOut) {
		    waitForJSAlert(timeOut).dismiss();
				
		}
		
		public void alertSendKeys(int timeOut, String value) {
		    waitForJSAlert(timeOut).sendKeys(value);
				
		}
		
		public Boolean waitForWindow(int numberOfWindowsToBe, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			return wait.until(ExpectedConditions.numberOfWindowsToBe(numberOfWindowsToBe));
				
			
		}
		
		//Frame: No need to switch the driver to Frame frameToBeAvailableAndSwitchToIt internally taken care of driver.switchTo().frame() 
		public void waitForFrameAndSwitchToIt(By frameLocator, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
				
		}
		
		public void waitForFrameAndSwitchToIt(int frameIndex, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
				
		}
		
		public void waitForFrameAndSwitchToIt(WebElement frameElement, int timeOut) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
				
		}
		
		//CustomWait:
		public WebElement retryingElement(By locator, int timeOut) {
			
			WebElement element = null;
			int attempts = 0;
			
			//while loop for we're not sure on how many times it's going to be happen
			while(attempts<timeOut) {
				try {
				element = getElement(locator);
				System.out.println("element is found....." + locator + " in attempts " + attempts);
				break;
				} catch (NoSuchElementException e) {
					System.out.println("element is not found...." + " in attempts " + attempts);
					TimeUtil.defaultTime();
				}
				attempts++;
			}
			
			if(element == null) {
				System.out.println("element is not found.... tried for" + timeOut + " times " + " with the interval of " + 500 + " milliseconds...");
				throw new ElementException("No Such Element");
			}
			checkHighlight(element);
			return element;
			
		}
		
		
	public WebElement retryingElement(By locator, int timeOut, int intervalTime) {
			
			WebElement element = null;
			int attempts = 0;
			
			//while loop for we're not sure on how many times it's going to be happen
			while(attempts<timeOut) {
				try {
				element = getElement(locator);
				System.out.println("element is found....." + locator + " in attempts " + attempts);
				break;
				} catch (NoSuchElementException e) {
					System.out.println("element is not found...." + " in attempts " + attempts);
					TimeUtil.applyWait(intervalTime);
				}
				attempts++;
			}
			
			if(element == null) {
				System.out.println("element is not found.... tried for" + timeOut + " times " + " with the interval of " + intervalTime + " seconds...");
				throw new ElementException("No Such Element");
			}
			checkHighlight(element);
			return element;
			
		}
		
		
	

}

