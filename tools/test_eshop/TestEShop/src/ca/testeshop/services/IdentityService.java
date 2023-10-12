package ca.testeshop.services;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import ca.testeshop.utils.*;

public class IdentityService extends Service implements IdentityServiceAPI {

	public IdentityService() {
		
	}
	
	public IdentityService(String urlbase) {
		super(urlbase);
	}
	
	private String buildEndSessionPayload() {
		StringBuilder stringBuilder = new StringBuilder();
        
		stringBuilder.append("?id_token_hint=" + HttpUtils.authToken.get());
		stringBuilder.append("&post_logout_redirect_uri=http://docker.for.mac.localhost:5104/");
        
        return stringBuilder.toString();
	}
	
	public EShopResponse endSession() throws Exception {
		return HttpUtils.doGet(urlBase + "/connect/endsession" + buildEndSessionPayload());
	}
	
	private HttpUtils.HttpPayload buildLoginPayload(String email, String password, String token) throws Exception {
		HttpUtils.HttpPayload payload = new HttpUtils.HttpPayload();
		StringBuilder stringBuilder = new StringBuilder();
        
		stringBuilder.append("?ReturnUrl=%2Fconnect%2Fauthorize%2Fcallback%3Fresponse_type%3Did_token%2520token%26client_id%3Djs");
		stringBuilder.append("%26redirect_uri%3Dhttp%253A%252F%252Fdocker.for.mac.localhost%253A5104%252F");
		stringBuilder.append("%26scope%3Dopenid%2520profile%2520orders%2520basket%2520webshoppingagg%2520orders.signalrhub");
		stringBuilder.append("%26nonce%3DN0.062170895354270121691431671493"); // TODO: generate this?
		stringBuilder.append("%26state%3D16914316714920.06560784072824567"); // TODO: generate this?
		stringBuilder.append("&Email=" + email);
		stringBuilder.append("&Password=" + password);
		stringBuilder.append("&button=login");
		stringBuilder.append("&__RequestVerificationToken=" + token);
		stringBuilder.append("&RememberLogin=false");

		payload.type = HttpUtils.HttpPayload.types.XWWWFORMURLENCODED;
        payload.data = stringBuilder.toString();
        
        //payload.dump();
        
        return payload;
	}
	
	private String buildLoginURL() {
		StringBuilder stringBuilder = new StringBuilder();
        
		stringBuilder.append("?ReturnUrl=%2Fconnect%2Fauthorize%2Fcallback%3Fresponse_type%3Did_token%2520token%26client_id%3Djs");
		stringBuilder.append("%26redirect_uri%3Dhttp%253A%252F%252Fdocker.for.mac.localhost%253A5104%252F");
		stringBuilder.append("%26scope%3Dopenid%2520profile%2520orders%2520basket%2520webshoppingagg%2520orders.signalrhub");
		stringBuilder.append("%26nonce%3DN0.062170895354270121691431671493"); // TODO: generate this?
		stringBuilder.append("%26state%3D16914316714920.06560784072824567"); // TODO: generate this?
        
        return stringBuilder.toString();
	}
	
	public EShopResponse login(String email, String password, String token) throws Exception {
		return HttpUtils.doPost(urlBase + "/Account/Login" + buildLoginURL(), buildLoginPayload(email, password, token), true);
	}
	
	private String buildAuthorizePayload() {
		StringBuilder stringBuilder = new StringBuilder();
        
		stringBuilder.append("?response_type=id_token%20token");
		stringBuilder.append("&client_id=js");
		stringBuilder.append("&redirect_uri=http://docker.for.mac.localhost:5104/");
		stringBuilder.append("&scope=openid%20profile%20orders%20basket%20webshoppingagg%20orders.signalrhub");
		stringBuilder.append("&nonce=N0.062170895354270121691431671493"); // TODO: generate this?
		stringBuilder.append("&state=16914316714920.06560784072824567"); // TODO: generate this?
        
        return stringBuilder.toString();
	}
	
	public EShopResponse authorize() throws Exception {
		return HttpUtils.doGet(urlBase + "/connect/authorize" + buildAuthorizePayload());
	}
	
	/*
	http://docker.for.mac.localhost:5105/Account/Register
		?returnurl=%2Fconnect%2Fauthorize%2Fcallback%3Fresponse_type%3Did_token%2520token%26client_id%3Djs
		%26redirect_uri%3Dhttp%253A%252F%252Fdocker.for.mac.localhost%253A5104%252F%26scope%3Dopenid%2520profile%2520orders%2520basket%2520webshoppingagg%2520orders.signalrhub
		%26nonce%3DN0.97816532256736681693562513218%26state%3D16935625132180.22370837161968837
		
	User.Name=Test
	&User.LastName=User
	&User.Street=123+Test+Street
	&User.City=Toronto
	&User.State=Ontario
	&User.Country=Canada
	&User.ZipCode=A1B2C3
	&User.PhoneNumber=123-456-789
	&User.CardNumber=123456789101
	&User.CardHolderName=Test+User
	&User.Expiration=01%2F30
	&User.SecurityNumber=1234
	&Email=testUser%40testeshop.ca
	&Password=Pass%40word1
	&ConfirmPassword=Pass%40word1
	&__RequestVerificationToken=CfDJ8Eqvo0sv4glJghm_QJ6vKj0oWZdvcZFx5oHr1ddLL5PWPvYxt9soC0ZtKAueAoprzkOyOSwBLIHus23hd6dLgnL1pm7qHH7vxNdSGSXyJxJDgsakaUGXYYH9aMoPAgSfRGcJMoRxCPt7DDacXM6e1YY 
	*/
	
	private String buildRegisterURL() {
		StringBuilder stringBuilder = new StringBuilder();
        
		stringBuilder.append("?returnurl=%2Fconnect%2Fauthorize%2Fcallback%3Fresponse_type%3Did_token%2520token%26client_id%3Djs");
		stringBuilder.append("%26redirect_uri%3Dhttp%253A%252F%252Fdocker.for.mac.localhost%253A5104%252F");
		stringBuilder.append("%26scope%3Dopenid%2520profile%2520orders%2520basket%2520webshoppingagg%2520orders.signalrhub");
		stringBuilder.append("%26nonce%3DN0.062170895354270121691431671493"); // TODO: generate this?
		stringBuilder.append("%26state%3D16914316714920.06560784072824567"); // TODO: generate this?
        
        return stringBuilder.toString();
	}
	
	private HttpUtils.HttpPayload buildRegisterPayload(String email, String password, String token) throws Exception {
		HttpUtils.HttpPayload payload = new HttpUtils.HttpPayload();
		StringBuilder stringBuilder = new StringBuilder();
        
		stringBuilder.append("User.Name=Test");
		stringBuilder.append("&User.LastName=User");
		stringBuilder.append("&User.Street=123+Test+Street");
		stringBuilder.append("&User.City=Toronto");
		stringBuilder.append("&User.State=Ontario");
		stringBuilder.append("&User.Country=Canada");
		stringBuilder.append("&User.ZipCode=A1B2C3");
		stringBuilder.append("&User.PhoneNumber=123-456-789");
		stringBuilder.append("&User.CardNumber=123456789101");
		stringBuilder.append("&User.CardHolderName=Test+User");
		stringBuilder.append("&User.Expiration=01%2F30");
		stringBuilder.append("&User.SecurityNumber=1234");
		stringBuilder.append("&Email=" + email);
		stringBuilder.append("&Password=" + password);
		stringBuilder.append("&ConfirmPassword=" + password);
		stringBuilder.append("&__RequestVerificationToken=" + token);

		payload.type = HttpUtils.HttpPayload.types.XWWWFORMURLENCODED;
        payload.data = stringBuilder.toString();
        
        //payload.dump();
        
        return payload;
	}
	
	public EShopResponse getRegistrationForm() throws Exception {
		return HttpUtils.doGet(urlBase + "/Account/Register" + buildRegisterURL());
	}
	
	public EShopResponse register(String email, String password, String token) throws Exception {
		return HttpUtils.doPost(urlBase + "/Account/Register" + buildRegisterURL(), buildRegisterPayload(email, password, token));
	}
	
	public EShopResponse authorizeCallback(String location) throws Exception {
		return HttpUtils.doGet(urlBase + location, true);
	}
	
	public EShopResponse getUserInfo() throws Exception {
		return HttpUtils.doGet(urlBase + "/connect/userInfo");
	}
}
