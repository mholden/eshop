package ca.testeshop.tests;

import java.net.HttpURLConnection;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.UUID;

import ca.testeshop.services.*;
import ca.testeshop.utils.*;

public abstract class Test {
	
	private ThreadLocal<String> username = new ThreadLocal<String>();
	private ThreadLocal<String> password = new ThreadLocal<String>();
	
	// Services
	public AggregatorService aggregatorService;
	public IdentityService identityService;
	public CatalogService catalogService;
	public BasketService basketService;
	
	//public String riaAsyncToken;
	public static ThreadLocal<String> userId = new ThreadLocal<String>();
	
	public Test() {
		
	}
	
	public Test(String username, String password, Services services) {
		this.username.set(username);
		this.password.set(password);
		this.aggregatorService = services.aggregatorService;
		this.identityService = services.identityService;
		this.catalogService = services.catalogService;
		this.basketService = services.basketService;
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
	
	public void doUserLogout() throws Exception {
		EShopResponse response;
		
		if (userId.get() == null) { // not logged in
			return;
		}
		
		response = identityService.endSession();
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
		
		HttpUtils.cookieManager.get().getCookieStore().removeAll();
		HttpUtils.authToken.set(null);
		userId.set(null);
		username.set(null);
		password.set(null);
	}
	
	public EShopResponse doUserLogin(String email, String password) throws Exception {
		String url = null;
		StringTokenizer st;
		EShopResponse response;
		
		//doUserLogout();
		
		response = identityService.authorize();
		//response.dump();
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
		
		// get __RequestVerificationToken from output:
		Scanner scanner = new Scanner(response.response);
		while (scanner.hasNextLine()) {
		  String line = scanner.nextLine();
		  if (line.contains("kc-form-login")) {
			  //System.out.println(line);
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
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_NOT_FOUND, response.toString()); // should get 404 here
		//response.dump();
	
		return response;
	}
	
	public void doUserRegistration(String email, String password) throws Exception {
		String token = null;
		StringTokenizer st;
		EShopResponse response;
		
		doUserLogout();
		
		response = identityService.getRegistrationForm();
		//response.dump();
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
		
		// get __RequestVerificationToken from output:
		Scanner scanner = new Scanner(response.response);
		while (scanner.hasNextLine()) {
		  String line = scanner.nextLine();
		  if (line.contains("__RequestVerificationToken")) {
			  //System.out.println(line);
			  st = new StringTokenizer(line, "\"");  
			  String last = "", next;
			  while (st.hasMoreTokens()) {  
				  next = st.nextToken();
				  if (last.contains("value")) {
					  token = next;
					  break;
				  }
				  last = next;
			  }  
			  break;
		  }
		}
		scanner.close();
		
		//System.out.println("token is " + token);
		
		response = identityService.register(email, password, token);
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
		//response.dump();
	}
	
	public EShopResponse doUserRegistrationAndLogin(String email, String password) throws Exception {
		doUserRegistration(email, password);
		return doUserLogin(email, password);
	}
	
	public EShopResponse doUserRegistrationAndLogin() throws Exception {
		doUserRegistration();
		return doUserLogin(username.get(), password.get());
	}
	
	public void doUserRegistration() throws Exception {
		String email, password;
		
		email = "testUser-" + UUID.randomUUID().toString().split("-")[4] + "@testeshop.com";
		password = TestUtils.defaultPassword;
		
		// TODO: make this a loop that tries again if username already exists
		/*
		 while (retries < 5 && response.httpCode != HttpURLConnection.HTTP_MOVED_TEMP && response.toString().contains("exists")) {
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
