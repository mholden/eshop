package ca.hldnpayment.event;

import ca.hldnpayment.dto.runtime.Order;

public class OrderPaymentSucceededEvent extends IntegratedEvent {
	private Order order;
	
	public OrderPaymentSucceededEvent() {}
	
	public OrderPaymentSucceededEvent(Order order) {
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
