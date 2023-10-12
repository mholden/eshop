package ca.testeshop.tests;

import java.net.HttpURLConnection;

import com.google.gson.reflect.TypeToken;

import ca.testeshop.services.*;
import ca.testeshop.utils.*;

public class TestBasket extends Test {
	
	public TestBasket() {
		
	}
	
	public TestBasket(String username, String password, Services services) {
		super(username, password, services);
	}
	
	// test get, set
	private void testSpecificCase1() throws Exception {
		PagenatedItems<CatalogItem> catalogItems;
		CustomerBasket basket;
		EShopResponse response;
		
		System.out.println("\ntestSpecificCase1");
		
		doUserLogin("demouser@microsoft.com", "Pass@word1");
		
		response = aggregatorService.getCatalogItems(0, 12);
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
		
		catalogItems = (PagenatedItems<CatalogItem>)JsonUtils.jsonToPojo(response.response, new TypeToken<PagenatedItems<CatalogItem>>(){}.getType());
		//System.out.println(catalogItems);
		
		response = aggregatorService.getBasket();
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
		
		basket = (CustomerBasket)JsonUtils.jsonToPojo(response.response, CustomerBasket.class);
		//System.out.println(basket);
		
		basket.items.clear();
		basket.items.add(new BasketItem(catalogItems.data.get(0)));
		//System.out.println(basket);
		
		response = aggregatorService.setBasket(basket);
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
		
		//System.out.println("set it, getting it again");
		
		response = aggregatorService.getBasket();
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
		
		basket = (CustomerBasket)JsonUtils.jsonToPojo(response.response, CustomerBasket.class);
		//System.out.println(basket);
	}
	
	public void run() throws Exception {
		testSpecificCase1();
	}
}
