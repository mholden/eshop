package ca.hldnorder.event;

import ca.hldnorder.dto.persistent.Order;

public class OrderPaymentInitiatedEvent extends IntegratedEvent {
	private Order order;
	
	public OrderPaymentInitiatedEvent() {}
	
	public OrderPaymentInitiatedEvent(Order order) {
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
