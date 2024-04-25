package ca.hldnbasket.event;

import ca.hldnbasket.dto.persistent.Basket;

public class CheckoutSuccessfulEvent extends IntegratedEvent {
	public Basket basket;
	
	public CheckoutSuccessfulEvent() {}
	
	public CheckoutSuccessfulEvent(Basket basket) {
		this.basket = basket;
	}
	
	public String getEventType() {
		return getClass().getSimpleName();
	}
	
	public String toString() {
		StringBuilder output = new StringBuilder();
		output.append(super.toString());
		output.append(" basket: " + basket);
		return output.toString();
	}
}
