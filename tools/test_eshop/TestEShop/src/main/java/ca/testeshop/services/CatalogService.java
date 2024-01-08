package ca.testeshop.services;

import ca.testeshop.utils.*;

public class CatalogService extends Service implements CatalogServiceAPI {

	public CatalogService() {
		
	}
	
	public CatalogService(String urlbase) {
		super(urlbase);
	}
	
	public EShopResponse getCatalogItems(Integer pageIndex, Integer pageSize) throws Exception {
		return HttpUtils.doGet(urlBase + "/catalog/items" + "?pageIndex=" + pageIndex + "&pageSize=" + pageSize);
	}
	
	public EShopResponse getCatalogItems() throws Exception {
		return HttpUtils.doGet(urlBase + "/catalog/items");
	}
	
	private HttpUtils.HttpPayload buildCreateCatalogItemPayload(CatalogItem catalogItem) throws Exception {
		HttpUtils.HttpPayload payload = new HttpUtils.HttpPayload();
		
		payload.type = HttpUtils.HttpPayload.types.JSON;
		payload.data = JsonUtils.pojoToJson(catalogItem);
		//payload.dump();
		
		return payload;
	}

	public EShopResponse createCatalogItem(CatalogItem catalogItem) throws Exception {
		return HttpUtils.doPost(urlBase + "/catalog/item", buildCreateCatalogItemPayload(catalogItem));
	}
}
