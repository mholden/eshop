package ca.testeshop.services;

import ca.testeshop.utils.*;

public class CatalogService extends Service implements CatalogServiceAPI {

	public CatalogService() {
		
	}
	
	public CatalogService(String urlbase) {
		super(urlbase);
	}
	
	private HttpUtils.HttpPayload buildGetCatalogBrandsPayload(String authToken) {
		HttpUtils.HttpPayload payload = new HttpUtils.HttpPayload();
		StringBuilder stringBuilder = new StringBuilder();

		payload.type = HttpUtils.HttpPayload.types.JSON;
        payload.data = stringBuilder.toString();
        
        return payload;
	}
	
	public EShopResponse getCatalogBrands(String authToken) throws Exception {
		return HttpUtils.doGet(urlBase + "/catalog-api/api/v1/catalog/catalogbrands", buildGetCatalogBrandsPayload(authToken));
	}
	
}
