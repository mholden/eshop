package ca.testeshop.tests;

import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import ca.testeshop.services.*;
import ca.testeshop.utils.*;

public class TestSecurity extends Test {
	
	public TestSecurity() {
		
	}
	
	public TestSecurity(String username, String password, Services services) {
		super(username, password, services);
	}
	
	//
	// touch a number of secured end-points across different services and
	// make sure we're not getting redirected to the authentication service.
	// this failed before using an api gateway
	//
	private void testSpecificCase1() throws Exception {
		Integer sRedirects, eRedirects;
		EShopResponse response;
		
		System.out.println("\ntestSpecificCase1");
		
		doUserRegistration();
		
		System.out.println("user: " + getUserName());
		
		sRedirects = HttpUtils.nRedirects.get();
		//System.out.println("nRedirects at start: " + sRedirects);
		
		response = catalogService.getCatalogItems(0, 12);
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
		
		response = basketService.getBasketItems();
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
		//response.dump();
		
		response = orderService.getOrders();
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
		
		eRedirects = HttpUtils.nRedirects.get();
		//System.out.println("nRedirects at end: " + eRedirects);
		
		TestUtils.failIf(eRedirects > sRedirects, null);
	}
	
	private void testSpecificCase2() throws Exception {
		String fileName;
		Content content;
		EShopResponse response;
		
		System.out.println("\ntestSpecificCase2");
		
		doUserRegistration();
		
		System.out.println("user: " + getUserName());
		
		fileName = "/TestCatalogCRUD/cute-cat.png";
		content = new Content(fileName);
		response = contentService.uploadContent(content); 
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_FORBIDDEN, response.toString()); // shouldn't be allowed
		
		response = catalogService.createCatalogItem(new CatalogItem("TestSecurity-testSpecificCase2-item", 599, "dummyId"));
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_FORBIDDEN, response.toString()); // shouldn't be allowed
	}
	
	public void run() throws Exception {
		testSpecificCase1();
		testSpecificCase2();
	}
}
