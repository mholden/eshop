package ca.testeshop.utils;

// the response of the oidc /userinfo endpoint
public class OIDCUserInfo {
	
	public String sub;
	public String preferred_username;
	public String given_name;
	public String name;
	public String family_name;
	public String card_number;
	public String card_holder;
	public String card_security_number;
	public String card_expiration;
	public String address_city;
	public String address_country;
	public String address_state;
	public String address_street;
	public String address_zip_code;
	public String email;
	public Boolean email_verified;
	public String phone_number;
	public Boolean phone_number_verified;
	
	OIDCUserInfo() {
		
	}
	
	public String toString() {
		return String.format("sub: %s name: %s preferred_username: %s", sub, name, preferred_username);
	}
}
