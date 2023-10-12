package ca.testeshop.services;

import ca.testeshop.utils.*;

public interface CatalogServiceAPI {
	public EShopResponse getCatalogBrands(String authToken) throws Exception;
}
