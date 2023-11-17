package ca.hldnorder.dto.persistent;

import java.sql.Timestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "_order") // note: 'order' is a reserved word in sql
public class Order {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	private String userId;
	private Timestamp creationTime;
	
	public enum OrderState { 
		INITIATED, 
		VERIFIED, 
		PAYMENT_SUCCEEDED, 
		SHIPPED 
	}
	
	@Enumerated(EnumType.STRING)
	private OrderState state;

    public Order(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }
    
    public OrderState getState() {
		return state;
	}

	public void setState(OrderState state) {
		this.state = state;
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
