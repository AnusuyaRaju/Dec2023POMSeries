package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.errors.AppError;
import com.qa.opencart.exceptions.BrowserException;
import com.qa.opencart.exceptions.FrameworkException;
import com.qa.opencart.logger.Log;

public class DriverFactory {

	WebDriver driver;
	Properties prop;
	OptionsManager optionsManager;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>(); // tlDriver means threadLocalDriver

	public static String highlight;

	public WebDriver initDriver(Properties prop) {

		String browserName = prop.getProperty("browser");

		System.out.println("browser name is: " + browserName);

		Log.info("Browser name is : " + browserName); // print o/p in console also in file which is in log4j2.xml ->
														// fileName="logs/application.log"

		highlight = prop.getProperty("highlight");

		optionsManager = new OptionsManager(prop);

		switch (browserName.trim().toLowerCase()) {
		case "chrome":
			// driver = new ChromeDriver(optionsManager.getChromeOptions());
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			break;
		case "firefox":
			// driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			break;
		case "edge":
			// driver = new EdgeDriver(optionsManager.getEdgeOptions());
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			break;

		case "safari":
			// driver = new SafariDriver();
			tlDriver.set(new SafariDriver());
			break;

		default:
			// System.out.println("Plz pass right browser...." + browserName);
			Log.error("Plz pass right browser...." + browserName);
			throw new BrowserException("NO BROWSER FOUND BROWSER..." + browserName);
		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));

		return getDriver();

	}

	public static WebDriver getDriver() {
		return tlDriver.get();
	}

	// right clk on config.properties copy path from src b4 pasting jus add ./paste
	// the path
	// ./ means in the current project directory
	// FileInputStream class will create connection with config.properties once
	// connection established we've to load all the properties to this particular
	// obj
	public Properties initProp() {

		// envName=qa,stage,prod,uat,dev
		// mvn clean install -Denv="uat" -DsuiteXmlFiles="//"
		FileInputStream ip = null;
		prop = new Properties();

		String envName = System.getProperty("env");
		System.out.println("Running tests on Env: " + envName);

		try {
			if (envName == null) {
				System.out.println("No env is given...hence running it on QA env...");
				ip = new FileInputStream("./src/test/resources/config/config.qa.properties");
			} else {

				switch (envName.toLowerCase().trim()) {
				case "qa":
					ip = new FileInputStream("./src/test/resources/config/config.qa.properties");
					break;
				case "dev":
					ip = new FileInputStream("./src/test/resources/config/config.dev.properties");
					break;
				case "stage":
					ip = new FileInputStream("./src/test/resources/config/config.stage.properties");
					break;
				case "uat":
					ip = new FileInputStream("./src/test/resources/config/config.uat.properties");
					break;
				case "prod":
					ip = new FileInputStream("./src/test/resources/config/config.properties");
					break;

				default:
					System.out.println("plz pass the right env name... " + envName);
					throw new FrameworkException(AppError.ENV_NAME_NOT_FOUND + " : " + envName);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;
	}

	/**
	 * take screenshot
	 */

	public static String getScreenshot(String methodName) {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);// temporary directory
		String path = System.getProperty("user.dir") + "/screenshot/" + methodName + "_" + System.currentTimeMillis()
				+ ".png";

		File destination = new File(path);

		try {
			FileHandler.copy(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return path;
	}
}
