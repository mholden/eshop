package ca.testeshop.services;

public class Services {
	public static enum location {
		LOCAL,
		DEV,
		STAGING,
		PROD
	}
	
	public AggregatorService aggregatorService;
	public IdentityService identityService;
	public CatalogService catalogService;
	public BasketService basketService;
	
	public Services() {
		
	}
	
	public Services(Services.location location) throws Exception {
		if (!(location == Services.location.LOCAL))
			throw new Exception("Non-local services not yet supported");
		
		aggregatorService = new AggregatorService("http://localhost:5202");
		identityService = new IdentityService("http://localhost:5122"); // just using basket service for now
		catalogService = new CatalogService("http://localhost:5121");
		basketService = new BasketService("http://localhost:5122");
	}
}
