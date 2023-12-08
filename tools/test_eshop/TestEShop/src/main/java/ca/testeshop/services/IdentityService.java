package ca.testeshop.services;

import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.StringTokenizer;

import ca.testeshop.utils.*;

public class IdentityService extends Service implements IdentityServiceAPI {

	public IdentityService() {
		
	}
	
	public IdentityService(String urlbase) {
		super(urlbase);
	}
	
	public EShopResponse login() throws Exception {
		return HttpUtils.doGet(urlBase + "/login");
	}
	
	private HttpUtils.HttpPayload buildLogoutPayload(String token) throws Exception {
		HttpUtils.HttpPayload payload = new HttpUtils.HttpPayload();
		StringBuilder stringBuilder = new StringBuilder();
        
		stringBuilder.append("_csrf=" + token);

		payload.type = HttpUtils.HttpPayload.types.XWWWFORMURLENCODED;
        payload.data = stringBuilder.toString();
        
        //payload.dump();
        
        return payload;
	}
	
	public EShopResponse logout() throws Exception {
		StringTokenizer st;
		String csrfToken = null;
		EShopResponse response;
		
		response = HttpUtils.doGet(urlBase + "/logout");
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString()); 
		
		// grab the csrf token from the response
		Scanner scanner = new Scanner(response.response);
		while (scanner.hasNextLine()) {
		  String line = scanner.nextLine();
		  if (line.contains("_csrf")) {
			  //System.out.println(line);
			  st = new StringTokenizer(line, "\"");  
			  String last = "", next;
			  while (st.hasMoreTokens()) {  
				  next = st.nextToken();
				  if (last.contains("value")) {
					  csrfToken = next;
					  break;
				  }
				  last = next;
			  }  
			  break;
		  }
		}
		scanner.close();
		
		//System.out.println("token is " + csrfToken);
		
		response = HttpUtils.doPost(urlBase + "/logout", buildLogoutPayload(csrfToken), true);
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_MOVED_TEMP, response.toString()); // should get 302 here
		
		//System.out.println("redirect location is " + response.redirectLocation);
		
		response = HttpUtils.doGet(response.redirectLocation, true);
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_MOVED_TEMP, response.toString()); // should get 302 here
		
		//System.out.println("redirect location is " + response.redirectLocation);
		
		response = HttpUtils.doGet(response.redirectLocation, true);
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_MOVED_TEMP, response.toString()); // should get 302 here
		
		//System.out.println("redirect location is " + response.redirectLocation); // should take you back to oauth link
		
		return response;
	}
	
	private HttpUtils.HttpPayload buildAuthenticatePayload(String email, String password) throws Exception {
		HttpUtils.HttpPayload payload = new HttpUtils.HttpPayload();
		StringBuilder stringBuilder = new StringBuilder();
        
		stringBuilder.append("username=" + email + "&password=" + password + "&credentialId=");

		payload.type = HttpUtils.HttpPayload.types.XWWWFORMURLENCODED;
        payload.data = stringBuilder.toString();
        
        //payload.dump();
        
        return payload;
	}
	
	public EShopResponse authenticate(String url, String email, String password) throws Exception {
		EShopResponse response;
		
		response = HttpUtils.doPost(url, buildAuthenticatePayload(email, password), true);
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_MOVED_TEMP, response.toString()); // should get 302 here
		
		//System.out.println("redirect location is " + response.redirectLocation);
		
		response = HttpUtils.doGet(response.redirectLocation, true);
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_MOVED_TEMP, response.toString()); // should get 302 here
		
		//System.out.println("redirect location is " + response.redirectLocation);
		
		response = HttpUtils.doGet(response.redirectLocation, true);
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_NOT_FOUND, response.toString()); // should get 404 here
		
		return response;
	}
	
	public EShopResponse authorize() throws Exception {
		EShopResponse response;
		
		response = HttpUtils.doGet(urlBase + "/oauth2/authorization/keycloak", true);
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_MOVED_TEMP, response.toString());

		//System.out.println("redirect location is " + response.redirectLocation);
		
		// note: this requires '127.0.0.1 docker.for.mac.localhost' in your /etc/hosts file
		return HttpUtils.doGet(response.redirectLocation);
	}
	
	private HttpUtils.HttpPayload buildRegisterPayload(String email, String password) throws Exception {
		HttpUtils.HttpPayload payload = new HttpUtils.HttpPayload();
		StringBuilder stringBuilder = new StringBuilder();
        
		stringBuilder.append("firstName=Test");
		stringBuilder.append("&lastName=User");
		stringBuilder.append("&email=" + URLEncoder.encode(email, StandardCharsets.UTF_8.toString()));
		stringBuilder.append("&password=" + URLEncoder.encode(password, StandardCharsets.UTF_8.toString()));
		stringBuilder.append("&password-confirm=" + URLEncoder.encode(password, StandardCharsets.UTF_8.toString()));

		payload.type = HttpUtils.HttpPayload.types.XWWWFORMURLENCODED;
        payload.data = stringBuilder.toString();
        
        //payload.dump();
        
        return payload;
	}
	
	public EShopResponse register(String url, String email, String password) throws Exception {
		StringTokenizer st;
		String _url = null;
		EShopResponse response;
		
		response = HttpUtils.doGet(url);
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
		//response.dump();
		
		// get registration url from output
		Scanner scanner = new Scanner(response.response);
		while (scanner.hasNextLine()) {
		  String line = scanner.nextLine();
		  if (line.contains("login-actions/registration")) {
			  //System.out.println(line);
			  st = new StringTokenizer(line, "\"");  
			  String last = "", next;
			  while (st.hasMoreTokens()) {  
				  next = st.nextToken();
				  if (last.contains("action")) {
					  _url = next.replaceAll("&amp;", "&");
					  break;
				  }
				  last = next;
			  }  
			  break;
		  }
		}
		scanner.close();
		
		//System.out.println("_url is " + _url);
		
		response = HttpUtils.doPost(_url, buildRegisterPayload(email, password), true);
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_MOVED_TEMP, response.toString()); // should get 302 here
		
		//System.out.println("redirect location is " + response.redirectLocation);
		
		response = HttpUtils.doGet(response.redirectLocation, true);
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_MOVED_TEMP, response.toString()); // should get 302 here
		
		//System.out.println("redirect location is " + response.redirectLocation);
		
		response = HttpUtils.doGet(response.redirectLocation);
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_NOT_FOUND, response.toString()); // should get 404 here
		
		return response;
	}
	
	// TODO: change to identity/userInfo
	public EShopResponse getUserInfo() throws Exception {
		return HttpUtils.doGet(urlBase + "/basket/userInfo");
	}
}
