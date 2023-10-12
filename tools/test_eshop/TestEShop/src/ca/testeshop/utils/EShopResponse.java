package ca.testeshop.utils;

public class EShopResponse {
	
	public int httpCode;
	public String redirectLocation;
	public String response;
	
	public EShopResponse() {
		
	}
	
	public void dump() throws Exception {
		System.out.println("httpCode: " + httpCode);
		if (redirectLocation != null) {
			System.out.println("redirectLocation: " + redirectLocation);
		}
		System.out.println("response: ");
		System.out.println(response);
	}
	
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("httpCode: " + httpCode);
		if (redirectLocation != null) {
			stringBuilder.append("\nredirectLocation: " + redirectLocation);
		}
		if (response != null) {
			stringBuilder.append("\nresponse: " + response);
		}
		return stringBuilder.toString();
	}
}
