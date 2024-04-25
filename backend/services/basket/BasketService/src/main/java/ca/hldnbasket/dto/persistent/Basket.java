package ca.hldnbasket.dto.persistent;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("Basket")
public class Basket implements Serializable {

	@Id
	private String userId;
	private List<BasketItem> basketItems;

	public Basket() {

	}

	public Basket(String userId, List<BasketItem> basketItems) {
		this.userId = userId;
		this.basketItems = basketItems;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<BasketItem> getBasketItems() {
		return basketItems;
	}

	public void setBasketItems(List<BasketItem> basketItems) {
		this.basketItems = basketItems;
	}

	public String toString() {
		StringBuilder output = new StringBuilder();
		output.append("userId: " + userId);
		output.append(" basketItems: " + basketItems);
		return output.toString();
	}
}
