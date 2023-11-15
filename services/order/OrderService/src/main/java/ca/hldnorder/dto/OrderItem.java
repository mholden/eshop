package ca.hldnorder.dto;

import jakarta.persistence.*;

@Entity
@Table(name = "orderitem")
public class OrderItem {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	private Integer orderId; // TODO: could use a join here..
	//
	// .. but do you want to? using joins always adds a dependency,
	// which basically disallows separating orders from order items
	// in the future
	//
    private Integer catalogItemId; 

    public OrderItem(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
    
    public Integer getCatalogItemId() {
        return catalogItemId;
    }

    public void setCatalogItemId(Integer catalogItemId) {
        this.catalogItemId = catalogItemId;
    }
    
    public String toString() {
    	return String.format("id: %d orderId %d catalogItemId %d", id, orderId, catalogItemId);
    }
}
