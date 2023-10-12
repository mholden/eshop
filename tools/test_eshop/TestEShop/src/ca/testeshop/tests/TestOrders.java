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
		PagenatedItems<CatalogItem> catalogItems;
		CatalogItem catalogItem;
		List<OrderSummary> orders;
		Order order;
		CustomerBasket basket;
		EShopResponse response;
		
		System.out.println("\ntestSpecificCase1");
		
		response = doUserLogin("demouser@microsoft.com", "Pass@word1");
		userInfo = ((UserInfo)JsonUtils.jsonToPojo(response.response, UserInfo.class));
		
		response = aggregatorService.getCatalogItems(0, 12);
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
		
		catalogItems = (PagenatedItems<CatalogItem>)JsonUtils.jsonToPojo(response.response, new TypeToken<PagenatedItems<CatalogItem>>(){}.getType());
		//System.out.println(catalogItems);
		
		response = aggregatorService.getBasket();
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
		
		basket = (CustomerBasket)JsonUtils.jsonToPojo(response.response, CustomerBasket.class);
		//System.out.println(basket);
		
		catalogItem = catalogItems.data.get(0);
		
		basket.items.clear();
		basket.items.add(new BasketItem(catalogItem));
		//System.out.println(basket);
		
		response = aggregatorService.setBasket(basket);
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
	}
	
	public void run() throws Exception {
		testSpecificCase1();
	}
}
