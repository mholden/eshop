package ca.testeshop.tests;

import java.net.HttpURLConnection;
import java.util.*;

import com.google.gson.reflect.TypeToken;

import ca.testeshop.services.*;
import ca.testeshop.utils.*;

public class TestOrders extends Test {
	
	public TestOrders() {
		
	}
	
	public TestOrders(String username, String password, Services services) {
		super(username, password, services);
	}
	
	// test ordering a single item
	private void testSpecificCase1() throws Exception {
		UserInfo userInfo;
		List<CatalogItem> catalogItems;
		CatalogItem catalogItem;
		List<OrderSummary> orders;
		Order order;
		Basket basket;
		List<BasketItem> basketItems;
		EShopResponse response;
		
		System.out.println("\ntestSpecificCase1");
		
		doUserLogin("alice", "alice");
		
		response = basketService.checkout();
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
		
		/*
		response = aggregatorService.getCatalogItems(0, 12);
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
		
		catalogItems = (List<CatalogItem>)JsonUtils.jsonToPojo(response.response, new TypeToken<List<CatalogItem>>(){}.getType());
		//System.out.println(catalogItems);
		
		response = basketService.getBasketItems();
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
		//response.dump();
		
		basketItems = (List<BasketItem>)JsonUtils.jsonToPojo(response.response, new TypeToken<List<BasketItem>>(){}.getType());
		System.out.println(basketItems);
		
		basketItems.clear();
		basketItems.add(new BasketItem(catalogItems.get(0)));
		System.out.println(basketItems);
		
		response = basketService.setBasketItems(basketItems);
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
		
		response = aggregatorService.checkout(new BasketCheckout(userInfo));
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_ACCEPTED, response.toString());
		
		response = aggregatorService.getOrders();
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
		
		orders = (List<OrderSummary>)JsonUtils.jsonToPojo(response.response, new TypeToken<List<OrderSummary>>(){}.getType());
		//System.out.println(orders);
		
		for (OrderSummary orderSummary : orders) {
			response = aggregatorService.getOrder(orderSummary.ordernumber);
			TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
			
			order = (Order)JsonUtils.jsonToPojo(response.response, Order.class);
			System.out.println(order);
		}
		*/
	}
	
	public void run() throws Exception {
		testSpecificCase1();
	}
}
