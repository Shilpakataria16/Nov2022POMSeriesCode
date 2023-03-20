package com.qa.opencart.pages;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {
	private WebDriver driver;
	private ElementUtil eleUtil;
	private By productHeader = By.cssSelector("div #content h1");
	private By productImages = By.cssSelector("ul.thumbnails img");
	private By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=1]/li");
	private By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=2]/li");
	private By quantity = By.id("input-quantity");
	private By addToCartBtn = By.id("button-cart");
	private By cartSuccessMsg = By.cssSelector("div.alert.alert-success");
	
	private Map<String,String> productInfoMap;
	
	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	public String getProductHeaderValue() {
		String productHeaderValue = eleUtil.doElementGetText(productHeader);
		System.out.println("product header value "+ productHeaderValue);
		return productHeaderValue;
	}

	public int getProductImagesCount() {
		int imagesCount = eleUtil.waitForElementsVisible(productImages, AppConstants.DEFAULT_MEDIUM_TIME_OUT).size();
		System.out.println("product images count: " + imagesCount);
		return imagesCount;
	}

	public void enterQuantity(int qty) {
		eleUtil.doSendKeys(quantity, Integer.toString(qty)); //String.valueOf(int qty)
	}
	
	public String addProductToCart() {
		eleUtil.doClick(addToCartBtn);
		String successMsg = eleUtil.waitForElementVisible(cartSuccessMsg, AppConstants.DEFAULT_SHORT_TIME_OUT).getText();
		StringBuilder sb = new StringBuilder(successMsg);
		String msg = sb.substring(0, successMsg.length()-1).replace("\n", "").toString();
		//successMsg = successMsg.substring(0,successMsg.length()-1).replace("\n", "");
		System.out.println("cart success msg: "+ msg);
		return msg;
	}
	
	public Map<String, String> getProductInfo() {
		
//		productInfoMap = new HashMap<String, String>();
		productInfoMap = new LinkedHashMap<String, String>();
		//Linked HashMap will maintain the order
		//header:
		productInfoMap.put("productname", getProductHeaderValue());
		getProductMetaData();					
		getProductPriceData();	
		System.out.println(productInfoMap);
		return productInfoMap;
	}
	
	//fetching meta data
	private void getProductMetaData() {
		//meta data:
		List<WebElement> metaList = eleUtil.getElements(productMetaData);
		for(WebElement e:metaList) {
			String meta = e.getText();
			String metaInfo[] = meta.split(":");
			String key = metaInfo[0].trim();
			String value = metaInfo[1].trim();
			productInfoMap.put(key, value);
		}
	}

	//fetching price data
	private void getProductPriceData() {
		//price:
		List<WebElement> priceList = eleUtil.getElements(productPriceData);
		String price = priceList.get(0).getText();
		String exTax = priceList.get(1).getText();
		String exTaxVal = exTax.split(":")[1].trim();
		
		productInfoMap.put("productprice", price);
			productInfoMap.put("extax", exTaxVal);
	}
}
