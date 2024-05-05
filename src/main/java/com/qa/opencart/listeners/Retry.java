package com.qa.opencart.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;


//IQ: Which Annotation is responsible to provide the Retry logic in TestNG...? Ans: IRetryAnalyzer is an Interface only one method is there retry. 
    //returns true if the test method has to be retried, false otherwise. 


//IQ: Do u really want to implement Retry logic in ur Framework...?
   //No, user will not expect Retry logic --> better instead of implementing Retry logic you fix ur coding issue, implementation issue, application issue we shouldn't implement retry logic always be a part of feature flag. User is not going to re-attempt again n again
      //fix ur locator put ur wait timing make sure talk to ur developer y it's not coming properly on time, y it's taking lots of time to displaying, y server is very slow fix the core issue not Retry. 
      //yeah in some situation like network issue that time we can do re attempt, better i'll fix my xpath instead of retry 
public class Retry implements IRetryAnalyzer {
	private int count = 0;
	private static int maxTry = 3;

	@Override	
	public boolean retry(ITestResult iTestResult) {
		if (!iTestResult.isSuccess()) { // Check if test not succeed
			if (count < maxTry) { // Check if maxtry count is reached
				count++; // Increase the maxTry count by 1
				iTestResult.setStatus(ITestResult.FAILURE); // Mark test as failed
				return true; // Tells TestNG to re-run the test
			} else {
				iTestResult.setStatus(ITestResult.FAILURE); // If maxCount reached,test marked as failed
			}
		} else {
			iTestResult.setStatus(ITestResult.SUCCESS); // If test passes, TestNG marks it as passed
		}
		return false;
	}

}
