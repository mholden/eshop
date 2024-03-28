package ca.testeshop.utils;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.*;
import java.net.CookieManager;
import java.net.HttpCookie;

public class HttpUtils {
	//static final CookieManager cookieManager = new CookieManager();
	static public final ThreadLocal<CookieManager> cookieManager = new ThreadLocal<CookieManager>() {
		@Override
		protected synchronized CookieManager initialValue() {
			return (new CookieManager());
		}
	};
	static public ThreadLocal<String> authToken = new ThreadLocal<String>();
	static public ThreadLocal<Integer> nRedirects = new ThreadLocal<Integer>() {
		@Override
		protected synchronized Integer initialValue() {
			return 0;
		}
	};
	static public ThreadLocal<Boolean> disableRedirects = new ThreadLocal<Boolean>() {
		@Override
		protected synchronized Boolean initialValue() {
			return false;
		}
	};

	public HttpUtils() {

	}
	
	static public class HttpPayload {
		public static enum types {
			JSON("application/json"),
			XML("application/xml"),
			XWWWFORMURLENCODED("application/x-www-form-urlencoded"),
			MULTIPARTFORMDATA("multipart/form-data");
			
			private String value;
			private types(String value) {
				this.value = value;
			}
			public String toString() {
				return this.value;
			}
		}
		
		public HttpPayload.types type;
		public String data;
		
		public void dump() throws Exception {
			System.out.println(type);
			System.out.println(data);
		}
	}
	
	private static Boolean isHttpErrorCode(int httpCode) {
		return httpCode >= 400;
	}
	
	private static void saveCookies(HttpURLConnection connection) throws Exception { 
		Map<String, List<String>> headerFields = connection.getHeaderFields();
		List<String> cookiesHeader = headerFields.get("Set-Cookie");

		if (cookiesHeader != null) {
		    for (String cookie : cookiesHeader) {
		    	//System.out.println("saving cookie " + HttpCookie.parse(cookie).get(0));
		    	cookieManager.get().getCookieStore().add(null,HttpCookie.parse(cookie).get(0));
		    }               
		}
	}
	
	public static EShopResponse doGet(String url) throws Exception {
		return doGet(url, null, false, null);
	}
	
	public static EShopResponse doGet(String url, HashMap<String, String> requestProps) throws Exception {
		return doGet(url, null, false, requestProps);
	}
	
	public static EShopResponse doGet(String url, HttpPayload payload) throws Exception {
		return doGet(url, payload, false, null);
	}
	
	public static EShopResponse doGet(String url, boolean noRedirect) throws Exception {
		return doGet(url, null, noRedirect, null);
	}

	public static EShopResponse doGet(String url, HttpPayload payload, boolean noRedirect, HashMap<String, String> requestProps) throws Exception {
		EShopResponse output = new EShopResponse();

		//System.out.println("doGet() url " + url);

		URL _url = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) _url.openConnection();

		// Set timeout as per needs
		// connection.setConnectTimeout(20000);
		// connection.setReadTimeout(40000);

		// Set DoOutput to true if you want to use URLConnection for output.
		// Default is false
		// connection.setDoOutput(true);

		// connection.setUseCaches(false);
		connection.setRequestMethod("GET");

		if (authToken.get() != null) {
			connection.setRequestProperty("Authorization", "Bearer " + authToken.get()); // hardcoding 'Bearer' for now (token_type in authorizeCallback response)
		}

		// HttpURLConnection.setFollowRedirects(false);
		// if (noRedirect) {
		connection.setInstanceFollowRedirects(false);
		// }

		// Set Headers
		// connection.setRequestProperty("Accept", payload.type.toString());
		connection.setRequestProperty("Accept", "*/*");
		// connection.setRequestProperty("Content-Type", payload.type.toString());
		if (cookieManager.get().getCookieStore().getCookies().size() > 0) {
			// While joining the Cookies, use ',' or ';' as needed. Most of the servers are
			// using ';'
			List<HttpCookie> cookieList = cookieManager.get().getCookieStore().getCookies();
			List<String> strings = new ArrayList<>(cookieList.size());
			for (Object object : cookieList) {
				if (Objects.toString(object, null).contains("JSESSION")) {
					System.out.println("doGet() using " + Objects.toString(object, null));				
				}
				strings.add(Objects.toString(object, null));
			}
			connection.setRequestProperty("Cookie", String.join(";", strings));
			// System.out.println("doGet() thread " + Thread.currentThread().getId() + " using cookies " + strings);
		}

		if (requestProps != null) {
			for (Map.Entry<String, String> requestProp : requestProps.entrySet()) {
				connection.setRequestProperty(requestProp.getKey(), requestProp.getValue());
			}
		}

		// connection.setRequestProperty("Host", "docker.for.mac.localhost:5121");
		// System.out.println(connection.getRequestProperties());

		// payload.dump();

		// Write payload
		/*
		 * OutputStream outputStream = connection.getOutputStream(); byte[] b =
		 * payload.data.getBytes("UTF-8"); outputStream.write(b); outputStream.flush();
		 * outputStream.close();
		 */

		// Read response
		connection.getResponseCode(); // this needs to be called first for the error stream logic below to work
		InputStream inputStream = connection.getErrorStream();
		if (inputStream == null) {
			inputStream = connection.getInputStream();
		}
		byte[] res = new byte[2048];
		int i = 0;
		StringBuilder response = new StringBuilder();
		while ((i = inputStream.read(res)) != -1) {
			response.append(new String(res, 0, i));
		}
		inputStream.close();

		saveCookies(connection);

		// System.out.println("Response= " + response.toString());

		output = new EShopResponse();
		output.httpCode = connection.getResponseCode();
		if (output.httpCode == HttpURLConnection.HTTP_MOVED_TEMP
				|| output.httpCode == HttpURLConnection.HTTP_MOVED_PERM) {
			output.redirectLocation = connection.getHeaderFields().get("Location").get(0);
			if (!noRedirect && !disableRedirects.get()) { // follow it
				if (output.redirectLocation.startsWith("/")) {
					output.redirectLocation = _url.getProtocol() + "://" + _url.getHost() + ":" + _url.getPort() + output.redirectLocation;
					//System.out.println("doGet() adjusted redirectLocation to " + output.redirectLocation);
				}
				//System.out.println("doGet() redirecting " + url + " to " + output.redirectLocation);
				nRedirects.set(nRedirects.get() + 1);
				return doGet(output.redirectLocation, noRedirect);
			}
		}
		output.response = response.toString();

		return output;
	}

	public static EShopResponse doPost(String url) throws Exception {
		return doPost(url, null, false);
	}

	public static EShopResponse doPost(String url, HttpPayload payload) throws Exception {
		return doPost(url, payload, false);
	}

	public static EShopResponse doPost(String url, HttpPayload payload, boolean noRedirect) throws Exception {
		EShopResponse output = new EShopResponse();

		//System.out.println("doPost() url " + url);

		URL _url = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) _url.openConnection();

		connection.setRequestMethod("POST");

		if (authToken.get() != null) {
			connection.setRequestProperty("Authorization", "Bearer " + authToken.get()); // hardcoding 'Bearer' for now (token_type in authorizeCallback response)
		}

		// if (noRedirect) {
		connection.setInstanceFollowRedirects(false);
		// }
		connection.setDoOutput(true);

		// Set Headers
		// connection.setRequestProperty("Accept", payload.type.toString());
		connection.setRequestProperty("Accept", "*/*");
		if (payload != null)
			connection.setRequestProperty("Content-Type", payload.type.toString());
		// connection.setRequestProperty("Content-Length",
		// String.valueOf(payload.data.length())); // doesn't work

		if (cookieManager.get().getCookieStore().getCookies().size() > 0) {
			// While joining the Cookies, use ',' or ';' as needed. Most of the servers are
			// using ';'
			List<HttpCookie> cookieList = cookieManager.get().getCookieStore().getCookies();
			List<String> strings = new ArrayList<>(cookieList.size());
			for (Object object : cookieList) {
				if (Objects.toString(object, null).contains("JSESSION")) {
					System.out.println("doPost() using " + Objects.toString(object, null));				
				}
				strings.add(Objects.toString(object, null));
			}
			connection.setRequestProperty("Cookie", String.join(";", strings));
			// System.out.println("doPost() thread " + Thread.currentThread().getId() + " using cookies " + strings);
		}

		if (payload != null) {
			OutputStream outputStream = connection.getOutputStream();
			byte[] b = payload.data.getBytes("UTF-8");
			outputStream.write(b);
			outputStream.flush();
			outputStream.close();
		}

		// Read response
		output = new EShopResponse();
		output.httpCode = connection.getResponseCode(); 
		
		InputStream inputStream = connection.getErrorStream();
		if (inputStream == null) {
			if (isHttpErrorCode(output.httpCode)) {
				return output;
			}
			inputStream = connection.getInputStream();
		}
		byte[] res = new byte[2048];
		int i = 0;
		StringBuilder response = new StringBuilder();
		while ((i = inputStream.read(res)) != -1) {
			response.append(new String(res, 0, i));
		}
		inputStream.close();

		saveCookies(connection);

		// System.out.println("Response= " + response.toString());
		if (output.httpCode == HttpURLConnection.HTTP_MOVED_TEMP
				|| output.httpCode == HttpURLConnection.HTTP_MOVED_PERM) {
			output.redirectLocation = connection.getHeaderFields().get("Location").get(0);
			if (!noRedirect && !disableRedirects.get()) { // follow it
				//System.out.println("doPost() redirecting " + url + " to " + output.redirectLocation);
				if (output.redirectLocation.startsWith("/")) {
					output.redirectLocation = _url.getProtocol() + "://" + _url.getHost() + ":" + _url.getPort() + output.redirectLocation;
					//System.out.println("doGet() adjusted redirectLocation to " + output.redirectLocation);
				}
				nRedirects.set(nRedirects.get() + 1);
				return doPost(output.redirectLocation, payload, noRedirect);
			}
		}
		output.response = response.toString();

		return output;
	}
}
