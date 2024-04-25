package ca.hldnnotification.notification;

import ca.hldnnotification.dto.runtime.Basket;

public class CheckoutSuccessfulNotification extends Notification {
	private Basket basket;
	
	public CheckoutSuccessfulNotification() {}
	
	public CheckoutSuccessfulNotification(Basket basket) {
		this.userId = basket.getUserId();
		this.notificationType = getClass().getSimpleName();
		this.basket = basket;
	}
	
	public Basket getBasket() {
		return basket;
	}

	public void setBasket(Basket basket) {
		this.basket = basket;
	}

	public String toString() {
		StringBuilder output = new StringBuilder();
		output.append(super.toString());
		output.append(" basket: " + basket);
		return output.toString();
	}
}
