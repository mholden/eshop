package ca.testeshop.utils;

public class OrderPaymentSucceededNotification extends Notification {
	private Order order;
	
	public OrderPaymentSucceededNotification() {}
	
	public OrderPaymentSucceededNotification(Order order) {
		this.userId = order.userId;
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
