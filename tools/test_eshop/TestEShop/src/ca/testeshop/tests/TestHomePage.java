package ca.testeshop.tests;

import java.net.HttpURLConnection;

import ca.testeshop.services.*;
import ca.testeshop.utils.EShopResponse;
import ca.testeshop.utils.TestUtils;

public class TestHomePage extends Test {
	
	public TestHomePage() {
		
	}
	
	public TestHomePage(String username, String password, Services services) {
		super(username, password, services);
	}
	
	private void testSpecificCase1() throws Exception {
		EShopResponse response;
		
		System.out.println("\ntestSpecificCase1");
		
		response = aggregatorService.getCatalogBrands();
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
		//response.dump();
		
		response = aggregatorService.getCatalogTypes();
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
		//response.dump();
		
		response = aggregatorService.getCatalogItems(0, 12);
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
		//response.dump();
	}
	
	public void run() throws Exception {
		testSpecificCase1();
	}
}
