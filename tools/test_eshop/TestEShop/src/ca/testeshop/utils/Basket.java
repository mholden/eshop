package ca.testeshop.utils;

import java.util.*;

// ca.hldnbasket.dto.Basket
public class Basket {
	
	public List<BasketItem> basketItems;
	
	Basket() {
		
	}

	public String toString() {
		StringBuilder output = new StringBuilder();
		output.append("items:");
		output.append("\n");
		if (basketItems != null) {
			for (BasketItem item : basketItems) {
				output.append(item);
			}
		}
		return output.toString();
	}
}
