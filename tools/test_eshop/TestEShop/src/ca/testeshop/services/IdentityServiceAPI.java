package ca.testeshop.services;

import ca.testeshop.utils.*;

public interface IdentityServiceAPI {
	public EShopResponse authorize() throws Exception;
	public EShopResponse authenticate(String url, String username, String password) throws Exception;
	public EShopResponse login() throws Exception;
	public EShopResponse logout() throws Exception;
}
