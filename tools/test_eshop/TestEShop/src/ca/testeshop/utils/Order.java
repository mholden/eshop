package ca.testeshop.utils;

import java.util.*;

//
// Ordering.API -> Application -> Queries -> OrderViewModel.cs
//
public class Order {
	
	public Integer ordernumber;
    public String date;
    public String status;
    public String description;
    public String street;
    public String city;
    public String zipcode;
    public String country;
    public List<OrderItem> orderitems;
    public Double total;
	
	Order() {
		
	}

	public String toString() {
		StringBuilder output = new StringBuilder();
		output.append("ordernumber: " + ordernumber + " date: " + date + " status: " + status + " total: " + total + "\n");
		for (OrderItem orderItem : orderitems) {
			output.append(orderItem);
		}
		output.append("\n");
		return output.toString();
	}
}
