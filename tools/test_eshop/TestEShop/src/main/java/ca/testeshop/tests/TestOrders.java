package ca.testeshop.tests;

import java.net.HttpURLConnection;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

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
		//OidcUserInfo userInfo;
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
		
		asyncChannel.get().waitFor("OrderPaymentSucceededNotification", Duration.ofSeconds(5));
		
		response = orderService.getOrders();
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
		
		orders = (List<Order>)JsonUtils.jsonToPojo(response.response, new TypeToken<List<Order>>(){}.getType());
		//System.out.println(orders);
		TestUtils.failIf(orders.size() != 1, null);
		
		order = orders.get(0);
		TestUtils.failIf(order.state != OrderState.PAYMENT_SUCCEEDED, null);
	}
	
	private void testMT() throws Exception {
		List<Thread> threads;
		ConcurrentLinkedQueue<Exception> exceptions = new ConcurrentLinkedQueue<Exception>();
		Thread thread;
		
		System.out.println("\ntestMT");
		
		threads = new LinkedList<Thread>();
		for (int i = 0; i < 4; i++) {
			thread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						testSpecificCase1();
					} catch (Exception e) {
						exceptions.add(e);
					}
				}
			});
			threads.add(thread);
			thread.start();
		}

		for (int i = 0; i < threads.size(); i++) {
			threads.get(i).join();
		}

		if (exceptions.size() != 0) {
			System.out.println("caught " + exceptions.size() + " exceptions");
			for (Exception e : exceptions) {
				e.printStackTrace();
			}
			throw new Exception("One or more thread(s) threw exception(s)");
		}
	}
	
	public void run() throws Exception {
		testSpecificCase1();
		testMT();
	}
}
