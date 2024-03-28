package ca.testeshop.utils;

// ca.hldnbasket.dto.BasketItem
public class BasketItem {

	public Integer id;
	private String userId;
	public Integer catalogItemId;

	BasketItem() {

	}

	public BasketItem(CatalogItem item) {
		catalogItemId = item.id;
	}

	public String toString() {
		StringBuilder output = new StringBuilder();
		output.append("id: " + id + " userId: " + userId + " catalogItemId: " + catalogItemId);
		return output.toString();
	}
}
