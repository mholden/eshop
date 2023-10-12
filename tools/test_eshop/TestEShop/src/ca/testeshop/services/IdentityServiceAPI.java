package ca.testeshop.services;

import ca.testeshop.utils.*;

public interface IdentityServiceAPI {
	public EShopResponse authorize() throws Exception;
	public EShopResponse authorizeCallback(String location) throws Exception;
	public EShopResponse login(String username, String password, String token) throws Exception;
}
