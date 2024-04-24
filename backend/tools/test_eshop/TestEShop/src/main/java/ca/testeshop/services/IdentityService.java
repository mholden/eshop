package ca.testeshop.services;

import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.StringTokenizer;

import ca.testeshop.utils.*;

public class IdentityService extends Service implements IdentityServiceAPI {

	public IdentityService() {

	}

	public IdentityService(String urlbase) {
		super(urlbase);
	}

	public EShopResponse login() throws Exception {
		return HttpUtils.doGet(urlBase + "/login");
	}

	private String buildLogoutPayload(String idTokenHint) {
		StringBuilder stringBuilder = new StringBuilder();
        
		stringBuilder.append("?id_token_hint=" + idTokenHint);
		
		return stringBuilder.toString();
	}
	
	public EShopResponse logout(String idTokenHint) throws Exception {
		return HttpUtils.doGet(urlBase + "/auth/realms/spring-cloud-gateway-realm/protocol/openid-connect/logout" + buildLogoutPayload(idTokenHint));
	}

	private HttpUtils.HttpPayload buildAuthenticatePayload(String email, String password) throws Exception {
		HttpUtils.HttpPayload payload = new HttpUtils.HttpPayload();
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("username=" + email + "&password=" + password + "&credentialId=");

		payload.type = HttpUtils.HttpPayload.types.XWWWFORMURLENCODED;
		payload.data = stringBuilder.toString();

		// payload.dump();

		return payload;
	}

	public EShopResponse authenticate(String url, String email, String password) throws Exception {
		return HttpUtils.doPost(url, buildAuthenticatePayload(email, password), true);
	}
	
	private String buildAuthorizePayload() {
		StringBuilder stringBuilder = new StringBuilder();
        
		stringBuilder.append("?client_id=spring-cloud-gateway-client"
				+ "&redirect_uri=http%3A%2F%2Flocalhost%3A3000"
				+ "&response_type=code"
				+ "&scope=openid"
				+ "&state=75bc1723c442427e85f354c143d96d29"
				+ "&code_challenge=LaG2-QpJLKPFkIec0i0PbGOJlWkJrIrRVHa3ruOTVEc"
				+ "&code_challenge_method=S256");
        
        return stringBuilder.toString();
	}

	public EShopResponse authorize() throws Exception {
		return HttpUtils.doGet(urlBase + "/auth/realms/spring-cloud-gateway-realm/protocol/openid-connect/auth" + buildAuthorizePayload());
	}

	private HttpUtils.HttpPayload buildRegisterPayload(String email, String password) throws Exception {
		HttpUtils.HttpPayload payload = new HttpUtils.HttpPayload();
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("firstName=Test");
		stringBuilder.append("&lastName=User");
		stringBuilder.append("&email=" + URLEncoder.encode(email, StandardCharsets.UTF_8.toString()));
		stringBuilder.append("&password=" + URLEncoder.encode(password, StandardCharsets.UTF_8.toString()));
		stringBuilder.append("&password-confirm=" + URLEncoder.encode(password, StandardCharsets.UTF_8.toString()));

		payload.type = HttpUtils.HttpPayload.types.XWWWFORMURLENCODED;
		payload.data = stringBuilder.toString();

		// payload.dump();

		return payload;
	}

	public EShopResponse register(String url, String email, String password) throws Exception {
		StringTokenizer st;
		String _url = null;
		EShopResponse response;

		response = HttpUtils.doGet(url);
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
		// response.dump();

		// get registration url from output
		Scanner scanner = new Scanner(response.response);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (line.contains("kc-register-form")) {
				// System.out.println(line);
				st = new StringTokenizer(line, "\"");
				String last = "", next;
				while (st.hasMoreTokens()) {
					next = st.nextToken();
					if (last.contains("action")) {
						_url = next.replaceAll("&amp;", "&");
						break;
					}
					last = next;
				}
				break;
			}
		}
		scanner.close();

		//System.out.println("_url is " + _url);

		response = HttpUtils.doPost(_url, buildRegisterPayload(email, password), true);
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_MOVED_TEMP, response.toString()); // should get 302 here

		return response;
	}
	
	private HttpUtils.HttpPayload buildTokenPayload(String code) throws Exception {
		HttpUtils.HttpPayload payload = new HttpUtils.HttpPayload();
		StringBuilder stringBuilder = new StringBuilder();
        
		stringBuilder.append("grant_type=authorization_code"
				+ "&redirect_uri=http%3A%2F%2Flocalhost%3A3000"
				+ "&code=" + code
				//
				// note: this code verifier pairs with the code_challenge in buildAuthorizePayload above.
				// can use https://tonyxu-io.github.io/pkce-generator/ to generate a valid pair (pass in 
				// code verifier, get out code challenge). should really be generating this randomly on
				// each call though
				//
				+ "&code_verifier=03647067219d495795ae00fe8adaf7d56a6bfd5882ad455bb92d020541e524d1094096b498b54093b88c9455ada55c7f"
				+ "&client_id=spring-cloud-gateway-client");
        
		payload.type = HttpUtils.HttpPayload.types.XWWWFORMURLENCODED;
		payload.data = stringBuilder.toString();

		//payload.dump();
		
        return payload;
	}
	
	public EShopResponse token(String code) throws Exception {
		return HttpUtils.doPost(urlBase + "/auth/realms/spring-cloud-gateway-realm/protocol/openid-connect/token", buildTokenPayload(code));
	}

	public EShopResponse getUserInfo() throws Exception {
		return HttpUtils.doGet(urlBase + "/auth/realms/spring-cloud-gateway-realm/protocol/openid-connect/userinfo");
	}
}
