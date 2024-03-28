package ca.testeshop.utils;

public class Notification {
	
	public Notification() {}
	
	protected String userId;
	protected String notificationType;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	public String toString() {
		StringBuilder output = new StringBuilder();
		output.append("userId: " + getUserId());
		output.append(" notificationType: " + getNotificationType());
		return output.toString();
	}
}
