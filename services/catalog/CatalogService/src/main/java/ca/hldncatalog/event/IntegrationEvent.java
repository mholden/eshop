package ca.hldncatalog.event;

public abstract class IntegrationEvent {
	
	public abstract String getEventType();

	public String toString() {
		StringBuilder output = new StringBuilder();
		output.append("eventType: " + getEventType());
		return output.toString();
	}
}
