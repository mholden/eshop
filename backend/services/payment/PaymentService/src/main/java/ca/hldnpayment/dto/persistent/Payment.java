package ca.hldnpayment.dto.persistent;

import ca.hldnpayment.dto.runtime.Order;
import jakarta.persistence.*;

@Entity
@Table(name = "payment")
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private Integer orderItemId;

	public Payment() {

	}

	public Payment(Order order) {
		this.orderItemId = order.getId();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Integer orderItemId) {
		this.orderItemId = orderItemId;
	}

	public String toString() {
		return String.format("id: %d orderItemId %d", id, orderItemId);
	}
}
