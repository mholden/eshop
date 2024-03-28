package ca.testeshop.services;

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
	
	public EShopResponse downloadContent(String contentId) throws Exception {
		return HttpUtils.doGet(urlBase + "/content" + "?contentId=" + contentId);
	}
}
