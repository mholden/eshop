package ca.testeshop.tests;

import java.net.HttpURLConnection;
import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.reflect.TypeToken;

import ca.testeshop.services.*;
import ca.testeshop.utils.*;

public class TestContent extends Test {
	
	public TestContent() {
		
	}
	
	public TestContent(String username, String password, Services services) {
		super(username, password, services);
	}
	
	// test bulk download
	private void testSpecificCase1() throws Exception {
		List<CatalogItem> catalogItems;
		List<String> contentIds;
		List<Content> content;
		EShopResponse response;
		
		System.out.println("\ntestSpecificCase1");
		
		doUserLogin("alice@testeshop.ca", "alice");
		
		response = catalogService.getCatalogItems(0, 12);
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
		
		catalogItems = (List<CatalogItem>)JsonUtils.jsonToPojo(response.response, new TypeToken<List<CatalogItem>>(){}.getType());
		//System.out.println(catalogItems);
		
		//response.dump();
		
		contentIds = new LinkedList<String>();
		for (CatalogItem catalogItem : catalogItems) {
			if (catalogItem.imageId != null) {
				contentIds.add(catalogItem.imageId);
			}
		}
		
		response = contentService.downloadContent(contentIds);
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
		
		//response.dump();
		
		content = (List<Content>)JsonUtils.jsonToPojo(response.response, new TypeReference<List<Content>>(){});
		//System.out.println(content);
		TestUtils.failIf(content.size() != contentIds.size(), null);
	}
	
	public void run() throws Exception {
		testSpecificCase1();
	}
}
