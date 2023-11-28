package ca.testeshop.utils;

import java.sql.Timestamp;

//ca.hldnorder.dto.persistent.Order
public class Order {
	
	public int id;
	public String userId;
	public Timestamp creationTime;
	
	public enum OrderState { 
		INITIATED, 
		VERIFIED, 
		PAYMENT_SUCCEEDED, 
		SHIPPED 
	}
	public OrderState state;
	
	Order() {
		
	}

	public String toString() {
		StringBuilder output = new StringBuilder();
		output.append("id: " + id);
		output.append(" userId: " + userId);
		output.append(" creationTime: " + creationTime);
		output.append(" state: " + state);
		return output.toString();
    }
}
