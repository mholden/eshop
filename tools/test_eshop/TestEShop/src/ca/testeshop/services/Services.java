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
	
	public Services() {
		
	}
	
	public Services(Services.location location) throws Exception {
		if (!(location == Services.location.LOCAL))
			throw new Exception("Non-local services not yet supported");
		
		//aggregatorService = new AggregatorService("http://docker.for.mac.localhost:5121");
		aggregatorService = new AggregatorService("http://localhost:5202");
		//identityService = new IdentityService("http://docker.for.mac.localhost:5105");
		identityService = new IdentityService("http://localhost:5105");
		//catalogService = new CatalogService("http://docker.for.mac.localhost:5101");
		catalogService = new CatalogService("http://localhost:5101");
	}
}
