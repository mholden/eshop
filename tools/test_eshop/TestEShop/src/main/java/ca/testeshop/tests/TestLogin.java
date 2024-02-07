package ca.testeshop.tests;

import java.net.HttpURLConnection;

import ca.testeshop.services.*;
import ca.testeshop.utils.EShopResponse;
import ca.testeshop.utils.HttpUtils;
import ca.testeshop.utils.TestUtils;

public class TestLogin extends Test {
	
	public TestLogin() {
		
	}
	
	public TestLogin(String username, String password, Services services) {
		super(username, password, services);
	}
	
	// just test login with default existing user
	private void testSpecificCase1() throws Exception {
		EShopResponse response;
		
		System.out.println("\ntestSpecificCase1");
		
		doUserLogout();
		
		HttpUtils.disableRedirects.set(true);
		
		response = basketService.getBasketItems();
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_MOVED_TEMP || // should redirect to login 
			!response.redirectLocation.contains("/oauth2/authorization"), response.toString());
		
		HttpUtils.disableRedirects.set(false);
		
		doUserLogin("alice@testeshop.ca", "alice");
		
		response = basketService.getBasketItems();
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
		//response.dump();
	}
	
	public void run() throws Exception {
		testSpecificCase1();
	}
}
