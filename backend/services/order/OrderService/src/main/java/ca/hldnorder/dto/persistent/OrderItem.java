package ca.hldnorder.dto.persistent;

import jakarta.persistence.*;

@Entity
@Table(name = "orderitem")
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private Integer orderId;
	private Integer catalogItemId;

	public OrderItem() {

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
