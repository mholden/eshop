package ca.hldnnotification.event;

import ca.hldnnotification.dto.runtime.Basket;

public class CheckoutSuccessfulEvent extends IntegratedEvent {
	public Basket basket;
	
	public CheckoutSuccessfulEvent() {}
	
	public CheckoutSuccessfulEvent(Basket basket) {
		this.basket = basket;
	}
	
	public String getEventType() {
		return getClass().getSimpleName();
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
