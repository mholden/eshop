package ca.testeshop.services;

import ca.testeshop.tests.Test;
import ca.testeshop.utils.*;

public class BasketService extends Service implements BasketServiceAPI {

	public BasketService() {
		
	}
	
	public BasketService(String urlbase) {
		super(urlbase);
	}
	
	public EShopResponse getBasket() throws Exception {
		return HttpUtils.doGet(urlBase + "/basket/" + Test.userId.get());
	}
	
	public EShopResponse doPing() throws Exception {
		return HttpUtils.doGet(urlBase + "/basket/ping");
	}
}
