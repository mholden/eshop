package ca.hldnorder.event;

public abstract class IntegratedEvent {
	
	public abstract String getEventType();

	public String toString() {
		StringBuilder output = new StringBuilder();
		output.append("eventType: " + getEventType());
		return output.toString();
	}
}
