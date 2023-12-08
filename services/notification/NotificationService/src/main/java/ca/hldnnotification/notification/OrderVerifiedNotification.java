package ca.hldnnotification.notification;

import ca.hldnnotification.dto.runtime.Order;

public class OrderVerifiedNotification extends Notification {
	private Order order;
	
	public OrderVerifiedNotification() {}
	
	public OrderVerifiedNotification(Order order) {
		this.userId = order.getUserId();
		this.notificationType = getClass().getSimpleName();
		this.order = order;
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
