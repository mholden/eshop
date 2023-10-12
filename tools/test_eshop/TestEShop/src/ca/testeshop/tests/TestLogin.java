package ca.testeshop.tests;

import java.net.HttpURLConnection;
import java.util.*;

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
		
		doUserLogin("demouser@microsoft.com", "Pass@word1");
		
		response = identityService.getUserInfo();
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
	}
	
	public void run() throws Exception {
		testSpecificCase1();
	}
}
