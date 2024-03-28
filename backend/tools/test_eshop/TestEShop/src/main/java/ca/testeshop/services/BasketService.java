package ca.testeshop.services;

import java.util.List;

import ca.testeshop.utils.*;

public class BasketService extends Service implements BasketServiceAPI {

	public BasketService() {

	}

	public BasketService(String urlbase) {
		super(urlbase);
	}

	public EShopResponse checkout() throws Exception {
		return HttpUtils.doPost(urlBase + "/basket/checkout");
	}

	public EShopResponse getBasketItems() throws Exception {
		return HttpUtils.doGet(urlBase + "/basket/items");
	}

	private HttpUtils.HttpPayload buildSetBasketItemsPayload(List<BasketItem> basketItems) {
		HttpUtils.HttpPayload payload = new HttpUtils.HttpPayload();
		payload.type = HttpUtils.HttpPayload.types.JSON;
		payload.data = JsonUtils.pojoToJson(basketItems);
		return payload;
	}

	public EShopResponse setBasketItems(List<BasketItem> basketItems) throws Exception {
		return HttpUtils.doPost(urlBase + "/basket/items", buildSetBasketItemsPayload(basketItems));
	}

	public EShopResponse doPing() throws Exception {
		return HttpUtils.doGet(urlBase + "/basket/ping");
	}
}
