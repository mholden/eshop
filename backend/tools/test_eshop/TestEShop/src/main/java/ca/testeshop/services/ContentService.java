package ca.testeshop.services;

import java.util.Arrays;
import java.util.List;

import ca.testeshop.utils.*;

public class ContentService extends Service implements ContentServiceAPI {

	public ContentService() {
		
	}
	
	public ContentService(String urlbase) {
		super(urlbase);
	}
	
	private HttpUtils.HttpPayload buildUploadContentPayload(Content content) throws Exception {
		HttpUtils.HttpPayload payload = new HttpUtils.HttpPayload();
		
		payload.type = HttpUtils.HttpPayload.types.JSON;
		payload.data = JsonUtils.pojoToJson(content);
		//payload.dump();
		
		return payload;
	}
	
	public EShopResponse uploadContent(Content content) throws Exception {
		return HttpUtils.doPost(urlBase + "/content", buildUploadContentPayload(content)); 
	}
	
	public EShopResponse downloadContent(List<String> contentIds) throws Exception {
		String urlSuffix = "?contentId=" + contentIds.get(0);
		for (int i = 1; i < contentIds.size(); i++) {
			urlSuffix += "&contentId=" + contentIds.get(i);
		}
		//System.out.println("urlSuffix is " + urlSuffix);
		return HttpUtils.doGet(urlBase + "/content" + urlSuffix);
	}
	
	public EShopResponse downloadContent(String contentId) throws Exception {
		return downloadContent(Arrays.asList(contentId));
	}
}
