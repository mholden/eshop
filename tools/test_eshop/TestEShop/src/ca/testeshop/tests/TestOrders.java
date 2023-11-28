package ca.testeshop.tests;

import java.net.HttpURLConnection;
import java.util.*;
import java.util.concurrent.TimeUnit;

import com.google.gson.reflect.TypeToken;

import ca.testeshop.services.*;
import ca.testeshop.utils.*;
import ca.testeshop.utils.Order.OrderState;

public class TestOrders extends Test {
	
	public TestOrders() {
		
	}
	
	public TestOrders(String username, String password, Services services) {
		super(username, password, services);
	}
	
	// test ordering a single item
	private void testSpecificCase1() throws Exception {
		List<CatalogItem> catalogItems;
		CatalogItem catalogItem;
		List<Order> orders;
		Order order;
		Basket basket;
		List<BasketItem> basketItems;
		EShopResponse response;
		
		System.out.println("\ntestSpecificCase1");
		
		doUserRegistration();
		
		System.out.println("user: " + getUserName());
		
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
		
		response = basketService.checkout();
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
		
		// TODO: verify async notifications; should be getting updates for order status as it changes states
		
		// just wait a few seconds for now
		TimeUnit.SECONDS.sleep(3);
		
		response = orderService.getOrders();
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
		
		orders = (List<Order>)JsonUtils.jsonToPojo(response.response, new TypeToken<List<Order>>(){}.getType());
		//System.out.println(orders);
		TestUtils.failIf(orders.size() != 1, null);
		
		order = orders.get(0);
		TestUtils.failIf(order.state != OrderState.PAYMENT_SUCCEEDED, null);
	}
	
	public void run() throws Exception {
		testSpecificCase1();
	}
}
