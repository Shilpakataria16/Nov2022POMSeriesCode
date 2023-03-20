package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class ProductInfoPageTest extends BaseTest{
	@BeforeClass
	public void accPageSetUp() {
		accPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
	}
	
	@DataProvider
	public Object[][] getProductImagesTestData(){
		return new Object[][] {
			{"Macbook", "MacBook Pro",4},
			{"iMac", "iMac",3},
			{"Apple", "Apple Cinema 30\"",6},
			{"Samsung", "Samsung SyncMaster 941BW",1},
			};
	}
	
	@Test(dataProvider = "getProductImagesTestData")
	public void productImagesCountTest(String searchKey,String productName,int imagesCount) {
		searchPage = accPage.performSearch(searchKey);
		productInfoPage = searchPage.selectProduct(productName);
		int accImagesCount = productInfoPage.getProductImagesCount();
		Assert.assertEquals(accImagesCount, imagesCount);
	}
	
	@DataProvider
	public Object[][] getProductInfoTestData(){
		return new Object[][] {
			{"MacBook","MacBook Pro","Apple","Product 18","800","In Stock","$2,000.00"},
			{"Samsung","Samsung Galaxy Tab 10.1","","SAM1","1000","Pre-Order","$199.99"}
		};
	}
	
	@Test(dataProvider = "getProductInfoTestData")
	public void productInfoTest(String searchKey,String productName,String brand,String productCode,String rewardPoints,String availability,String productPrice) {
		searchPage = accPage.performSearch(searchKey);
		productInfoPage = searchPage.selectProduct(productName);
		Map<String,String> actualProductInfoMap = productInfoPage.getProductInfo();
		softAssert.assertEquals(actualProductInfoMap.get("Brand"), brand);
		softAssert.assertEquals(actualProductInfoMap.get("Product Code"), productCode);
		softAssert.assertEquals(actualProductInfoMap.get("Reward Points"), rewardPoints);
		softAssert.assertEquals(actualProductInfoMap.get("Availability"), availability);
		softAssert.assertEquals(actualProductInfoMap.get("productname"), productName);
		softAssert.assertEquals(actualProductInfoMap.get("productprice"), productPrice);
	}
	
	@DataProvider
	public Object[][] getAddToCartTestData(){
		return new Object[][] {
			{"MacBook","MacBook Pro",2},
			{"iMac","iMac",1}
		};
	}
	
	@Test(dataProvider = "getAddToCartTestData")
	public void addToCartTest(String searchKey,String productName,int qty) {
		searchPage = accPage.performSearch(searchKey);
		productInfoPage = searchPage.selectProduct(productName);
		productInfoPage.enterQuantity(qty);
		String actualcartMsg = productInfoPage.addProductToCart();
		softAssert.assertTrue(actualcartMsg.contains("Success"));
		softAssert.assertTrue(actualcartMsg.contains(productName));
		softAssert.assertEquals(actualcartMsg, "Success: You have added "+ productName +" to your shopping cart!");
		softAssert.assertAll();
	}
}


