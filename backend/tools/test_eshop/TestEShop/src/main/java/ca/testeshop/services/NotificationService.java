package ca.testeshop.services;

public class NotificationService extends Service implements NotificationServiceAPI {
	
	public String asyncChannelUrl;

	public NotificationService() {
		
	}
	
	public NotificationService(String urlbase) {
		super(urlbase);
		asyncChannelUrl = urlbase + "/notification/ws";
	}
}
