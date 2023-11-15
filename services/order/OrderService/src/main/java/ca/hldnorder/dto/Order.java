package ca.hldnorder.dto;

import java.sql.Timestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "order")
public class Order {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	private String userId;
	private Timestamp creationTime;
	private String state;

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
    
    public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String toString() {
    	return String.format("id: %d userId %d creationTime %d", id, userId, creationTime);
    }
}
