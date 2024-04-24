package ca.testeshop.utils;

// the response of the oidc /token endpoint
public class OIDCTokens {
	public String access_token;
	public Integer expires_in;
	public Integer refresh_expires_in;
	public String refresh_token;
	public String token_type;
	public String id_token;
	public Integer not_before_policy;
	public String session_state;
	public String scope;
	
	public String toString() {
		return String.format("access_token: %s id_token: %s", access_token, id_token);
	}
}
