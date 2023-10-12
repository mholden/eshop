package ca.testeshop.utils;

import java.util.UUID;

//
// Basket.API -> Model -> BasketItem.cs
//
public class BasketItem {
	
	public String id;
    public Integer productId;
    public String productName;
    public Double unitPrice;
    public Double oldUnitPrice;
    public Integer quantity;
    public String pictureUrl;
	
	BasketItem() {
		
	}
	
	public BasketItem(CatalogItem item) {
		id = UUID.randomUUID().toString();
		productId = item.id;
		productName = item.name;
		unitPrice = item.price;
		oldUnitPrice = 0.0;
		quantity = 1;
		pictureUrl = item.pictureUri;
	}

	public String toString() {
		StringBuilder output = new StringBuilder();
		output.append("id: " + id + " productId: " + productId + " productName: " + productName + " unitPrice: " + unitPrice + " oldUnitPrice: " + oldUnitPrice + 
								" quantity: " + quantity + " pictureUrl: " + pictureUrl);
		output.append("\n");
		return output.toString();
	}
}
