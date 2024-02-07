package ca.testeshop.tests;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.UUID;

import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.jwt.Jwt;

import com.google.gson.reflect.TypeToken;

import ca.testeshop.services.*;
import ca.testeshop.utils.*;

public abstract class Test {
	
	private ThreadLocal<String> username = new ThreadLocal<String>();
	private ThreadLocal<String> password = new ThreadLocal<String>();
	private ThreadLocal<String> userId = new ThreadLocal<String>();
	
	// Services
	public IdentityService identityService;
	public CatalogService catalogService;
	public BasketService basketService;
	public OrderService orderService;
	// TODO: add payment service?
	public NotificationService notificationService;
	public ContentService contentService;
	
	public ThreadLocal<AsyncChannel> asyncChannel = new ThreadLocal<AsyncChannel>();
	
	public Test() {
		
	}
	
	public Test(String username, String password, Services services) {
		this.username.set(username);
		this.password.set(password);
		this.identityService = services.identityService;
		this.catalogService = services.catalogService;
		this.basketService = services.basketService;
		this.orderService = services.orderService;
		this.notificationService = services.notificationService;
		this.contentService = services.contentService;
	}
	
	public String getUserName() {
		return username.get();
	}
	
	public void setUserName(String username) {
		this.username.set(username);
	}
	
	public String getPassword() {
		return password.get();
	}
	
	public void setPassword(String password) {
		this.password.set(password);
	}
	
	public String getUserId() {
		return userId.get();
	}
	
	public void setUserId(String userId) {
		this.userId.set(userId);
	}
	
	public void doUserLogout() throws Exception {
		EShopResponse response;
		
		response = identityService.logout();
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
		
		HttpUtils.cookieManager.get().getCookieStore().removeAll();
		HttpUtils.authToken.set(null);
		userId.set(null);
		username.set(null);
		password.set(null);
		// TODO: cut off async channel?
	}
	
	public EShopResponse doUserLogin(String email, String password) throws Exception {
		String url = null;
		StringTokenizer st;
		Map<String, Object> jwtClaims;
		EShopResponse response;
		
		doUserLogout();
		
		response = identityService.authorize();
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
		//response.dump();
		
		// get auth url from output:
		Scanner scanner = new Scanner(response.response);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (line.contains("login-actions/authenticate")) {
				// System.out.println(line);
				st = new StringTokenizer(line, "\"");
				String last = "", next;
				while (st.hasMoreTokens()) {
					next = st.nextToken();
					if (last.contains("action")) {
						url = next.replaceAll("&amp;", "&");
						break;
					}
					last = next;
				}
				break;
			}
		}
		scanner.close();

		//System.out.println("url is " + url);
		
		response = identityService.authenticate(url, email, password);
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString()); // should get 200 here
		//response.dump();
		
		response = identityService.getUserInfo();
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
		//response.dump();
		
		jwtClaims = (Map<String, Object>)JsonUtils.jsonToPojo(response.response, new TypeToken<Map<String, Object>>(){}.getType());
		userId.set((String)jwtClaims.get("sub"));
		//System.out.println("userId is " + userId.get());
		
		asyncChannel.set(new AsyncChannel(notificationService.urlBase, userId.get()));
		
		return response;
	}
	
	public void doUserRegistration(String email, String password) throws Exception {
		String url = null;
		StringTokenizer st;
		Map<String, Object> jwtClaims;
		EShopResponse response;
		
		doUserLogout();
		
		response = identityService.authorize();
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
		//response.dump();
		
		// get registration url from output
		Scanner scanner = new Scanner(response.response);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (line.contains("login-actions/registration")) {
				// System.out.println(line);
				st = new StringTokenizer(line, "\"");
				String last = "", next;
				while (st.hasMoreTokens()) {
					next = st.nextToken();
					if (last.contains("href")) {
						url = "http://docker.for.mac.localhost:8090" + next.replaceAll("&amp;", "&"); // hardcoding the hostname for now..
						break;
					}
					last = next;
				}
				break;
			}
		}
		scanner.close();
		
		//System.out.println("url is " + url);
		
		response = identityService.register(url, email, password);
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString()); // should get 200 here
		//response.dump();
		
		response = identityService.getUserInfo();
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
		
		jwtClaims = (Map<String, Object>)JsonUtils.jsonToPojo(response.response, new TypeToken<Map<String, Object>>(){}.getType());
		userId.set((String)jwtClaims.get("sub"));
		//System.out.println("userId is " + userId.get());
		
		asyncChannel.set(new AsyncChannel(notificationService.urlBase, userId.get()));
	}
	
	public void doUserRegistration() throws Exception {
		String email, password;
		
		email = "testUser-" + UUID.randomUUID().toString().split("-")[4] + "@testeshop.com";
		password = TestUtils.defaultPassword;
		
		// TODO: make this a loop that tries again if username already exists
		/*
		 while (retries < 5 && response.httpCode != HttpURLConnection.HTTP_OK && response.toString().contains("exists")) {
		 	// try again
		 	retries++;
		 }
		 */
		doUserRegistration(email, password);
		
		this.username.set(email);
		this.password.set(password);
	}
	
	public abstract void run() throws Exception;
}
