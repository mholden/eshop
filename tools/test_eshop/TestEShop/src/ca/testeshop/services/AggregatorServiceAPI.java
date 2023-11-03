package ca.testeshop.services;

import ca.testeshop.utils.*;

public interface AggregatorServiceAPI {
	public EShopResponse getBasket() throws Exception;
	public EShopResponse getCatalogBrands() throws Exception;
	public EShopResponse getCatalogTypes() throws Exception;
	public EShopResponse getCatalogItems(Integer pageIndex, Integer pageSize) throws Exception;
	public EShopResponse setBasket(Basket basket) throws Exception;
}
