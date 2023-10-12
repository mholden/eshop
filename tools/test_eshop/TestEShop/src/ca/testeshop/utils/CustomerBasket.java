package ca.testeshop.utils;

import java.util.*;

//
// Basket.API -> Model -> CustomerBasket.cs
//
public class CustomerBasket {
	
	public String buyerId;
	public List<BasketItem> items;
	
	CustomerBasket() {
		
	}

	public String toString() {
		StringBuilder output = new StringBuilder();
		output.append("buyerId: " + buyerId);
		output.append("\n");
		output.append("items:");
		output.append("\n");
		if (items != null) {
			for (BasketItem item : items) {
				output.append(item);
			}
		}
		return output.toString();
	}
}
