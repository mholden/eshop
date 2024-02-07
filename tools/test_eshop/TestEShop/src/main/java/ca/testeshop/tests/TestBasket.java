package ca.testeshop.tests;

import java.net.HttpURLConnection;
import java.util.*;

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
		List<CatalogItem> catalogItems;
		List<BasketItem> basketItems;
		EShopResponse response;
		
		System.out.println("\ntestSpecificCase1");
		
		doUserLogin("alice@testeshop.ca", "alice");
		
		response = catalogService.getCatalogItems(0, 12);
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
		
		catalogItems = (List<CatalogItem>)JsonUtils.jsonToPojo(response.response, new TypeToken<List<CatalogItem>>(){}.getType());
		//System.out.println(catalogItems);
		
		response = basketService.getBasketItems();
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
		//response.dump();
		
		basketItems = (List<BasketItem>)JsonUtils.jsonToPojo(response.response, new TypeToken<List<BasketItem>>(){}.getType());
		//System.out.println(basketItems);
		
		basketItems.clear();
		basketItems.add(new BasketItem(catalogItems.get(0)));
		//System.out.println(basketItems);
		
		response = basketService.setBasketItems(basketItems);
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
		
		//System.out.println("set it, getting it again");
		
		response = basketService.getBasketItems();
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
		
		basketItems = (List<BasketItem>)JsonUtils.jsonToPojo(response.response, new TypeToken<List<BasketItem>>(){}.getType());
		//System.out.println(basketItems);
		TestUtils.failIf(basketItems.size() != 1, null);
		TestUtils.failIf(basketItems.get(0).catalogItemId != catalogItems.get(0).id, null);
	}
	
	public void run() throws Exception {
		testSpecificCase1();
	}
}
