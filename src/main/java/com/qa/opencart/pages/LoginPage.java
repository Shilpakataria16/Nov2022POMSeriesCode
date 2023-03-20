package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	//1. private By locators:
	private By emailId = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By forgotPwdLink = By.linkText("Forgotten Password");
	private By registerLink = By.linkText("Register");
	private By naveenAutomationLogo = By.xpath("//img[@title='naveenopencart']");
	private By myAccountLink = By.linkText("My Account");
	private By newCustomerHeader = By.xpath("//h2[text()='New Customer']");
	private By returningCustomerHeader = By.xpath("//h2[text()='Returning Customer']");
	
	//2. page constructor
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	//3. page actions/methods
	@Step("Getting the login page title")
	public String getLoginPageTitle() {
		String title = eleUtil.waitForTitleIsAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT, AppConstants.LOGIN_PAGE_TITLE_VALUE);
		System.out.println("Login page title: " + title);
		return title;
	}
	
	@Step("Getting the login page url")
	public String getLoginPageURL() {
		String url = eleUtil.waitForURLContainsAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT, AppConstants.LOGIN_PAGE_URL_FRACTION_VALUE);
//		String url = driver.getCurrentUrl();
		System.out.println("Login page url: "+ url);
		return url;
	}
	
	@Step("Getting the forgot password link")
	public boolean isForgotPwdLinkExist() {
		return eleUtil.waitForElementVisible(forgotPwdLink, AppConstants.DEFAULT_MEDIUM_TIME_OUT).isDisplayed();
	}
	
	@Step("Login with username:{0} and password:{1}")
	public AccountsPage doLogin(String un,String pwd) {
		eleUtil.waitForElementVisible(emailId, AppConstants.DEFAULT_MEDIUM_TIME_OUT).sendKeys(un);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		return new AccountsPage(driver);
	}
	
	public boolean isNaveenAutomationLogoExist() {
		return eleUtil.doElementIsDisplayed(naveenAutomationLogo);
	}
	
	public boolean isMyAccountLinkClickable() {
		return eleUtil.getElement(myAccountLink).isEnabled();
	}
	
	public boolean isNewCustomerHeaderExist() {
		return eleUtil.doElementIsDisplayed(newCustomerHeader);
	}
	
	public boolean isReturningCustomerHeaderExist() {
		return eleUtil.doElementIsDisplayed(returningCustomerHeader);
	}
	
	@Step("navigating to Register page")
	public RegisterPage navigateToRegisterPage() {
		eleUtil.doClick(registerLink);
		return new RegisterPage(driver);
	}
}
