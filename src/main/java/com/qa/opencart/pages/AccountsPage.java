package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class AccountsPage {
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By logoutLink = By.linkText("Logout");
	private By accHeaders = By.xpath("//div[@id='content']/h2");   //By.ByCssSelector("div#content h2")
	private By searchField = By.name("search");
	private By searchIcon = By.cssSelector("#search button");
	
	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	public String getAccPageTitle() {
		String title = eleUtil.waitForTitleIsAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT, AppConstants.ACCOUNTS_PAGE_TITLE_VALUE);
		System.out.println("Acc page title: "+ title);
		return title;
	}
	
	public String getAccPageURL() {
		String url =  eleUtil.waitForURLContainsAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT, AppConstants.ACCOUNTS_PAGE_URL_FRACTION_VALUE);
		System.out.println("Acc page url: "+ url);
		return url;
	} 
	
	public boolean isLogoutLinkExist() {
		return eleUtil.waitForElementVisible(logoutLink, AppConstants.DEFAULT_MEDIUM_TIME_OUT).isDisplayed();
//		return driver.findElement(logoutLink).isDisplayed();
	}
	
	public boolean isSearchFieldExist() {
		return eleUtil.waitForElementVisible(searchField, AppConstants.DEFAULT_MEDIUM_TIME_OUT).isDisplayed();
//		return driver.findElement(searchField).isDisplayed();
	}
	
	public List<String> getAccountsPageHeadersList() {
		List<WebElement> accHeadersList = eleUtil.waitForElementsVisible(accHeaders, AppConstants.DEFAULT_MEDIUM_TIME_OUT);
//		List<WebElement> accHeadersList = driver.findElements(accHeaders);
		List<String> accHeadersValList = new ArrayList<String>();
		
		for(WebElement e: accHeadersList) {
			String headerText = e.getText();
			accHeadersValList.add(headerText);
		}
		
		return accHeadersValList;
	}
	
	public SearchPage performSearch(String searchKey) {
		if(isSearchFieldExist()) {
			eleUtil.doSendKeys(searchField, searchKey);
			eleUtil.doClick(searchIcon);
			return new SearchPage(driver);
		}
		else {
			System.out.println("Search field is not present");
			return null;
		}
	}
}
