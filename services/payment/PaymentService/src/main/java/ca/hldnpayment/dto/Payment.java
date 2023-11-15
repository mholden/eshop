package ca.hldnpayment.dto;

import jakarta.persistence.*;

@Entity
@Table(name = "payment")
public class Payment {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Integer catalogItemId;
    private Integer orderItemId;

    public Payment(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getCatalogItemId() {
		return catalogItemId;
	}

	public void setCatalogItemId(Integer catalogItemId) {
		this.catalogItemId = catalogItemId;
	}

	public Integer getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Integer orderItemId) {
		this.orderItemId = orderItemId;
	}

	public String toString() {
    	return String.format("id: %d catalogItemId %d orderItemId %d", id, catalogItemId, orderItemId);
    }
}
