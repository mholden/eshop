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
	public ContentService contentService;
	
	public Services() {
		
	}
	
	public Services(Services.location location) throws Exception {
		if (!(location == Services.location.LOCAL))
			throw new Exception("Non-local services not yet supported");
		
		/*
		identityService = new IdentityService("http://docker.for.mac.localhost:8090");
		catalogService = new CatalogService("http://localhost:8080"); // mapped to http://localhost:5121 via gateway
		basketService = new BasketService("http://localhost:8080"); // mapped to http://localhost:5122 via gateway 
		orderService = new OrderService("http://localhost:8080"); // mapped to http://localhost:5123 via gateway
		// TODO: add payment service?
		notificationService = new NotificationService("ws://localhost:8080"); // mapped to ws://localhost:5125 via gateway
		contentService = new ContentService("http://localhost:8080"); // mapped to http://localhost:5126 via gateway
		*/
		
		identityService = new IdentityService("https://eshop.hldn.live:8543");
		catalogService = new CatalogService("http://eshop.hldn.live:8080"); // mapped to http://eshop.hldn.live:5121 via gateway
		basketService = new BasketService("http://eshop.hldn.live:8080"); // mapped to http://eshop.hldn.live:5122 via gateway 
		orderService = new OrderService("http://eshop.hldn.live:8080"); // mapped to http://eshop.hldn.live:5123 via gateway
		// TODO: add payment service?
		notificationService = new NotificationService("ws://eshop.hldn.live:8080"); // mapped to ws://eshop.hldn.live:5125 via gateway
		contentService = new ContentService("http://eshop.hldn.live:8080"); // mapped to http://eshop.hldn.live:5126 via gateway
	}
}
