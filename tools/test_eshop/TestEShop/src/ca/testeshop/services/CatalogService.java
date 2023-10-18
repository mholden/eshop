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
}
