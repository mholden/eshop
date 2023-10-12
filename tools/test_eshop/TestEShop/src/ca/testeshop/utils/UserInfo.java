package ca.testeshop.utils;

//
// https://identityserver4.readthedocs.io/en/latest/endpoints/userinfo.html
//
public class UserInfo {
	
	public String sub;
	public String preferred_username;
	public String unique_name;
	public String name;
	public String last_name;
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
	
	UserInfo() {
		
	}
}
