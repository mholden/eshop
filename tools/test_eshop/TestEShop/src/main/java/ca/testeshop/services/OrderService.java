package ca.testeshop.services;

import ca.testeshop.utils.*;

public class OrderService extends Service implements OrderServiceAPI {

	public OrderService() {
		
	}
	
	public OrderService(String urlbase) {
		super(urlbase);
	}
	
	public EShopResponse getOrders() throws Exception {
		return HttpUtils.doGet(urlBase + "/order/orders");
	}
	
	public EShopResponse doPing() throws Exception {
		return HttpUtils.doGet(urlBase + "/order/ping");
	}
}
