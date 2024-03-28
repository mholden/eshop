package ca.testeshop.services;

import ca.testeshop.utils.*;

public interface CatalogServiceAPI {
	public EShopResponse getCatalogItems(Integer pageIndex, Integer pageSize) throws Exception;
}
