package ca.testeshop.services;

import ca.testeshop.tests.Test;
import ca.testeshop.utils.*;

public class AggregatorService extends Service implements AggregatorServiceAPI {

	public AggregatorService() {
		
	}
	
	public AggregatorService(String urlbase) {
		super(urlbase);
	}
	
	private HttpUtils.HttpPayload buildCheckoutPayload(BasketCheckout basketCheckout) throws Exception {
		HttpUtils.HttpPayload payload = new HttpUtils.HttpPayload();
		payload.type = HttpUtils.HttpPayload.types.JSON;
        payload.data = JsonUtils.pojoToJson(basketCheckout);
        //payload.dump();
        return payload;
	}
	
	public EShopResponse checkout(BasketCheckout basketCheckout) throws Exception {
		return HttpUtils.doPost(urlBase + "/b/api/v1/basket/checkout", buildCheckoutPayload(basketCheckout));
	}
	
	public EShopResponse getBasket() throws Exception {
		return HttpUtils.doGet(urlBase + "/b/api/v1/basket/" + Test.userId.get());
	}
	
	public EShopResponse getCatalogBrands() throws Exception {
		return HttpUtils.doGet(urlBase + "/c/api/v1/catalog/catalogbrands");
	}
	
	public EShopResponse getCatalogTypes() throws Exception {
		return HttpUtils.doGet(urlBase + "/c/api/v1/catalog/catalogtypes");
	}
	
	public EShopResponse getCatalogItems(Integer pageIndex, Integer pageSize) throws Exception {
		return HttpUtils.doGet(urlBase + "/c/api/v1/catalog/items" + "?pageIndex=" + pageIndex + "&pageSize=" + pageSize);
	}
	
	public EShopResponse getOrder(Integer orderNumber) throws Exception {
		return HttpUtils.doGet(urlBase + "/o/api/v1/orders/" + orderNumber);
	}
	
	public EShopResponse getOrders() throws Exception {
		return HttpUtils.doGet(urlBase + "/o/api/v1/orders");
	}
	
	private HttpUtils.HttpPayload buildSetBasketPayload(Basket basket) {
		HttpUtils.HttpPayload payload = new HttpUtils.HttpPayload();
		payload.type = HttpUtils.HttpPayload.types.JSON;
        payload.data = JsonUtils.pojoToJson(basket);
        return payload;
	}
	
	public EShopResponse setBasket(Basket basket) throws Exception {
		return HttpUtils.doPost(urlBase + "/b/api/v1/basket", buildSetBasketPayload(basket));
	}
}
