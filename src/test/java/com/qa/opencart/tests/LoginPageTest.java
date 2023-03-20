package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic - 100: design login for open cart app")
@Story("US Login: 101: design login page features for open cart")
public class LoginPageTest extends BaseTest{
	@Severity(SeverityLevel.TRIVIAL)
	@Description("Checking the title of the page..Tester:Shilpa")
	@Test(priority =1)
	public void loginPageTitleTest() {
		String actualTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actualTitle, AppConstants.LOGIN_PAGE_TITLE_VALUE);
	}
	
	@Severity(SeverityLevel.NORMAL)
	@Description("Checking the url of the page..Tester:Shilpa")
	@Test(priority =2)
	public void loginPageURLTest() {
		String actualURL = loginPage.getLoginPageURL();
		Assert.assertTrue(actualURL.contains(AppConstants.LOGIN_PAGE_URL_FRACTION_VALUE));
	}
	
	@Severity(SeverityLevel.CRITICAL)
	@Description("Checking the forgot password link exists..Tester:Shilpa")
	@Test(priority =3)
	public void forPwdLinkExistTest() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExist());
	}
	
	
	@Test(priority =4)
	public void naveenAutomationLogoExistTest() {
		Assert.assertTrue(loginPage.isNaveenAutomationLogoExist());
	}
	
	@Test(priority =5)
	public void newCustomerHeaderExistTest() {
		Assert.assertTrue(loginPage.isNewCustomerHeaderExist());
	}
	
	@Test(priority =6)
	public void returningCustomerHeaderExistTest() {
		Assert.assertTrue(loginPage.isReturningCustomerHeaderExist());
	}
	
	@Test(priority =7)
	public void myAccountLinkClickableTest() {
		Assert.assertTrue(loginPage.isMyAccountLinkClickable());
	}
	
	@Severity(SeverityLevel.BLOCKER)
	@Description("checking user is able to login to app with correct username and password")
	@Test(priority =8)
	public void loginTest() {
		accPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}
}
