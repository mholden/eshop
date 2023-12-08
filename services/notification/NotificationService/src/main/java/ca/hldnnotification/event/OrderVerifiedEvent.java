package ca.hldnnotification.event;

import ca.hldnnotification.dto.runtime.Order;

public class OrderVerifiedEvent extends IntegratedEvent {
	private Order order;
	
	public OrderVerifiedEvent() {}
	
	public OrderVerifiedEvent(Order order) {
		this.order = order;
	}
	
	public String getEventType() {
		return getClass().getSimpleName();
	}
	
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String toString() {
		StringBuilder output = new StringBuilder();
		output.append(super.toString());
		output.append(" order: " + order);
		return output.toString();
	}
}
