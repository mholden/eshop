package ca.testeshop.tests;

import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.reflect.TypeToken;

import ca.testeshop.services.*;
import ca.testeshop.utils.*;

public class TestCatalogCRUD extends Test {
	
	public TestCatalogCRUD() {
		
	}
	
	public TestCatalogCRUD(String username, String password, Services services) {
		super(username, password, services);
	}
	
	private void testSpecificCase1() throws Exception {
		String fileName, imageId;
		List<CatalogItem> catalogItems;
		CatalogItem catalogItem;
		Content content;
		List<Content> downloadedContent;
		EShopResponse response;
		
		System.out.println("\ntestSpecificCase1");
		
		doUserLogin("admin@testeshop.ca", "admin");
		
		fileName = "/TestCatalogCRUD/cute-cat.png";
		content = new Content(fileName);
		response = contentService.uploadContent(content);
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK || response.response == null, response.toString());
		
		imageId = response.response;
		response = catalogService.createCatalogItem(new CatalogItem("TestCatalogCRUD-testSpecificCase1-item", 599, imageId));
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
		
		response = catalogService.getCatalogItems();
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
		
		catalogItems = (List<CatalogItem>)JsonUtils.jsonToPojo(response.response, new TypeToken<List<CatalogItem>>(){}.getType());
		//System.out.println(catalogItems);
		
		catalogItem = null;
		for (CatalogItem _catalogItem : catalogItems) {
			if (imageId.equals(_catalogItem.imageId)) {
				catalogItem = _catalogItem;
				break;
			}
		}
		TestUtils.failIf(catalogItem == null, null);
		
		response = contentService.downloadContent(catalogItem.imageId);
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
		
		//response.dump();
		
		// verify data integrity
		downloadedContent = (List<Content>)JsonUtils.jsonToPojo(response.response, new TypeReference<List<Content>>(){});
		TestUtils.failIf(downloadedContent.size() != 1, imageId);
		TestUtils.failIf(!Arrays.equals(downloadedContent.get(0).getData(), content.getData()), null);
	}
	
	public void run() throws Exception {
		testSpecificCase1();
	}
}
