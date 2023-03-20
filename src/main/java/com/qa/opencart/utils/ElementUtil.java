package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.factory.DriverFactory;

public class ElementUtil {
	private WebDriver driver;
	private JavaScriptUtil jsUtil;
	
	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		jsUtil = new JavaScriptUtil(driver);
	}
	public WebElement getElement(By locator) {
		WebElement element = driver.findElement(locator);
		if(Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(element);
		}
		return element;
	}

	public WebElement getElement(By locator,int timeOut) {
		return waitForElementVisible(locator, timeOut);
	}
	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}
	public void doSendKeys(By locator,String value) {
		WebElement element = getElement(locator);
		element.clear();
		element.sendKeys(value);
	}
	
	public void doClick(By locator) {
		getElement(locator).click();
	}
	
	public void doActionsSendKeys(By locator, String value) {
		Actions act = new Actions(driver);
		act.sendKeys(getElement(locator), value).build().perform();
	}
	public void doActionsClick(By locator) {
		Actions act = new Actions(driver);
		act.click(getElement(locator)).build().perform();
	}
	
	public String doElementGetText(By locator) {
		return getElement(locator).getText();
		
	}
	
	public boolean doElementIsDisplayed(By locator) {
		return getElement(locator).isDisplayed();
	}
	public String getElementAttribute(By locator,String attrName) {
		return getElement(locator).getAttribute(attrName);
	}
	public void getElementAttributes(By locator,String attrName) {
		List<WebElement> eleList = getElements(locator);
		for(WebElement e: eleList) {
			String attrVal = e.getAttribute(attrName);
			System.out.println(attrVal);
		}
	}
	public int getTotalElementsCount(By locator) {
		int eleCount = getElements(locator).size();
		System.out.println("total elements for : "+locator +"--->"+ eleCount);
		return eleCount;
	}
	public List<String> getElementsTextList(By locator) {
		List<String> eleTextList = new ArrayList<String>();
		List<WebElement> eleList = getElements(locator);
		for(WebElement e: eleList) {
			String text = e.getText();
			eleTextList.add(text);
		}
		return eleTextList;
	}

	//********** Select based dropdown methods ***********
	public void doSelectDropDownByIndex(By locator,int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}
	public void doSelectDropDownByValue(By locator,String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
	}
	public void doSelectDropDownByText(By locator,String text) {
		Select select = new Select(getElement(locator));
		select.selectByValue(text);
	}
	public List<WebElement> getDropDownOptionsList(By locator) {
		Select select = new Select(getElement(locator));
		return select.getOptions();
	}
	public List<String> getDropDownOptionsTextList(By locator) {
		List<WebElement> optionsList = getDropDownOptionsList(locator);
		List<String> optionsTextList = new ArrayList<String>();
		for(WebElement e:optionsList) {
			String text = e.getText();
			optionsTextList.add(text);
		}
		return optionsTextList;
	}
	public void selectDropDownValue(By locator,String expValue) {
		List<WebElement> optionsList = getDropDownOptionsList(locator);
		for(WebElement e:optionsList) {
			String text = e.getText();
			System.out.println(text);
			if(text.equals(expValue)) {
				e.click();
				break;
			}
		}
	}
	public int getTotalDropDownOptions(By locator) {
		int optionsCount = getDropDownOptionsList(locator).size();
		System.out.println("total options "+optionsCount);
		return optionsCount;
	} 
	
	public void selectDropDownValue_WithoutSelect(By locator,String expValue) {
		List<WebElement> optionsList =getElements(locator);
		for(WebElement e:optionsList) {
			String text = e.getText();
			System.out.println(text);
			if(text.equals(expValue)) {
				e.click();
				break;
			}
		}
	}

	//************ Wait utils ***********//
	/*
	 * An expectation for checking that an element is present on the DOM of a page. This does notnecessarily mean that the element is visible.
	 */
	public WebElement waitForElementPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	/*
	 * An expectation for checking that an element is present on the DOM of a page and visible.Visibility means that the element is not only displayed but also has a height and width that isgreater than 0.
	 */
	public WebElement waitForElementVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public List<WebElement> waitForElementsVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}
	
	/**
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public List<WebElement> waitForElementsPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}
	
	public Alert waitForAlertPresence(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.alertIsPresent());
	}
	
	public String getAlertText(int timeOut) {
		return waitForAlertPresence(timeOut).getText();
	}
	
	public void acceptAlert(int timeOut) {
		waitForAlertPresence(timeOut).accept();
	}
	
	public void dismissAlert(int timeOut) {
		waitForAlertPresence(timeOut).dismiss();;
	}
	
	public void alertSendKeys(int timeOut,String value) {
		waitForAlertPresence(timeOut).sendKeys(value);;
	}

	public String waitForTitleContainsAndFetch(int timeOut,String titleFractionValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.titleContains(titleFractionValue));
		return driver.getTitle();
	}
	
	public String waitForTitleIsAndFetch(int timeOut,String titleFractionValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.titleIs(titleFractionValue));
		return driver.getTitle();
	}
	
	public String waitForURLContainsAndFetch(int timeOut,String urlFractionValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.urlContains(urlFractionValue));
		return driver.getCurrentUrl();
	}
	
	public Boolean waitForURLIsAndFetch(int timeOut,String urlValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.urlToBe(urlValue));
	}
	
	public void waitForFrameAndSwitchToItByIDOrName(int timeOut,String idOrName) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(idOrName));
	}
	
	public void waitForFrameAndSwitchToItByIndex(int timeOut,String frameIndex) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
	}
	
	public void waitForFrameAndSwitchToItByFrameElement(int timeOut,WebElement frameElement) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
	}
	public void waitForFrameAndSwitchToItByFrameLocator(int timeOut,By frameLocator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}
	
	public void clickWhenReady(int timeOut,By locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}
	
	public WebElement waitForElementToBeClickable(int timeOut,By locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}
	
	public void doClickWithActionsAndWait(int timeOut,By locator) {
		WebElement ele = waitForElementToBeClickable(timeOut,locator);
		Actions act = new Actions(driver);
		act.click(ele).build().perform();
	}
	
	public WebElement waitForElementPresenceWithFluentWait(int timeOut,int pollingTime,By locator) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeOut))
				.ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class)
				.pollingEvery(Duration.ofSeconds(pollingTime))
				.withMessage("Element is not found on tha page");
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	public void waitForAlertWithFluentWait(int timeOut,int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeOut))
				.ignoring(NoAlertPresentException.class)
				.pollingEvery(Duration.ofSeconds(pollingTime))
				.withMessage("Alert is not found on tha page");
		wait.until(ExpectedConditions.alertIsPresent());
		
	}
}
