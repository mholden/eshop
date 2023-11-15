package ca.hldnpayment.event;

import java.util.List;

import ca.hldnpayment.dto.BasketItem;

public class CheckoutEvent extends IntegrationEvent {
	public String userId;
	public List<BasketItem> basketItems;
	
	public CheckoutEvent() {}
	
	public CheckoutEvent(String userId, List<BasketItem> basketItems) {
		this.userId = userId;
		this.basketItems = basketItems;
	}
	
	public String getEventType() {
		return getClass().getSimpleName();
	}
	
	public String toString() {
		StringBuilder output = new StringBuilder();
		output.append(super.toString());
		output.append(" userId: " + userId);
		output.append(" basketItems: " + basketItems);
		return output.toString();
	}
}
