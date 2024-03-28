package ca.testeshop.tests;

import java.net.HttpURLConnection;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import ca.testeshop.services.*;
import ca.testeshop.utils.CatalogItem;
import ca.testeshop.utils.EShopResponse;
import ca.testeshop.utils.JsonUtils;
import ca.testeshop.utils.TestUtils;

public class TestHomePage extends Test {
	
	public TestHomePage() {
		
	}
	
	public TestHomePage(String username, String password, Services services) {
		super(username, password, services);
	}
	
	private void testSpecificCase1() throws Exception {
		List<CatalogItem> catalogItems;
		EShopResponse response;
		
		System.out.println("\ntestSpecificCase1");
		
		response = catalogService.getCatalogItems(0, 12);
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
		//response.dump();
		
		catalogItems = (List<CatalogItem>)JsonUtils.jsonToPojo(response.response, new TypeToken<List<CatalogItem>>(){}.getType());
		TestUtils.failIf(catalogItems.isEmpty(), null);
	}
	
	public void run() throws Exception {
		testSpecificCase1();
	}
}
