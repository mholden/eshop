package ca.testeshop.services;

public class Services {
	public static enum location {
		LOCAL,
		DEV,
		STAGING,
		PROD
	}
	
	public IdentityService identityService;
	public CatalogService catalogService;
	public BasketService basketService;
	public OrderService orderService;
	// TODO: add payment service?
	public NotificationService notificationService;
	
	public Services() {
		
	}
	
	public Services(Services.location location) throws Exception {
		if (!(location == Services.location.LOCAL))
			throw new Exception("Non-local services not yet supported");
		
		identityService = new IdentityService("http://localhost:5122"); // just using basket service for now
		catalogService = new CatalogService("http://localhost:5121");
		basketService = new BasketService("http://localhost:5122");
		orderService = new OrderService("http://localhost:5123");
		// TODO: add payment service?
		notificationService = new NotificationService("ws://localhost:5125/ws");
	}
}
